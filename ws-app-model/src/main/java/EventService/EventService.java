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

    public Event addEvent(Event event) throws InputValidationException;

    public Response addResponse(Response response) throws InputValidationException,InstanceNotFoundException,OutOfTimeException, AlreadyCanceledException, AlreadyResponseException;
    public void CancelEvent(Long eventId, Boolean status) throws InstanceNotFoundException, OutOfTimeException, AlreadyCanceledException, InputValidationException;

    public Event findEvent(Long eventId) throws InstanceNotFoundException;

    public List<Event> findEventsbyDate(LocalDate dateIn, LocalDate dateEnd, String keywords) throws  InputValidationException;


    public List<Response> findResponsebyEmail(String email, Boolean Asist) throws InputValidationException;

}
