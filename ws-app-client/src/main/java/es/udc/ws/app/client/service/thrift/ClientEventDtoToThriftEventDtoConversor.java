package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.thrift.ThriftEventDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

public class ClientEventDtoToThriftEventDtoConversor {

    public static ThriftEventDto toThriftEventDto(
            ClientEventDto clientEventDto) {

        Long eventId = clientEventDto.getEventId();

        return new ThriftEventDto(
                eventId == null ? -1 : eventId.longValue(),
                clientEventDto.getEventName(),
                clientEventDto.getCelebrationDate().toString(),
                (int) HOURS.between(clientEventDto.getCelebrationDate(), clientEventDto.getEndDate()),
                clientEventDto.getEventDescription(),
                clientEventDto.getEventState(),
                clientEventDto.getAttendance(),
                clientEventDto.getTotalAttendance());

    }

    public static List<ClientEventDto> toClientEventDtos(List<ThriftEventDto> events) {

        List<ClientEventDto> clientEventDtos = new ArrayList<>(events.size());

        for (ThriftEventDto event : events) {
            clientEventDtos.add(toClientEventDto(event));
        }
        return clientEventDtos;

    }

    private static ClientEventDto toClientEventDto(ThriftEventDto event) {

        return new ClientEventDto(
                event.getEventId(),
                event.getEventName(),
                event.getEventDescription(),
                LocalDateTime.parse(event.getCelebrationDate()),
                LocalDateTime.parse(event.getCelebrationDate()).plusHours(event.getRuntime()),
                event.isEventState(),
                event.getAttendance(),
                event.getTotalAttendance());

    }
}
