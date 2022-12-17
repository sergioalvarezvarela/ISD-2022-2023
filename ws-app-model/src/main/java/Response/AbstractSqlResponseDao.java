package Response;


import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSqlResponseDao implements SqlResponseDao {

    protected AbstractSqlResponseDao() {
    }


     @Override
     public List<Response> findByEmployee(Connection connection, String email, Boolean asistencia) {
         String queryString;
         if(asistencia){
             queryString="SELECT responseId, eventId, responseDate, attendance "
             + "FROM Response WHERE workerEmail = ? AND attendance = true";
         } else {
             queryString="SELECT responseId, eventId, responseDate, attendance "
                        + "FROM Response WHERE workerEmail = ?";
         }

         try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)){
             int i=1;
             preparedStatement.setString(i++,email);
             ResultSet resultSet= preparedStatement.executeQuery();
             List<Response> responses= new ArrayList<>();
             while (resultSet.next()){
                 i=1;
                 Long responseId=resultSet.getLong(i++);
                 Long eventId= resultSet.getLong(i++);
                 Timestamp responseDateAsTimestamp = resultSet.getTimestamp(i++);
                 LocalDateTime responseDate = responseDateAsTimestamp.toLocalDateTime();
                 boolean attendance  = resultSet.getBoolean(i++);

                 responses.add(new Response(responseId,eventId,email,responseDate, attendance));
             }
             return responses;
         } catch (SQLException e){
             throw new RuntimeException(e);
         }
    }



    @Override
    public void remove(Connection connection, Long responseId) throws InstanceNotFoundException {
        String queryString = "DELETE FROM Response WHERE" + " responseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            int i = 1;
            preparedStatement.setLong(i++, responseId);
            int removedRows = preparedStatement.executeUpdate();
            if (removedRows == 0) {
                throw new InstanceNotFoundException(responseId, Response.class.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsResponse(Connection connection,Long eventId, String email) {

        /* Create "queryString". */
        String queryString = "SELECT responseId, responseDate, attendance"
                + " FROM Response WHERE eventId = ? AND workerEmail = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, eventId);
            preparedStatement.setString(i++,email);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

            /* Get results. */

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response findResponseById(Connection connection, Long responseId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT eventId, workerEmail, responseDate, attendance"
                + " FROM Response WHERE responseId = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, responseId);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(responseId, Response.class.getName());
            }

            /* Get results. */
            i = 1;
            Long eventId = resultSet.getLong(i++);
            String workerEmail = resultSet.getString(i++);
            Timestamp responseDateAsTimestamp = resultSet.getTimestamp(i++);
            boolean attendance = resultSet.getBoolean(i++);
            LocalDateTime responseDate = responseDateAsTimestamp.toLocalDateTime();

            /* Return Response. */
            return new Response(responseId,eventId, workerEmail,
                    responseDate,attendance);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}