package Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class Jdbc3CcSqlEventDao extends AbstractSqlEventDao {


    @Override
    public Event create(Connection connection, Event event) {
        /* Create "queryString". */
        String queryString = "INSERT INTO Events"
                + " (event_name, description, runtime, creationDate, celebrationDate, event_state, attendance, not_attendance)"
                + " VALUES (?,  ? , ? , ?, ? , ? , ? , ? )";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1;
            preparedStatement.setString(i++, event.getName());
            preparedStatement.setString(i++, event.getDescription());
            preparedStatement.setInt(i++, event.getRuntime());
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(event.getCreationDate()));
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(event.getCelebrationDate()));
            preparedStatement.setBoolean(i++, event.getEventState());
            preparedStatement.setInt(i++, event.getAttendance());
            preparedStatement.setInt(i++, event.getNot_Attendance());

            /* Execute query. */
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long eventId = resultSet.getLong(1);

            return new Event(eventId, event.getName(), event.getDescription(), event.getCelebrationDate(), event.getCreationDate(), event.getRuntime(), event.getEventState(), event.getAttendance(), event.getNot_Attendance());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
