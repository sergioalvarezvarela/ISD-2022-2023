package Event;

import es.udc.ws.util.exceptions.InputValidationException;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface SqlEventDao {
    Event create(Connection connection, Event event);
    Event findEventById(Connection connection, Long eventId)
            throws InstanceNotFoundException;
    List<Event> findEventByDateOrAndKeyWord(Connection connection, LocalDate start, LocalDate finish, String keywords);
    void remove(Connection connection, Long eventId)
        throws InstanceNotFoundException;

    void CancelEvent(Connection connection, Long eventId)
            throws InstanceNotFoundException;

    void update(Connection connection, Event event)
            throws InstanceNotFoundException;
}
