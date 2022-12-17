package EventService;

import Event.Event;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.AlreadyResponseException;
import EventService.Exception.OutOfTimeException;
import Response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    Event addEvent(Event event) throws InputValidationException;

    Response addResponse(Response response) throws InputValidationException,InstanceNotFoundException,OutOfTimeException, AlreadyCanceledException, AlreadyResponseException;
    void CancelEvent(Long eventId) throws InstanceNotFoundException, OutOfTimeException, AlreadyCanceledException, InputValidationException;

    Event findEvent(Long eventId) throws InstanceNotFoundException;

    List<Event> findEventsbyDate(LocalDate dateIn, LocalDate dateEnd, String keywords) throws  InputValidationException;


    List<Response> findResponsebyEmail(String email, Boolean Asist) throws InputValidationException;

}
