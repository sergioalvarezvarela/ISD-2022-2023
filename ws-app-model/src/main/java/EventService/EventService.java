package EventService;
import java.time.LocalDateTime;
import java.util.List;
import Event.Event;
import EventService.Exception.AlreadyDoneException;
import Response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import EventService.Exception.OutOfTimeException;

public interface EventService {

    public Event addEvent(Event event) throws InputValidationException;
    public Response addResponse(Response response) throws InputValidationException,InstanceNotFoundException,OutOfTimeException;
    public void CancelEvent(Long eventId, Boolean status) throws InstanceNotFoundException, OutOfTimeException, AlreadyDoneException, InputValidationException;

    public Event findEvent(Long eventId) throws InstanceNotFoundException;

    public List<Event> findEvents(LocalDateTime dateIn,LocalDateTime dateEnd, String keywords) throws  InputValidationException;

    public List<Response> findResponse(String email, boolean Asist) throws InputValidationException;

}
