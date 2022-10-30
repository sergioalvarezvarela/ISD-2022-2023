package Event;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSqlEventDao implements SqlEventDao {

    protected AbstractSqlEventDao() {
    }


    public Event findEventById(Connection connection, Long eventId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT event_name, description, celebrationDate, creationDate, "
                + " runtime, event_state, attendance, not_attendance FROM Events WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(eventId, Event.class.getName());
            }

            /* Get results. */
            i = 1;
            String event_name = resultSet.getString(i++);
            String description = resultSet.getString(i++);
            Timestamp celebrationDateAsTimestamp = resultSet.getTimestamp(i++);
            Timestamp creationDateAsTimestamp = resultSet.getTimestamp(i++);
            int runtime = resultSet.getInt(i++);
            boolean event_state = resultSet.getBoolean(i++);
            int attendance = resultSet.getInt(i++);
            int not_attendance = resultSet.getInt(i++);

            LocalDateTime creationDate = creationDateAsTimestamp.toLocalDateTime();
            LocalDateTime celebrationDate = celebrationDateAsTimestamp.toLocalDateTime();

            /* Return movie. */
            return new Event(eventId, event_name, description, celebrationDate,
                    creationDate, runtime, event_state, attendance, not_attendance);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> findEventByDateOrAndKeyWord(Connection connection, LocalDate start, LocalDate finish, String keywords) throws InputValidationException {
        return null;
    }

    public void cancel(Connection connection, Long eventid, Boolean status)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Events"
                + " SET event_state = ? WHERE EventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setBoolean(i++, status);
            preparedStatement.setLong(i++, eventid);

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows == 0) {
                throw new InstanceNotFoundException(eventid, Event.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean isAlreadyCancelled(Connection connection, Long eventId, Boolean status) {

        /* Create "queryString". */
        String queryString = "SELECT event_name FROM Events WHERE eventId = ? AND event_state = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            preparedStatement.setBoolean(i++,status);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return false;
            } else{
                return true;
            }
            /* Return movie. */

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void remove(Connection connection, Long eventId) throws InstanceNotFoundException {
        String queryString = "DELETE FROM Events WHERE" + " eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            int removedRows = preparedStatement.executeUpdate();
            if (removedRows == 0) {
                throw new InstanceNotFoundException(eventId, Event.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }}





/*  @Override
    public Event find(Connection connection, Long eventId)
            throws InstanceNotFoundException, SQLException {
        String queryString = "SELECT title, celebracionDate, creationDate"
                + "eventDescription, runtime, eventState, attendance, not_attendance"
                + "FROM Event WHERE evenId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, eventId.longValue());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(eventId,
                        Event.class.getName());
            }
            i = 1;
            String title = resultSet.getString(i++);
            int runtime = resultSet.getShort(i++);
            String eventDescription = resultSet.getString(i++);
            boolean eventState = resultSet.getBoolean(i++);
            int attendance = resultSet.getInt(i++);
            int not_attendance = resultSet.getInt(i++);
            Timestamp celebrationDateT = resultSet.getTimestamp(i++);
            LocalDateTime celebrationDate = celebrationDateT.toLocalDateTime();
            Timestamp creationDateT = resultSet.getTimestamp(i++);
            LocalDateTime creationDate = creationDateT.toLocalDateTime();

            return new Event(eventId, title, eventDescription, runtime, celebrationDate,
                    creationDate, eventState, attendance, not_attendance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}*/
