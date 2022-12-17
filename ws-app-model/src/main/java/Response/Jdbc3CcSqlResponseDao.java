package Response;

import Event.Event;
import es.udc.ws.util.exceptions.InputValidationException;

import java.sql.*;

public class Jdbc3CcSqlResponseDao extends AbstractSqlResponseDao {

    @Override
    public Response create(Connection connection, Response response) {

    /* Create "queryString". */
    String queryString = "INSERT INTO Response"
            + " (eventId, workerEmail, responseDate, attendance)"
            + " VALUES (?, ?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(
            queryString, Statement.RETURN_GENERATED_KEYS)) {

        /* Fill "preparedStatement". */
        int i = 1;
        preparedStatement.setLong(i++, response.getEventId());
        preparedStatement.setString(i++, response.getEmail());
        preparedStatement.setTimestamp(i++, Timestamp.valueOf(response.getResponseDate()));
        preparedStatement.setBoolean(i++, response.getAsistencia());

        /* Execute query. */
        preparedStatement.executeUpdate();

        /* Get generated identifier. */
        ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if (!resultSet.next()) {
            throw new SQLException(
                    "JDBC driver did not return generated key.");
        }
        Long responseId = resultSet.getLong(1);

        /* Return response. */
        return new Response(responseId, response.getEventId(),
                response.getEmail(),response.getResponseDate(), response.getAsistencia());

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}
}
