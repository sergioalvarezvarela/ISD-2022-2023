package es.udc.ws.app.client.service.rest.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientEventDtoConversor {


    public static ObjectNode toObjectNode(ClientEventDto event) throws IOException {

        ObjectNode eventObject = JsonNodeFactory.instance.objectNode();

        if (event.getEventId() != null) {
            eventObject.put("eventId", event.getEventId());
        }
        eventObject.put("eventName", event.getEventName())
                .put("celebrationDate", event.getCelebrationDate().toString())
                .put("runtime", ChronoUnit.HOURS.between(event.getCelebrationDate(),event.getEndDate()))
                .put("description", event.getEventDescription())
                .put("eventDescription", event.getEventDescription())
                .put("eventState", event.getEventState())
                .put("attendance", event.getAttendance())
                .put("totalAttendance", event.getTotalAttendance());

        return eventObject;
    }

    public static ClientEventDto toClientEventDto(InputStream jsonEvent) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonEvent);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientEventDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientEventDto> toClientEventDtos(InputStream jsonMovies) throws ParsingException {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonMovies);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode eventsArray = (ArrayNode) rootNode;
                List<ClientEventDto> eventDtos = new ArrayList<>(eventsArray.size());
                for (JsonNode eventNode : eventsArray) {
                    eventDtos.add(toClientEventDto(eventNode));
                }

                return eventDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientEventDto toClientEventDto(JsonNode eventNode) throws ParsingException {
        if (eventNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode eventObject = (ObjectNode) eventNode;

            JsonNode eventIdNode = eventObject.get("eventId");
            Long movieId = (eventIdNode != null) ? eventIdNode.longValue() : null;
            String eventName = eventObject.get("eventName").textValue().trim();
            LocalDateTime celebrationDate = LocalDateTime.parse(eventObject.get("celebrationDate").textValue().trim());
            LocalDateTime endDate = celebrationDate.plusHours(eventObject.get("runtime").intValue());
            String description = eventObject.get("description").textValue().trim();
            JsonNode eventstateNode = eventObject.get("eventState");
            Boolean state = eventstateNode == null || eventstateNode.booleanValue();
            JsonNode eventattendanceNode = eventObject.get("attendance");
            Integer attendance = (eventattendanceNode != null) ? eventattendanceNode.intValue() : 0;
            JsonNode totalAttendanceNode = eventObject.get("totalAttendance");
            Integer totalAttendance = (totalAttendanceNode != null) ? totalAttendanceNode.intValue() : 0;
            return new ClientEventDto(movieId, eventName, description,celebrationDate , endDate, state, attendance,totalAttendance);
        }
    }

}
