package es.udc.ws.app.restservice.servlets;

import Event.Event;
import EventService.EventServiceFactory;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.OutOfTimeException;
import es.udc.ws.app.restservice.dto.EventToRestEventDtoConversor;
import es.udc.ws.app.restservice.dto.RestEventDto;
import es.udc.ws.app.restservice.json.AppExceptionToJsonConversor;
import es.udc.ws.app.restservice.json.JsonToRestEventDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static es.udc.ws.util.servlet.ServletUtils.normalizePath;

public class EventServlet extends RestHttpServletTemplate {

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InputValidationException, InstanceNotFoundException {
        String path = normalizePath(req.getPathInfo());
        if ((path) == null) {
            RestEventDto eventDto = JsonToRestEventDtoConversor.toRestEventDto(req.getInputStream());
            Event event = EventToRestEventDtoConversor.toEvent(eventDto);

            event = EventServiceFactory.getService().addEvent(event);

            eventDto = EventToRestEventDtoConversor.toRestEventDto(event);
            String eventURL = normalizePath(req.getRequestURL().toString()) + "/" + event.getEventId();
            Map<String, String> headers = new HashMap<>(1);
            headers.put("Location", eventURL);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                    JsonToRestEventDtoConversor.toObjectNode(eventDto), headers);
        } else {
            String[] pathdivided = path.split("/");
            try {
                Long.valueOf(pathdivided[1]);
            } catch (NumberFormatException var6) {
                throw new InputValidationException("Invalid Request: invalid eventId id '" + pathdivided[1] + "'");
            }
            Long eventId = Long.valueOf(pathdivided[1]);
            String cancel = pathdivided[2];
            if (!cancel.equals("cancel")) {
                throw new InputValidationException("Invalid Request: invalid param for event'" + pathdivided[2] + "'");
            }
            try {
                EventServiceFactory.getService().CancelEvent(eventId);
            } catch (OutOfTimeException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE, AppExceptionToJsonConversor.toOutOfTimeException(e), null);
            } catch (AlreadyCanceledException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN, AppExceptionToJsonConversor.toAlreadyCanceledException(e), null);
            }

            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NO_CONTENT, null, null);
        }


    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
            InstanceNotFoundException, InputValidationException {
        String path = normalizePath(req.getPathInfo());
        if ((path) != null) {
            Long eventId = ServletUtils.getIdFromPath(req, "eventId");

            Event event = EventServiceFactory.getService().findEvent(eventId);

            RestEventDto eventDto = EventToRestEventDtoConversor.toRestEventDto(event);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestEventDtoConversor.toObjectNode(eventDto), null);
        } else {
            LocalDate dateNow = LocalDate.now();
            LocalDate finalDate = LocalDate.parse(req.getParameter("finalDate"));
            String keywords = req.getParameter("keywords");
            List<Event> events = EventServiceFactory.getService().findEventsbyDate(dateNow, finalDate, keywords);
            List<RestEventDto> eventDtos = EventToRestEventDtoConversor.toRestEventDtos(events);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestEventDtoConversor.toArrayNode(eventDtos), null);


        }
    }

}