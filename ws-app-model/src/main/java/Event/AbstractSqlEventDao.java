package Event;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSqlEventDao implements SqlEventDao {

    protected AbstractSqlEventDao() {
    }

    @Override
    public Event findEventById(Connection connection, Long eventId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT event_name, description, celebrationDate, creationDate, "
                + " runtime, event_state, attendance, not_attendance FROM Events WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);

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

            /* Return event. */
            return new Event(eventId, event_name, description, celebrationDate,
                    creationDate, runtime, event_state, attendance, not_attendance);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findEventByDateOrAndKeyWord(Connection connection, LocalDate start, LocalDate finish, String keywords) {

        String queryString = "";

        if (start ==null){
             queryString = "SELECT eventId,event_name, description, celebrationDate, creationDate, "
                    + " runtime, event_state, attendance, not_attendance FROM Events WHERE celebrationDate <= ?  ";

        }else{
             queryString = "SELECT eventId,event_name, description, celebrationDate, creationDate, "
                    + " runtime, event_state, attendance, not_attendance FROM Events WHERE celebrationDate >= ? AND celebrationDate <= ?  ";
        }

        if (keywords != null) {
            queryString += " AND ";
            queryString += " LOWER(description) LIKE LOWER(?)";
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            int i = 1;

            /* Fill "preparedStatement". */
            if(start!=null){
                preparedStatement.setDate(i++, java.sql.Date.valueOf(start));
            }
            preparedStatement.setDate(i++, java.sql.Date.valueOf(finish));
            preparedStatement.setString(i++, "%" + keywords + "%");


            ResultSet resultSet = preparedStatement.executeQuery();

            List<Event> eventsBetweenDates = new ArrayList<Event>();

            while (resultSet.next()) {
                i = 1;
                Long eventId = resultSet.getLong(i++);
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


                Event event = new Event(eventId, event_name, description, celebrationDate,
                        creationDate, runtime, event_state, attendance, not_attendance);
                eventsBetweenDates.add(event);

            }
            return eventsBetweenDates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CancelEvent(Connection connection, Long eventId)
            throws InstanceNotFoundException {


        String queryString = "UPDATE Events SET event_state = ?"
                + " WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setBoolean(i++, false);
            preparedStatement.setLong(i++, eventId);

            /* Execute query. */
            int canceledRows = preparedStatement.executeUpdate();
            if (canceledRows == 0) {
                throw new InstanceNotFoundException(eventId, Event.class.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override

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


    }

    @Override
    public void update(Connection connection, Event event)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Events"
                + " SET event_name = ?, description = ?, celebrationDate = ?, "
                + "creationDate = ?, runtime = ?, event_state = ?, attendance = ?, not_attendance = ? WHERE eventId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, event.getName());
            preparedStatement.setString(i++, event.getDescription());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(event.getCelebrationDate()));
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(event.getCreationDate()));
            preparedStatement.setInt(i++, event.getRuntime());
            preparedStatement.setBoolean(i++, event.getEventState());
            preparedStatement.setInt(i++, event.getAttendance());
            preparedStatement.setInt(i++, event.getNot_Attendance());
            preparedStatement.setLong(i++, event.getEventId());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new InstanceNotFoundException(event.getEventId(),
                        Event.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}





