package Event;

import es.udc.ws.util.exceptions.InputValidationException;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface SqlEventDao {
    public Event create(Connection connection, Event event);
    public Boolean isAlreadyCancelled(Connection connection, Long eventId, Boolean status);
    public Event findEventById(Connection connection, Long eventId)
            throws InstanceNotFoundException;
    public List<Event> findEventByDateOrAndKeyWord(Connection connection, LocalDate start, LocalDate finish, String keywords);
    public void remove(Connection connection, Long eventId)
        throws InstanceNotFoundException;

    void CancelEvent(Connection connection, Long eventId, Boolean status)
            throws InstanceNotFoundException;

    public void updateAttendance(Connection connection, Long eventId, Boolean asistencia, int cant)
            throws InstanceNotFoundException;

    void update(Connection connection, Event event)
            throws InstanceNotFoundException;
}
