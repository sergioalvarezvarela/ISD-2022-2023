package Response;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractSqlResponseDao implements SqlResponseDao {

    protected AbstractSqlResponseDao() {
    }
    @Override
    public List<Response> findByEmployee(Connection connection, String email, boolean asistencia) throws InputValidationException {
        return null;
    }

    @Override
    public boolean ifExistsResponse(Connection connection, String email, Long idEvento) throws InstanceNotFoundException {
        return false;
    }

    @Override
    public void remove(Connection connection, Long responseId) throws InstanceNotFoundException {
        String queryString= "DELETE FROM Response WHERE" + " responseId = ?";
        try(PreparedStatement preparedStatement= connection.prepareStatement(queryString)){
            int i=1;
            preparedStatement.setLong(i++,responseId);
            int removedRows = preparedStatement.executeUpdate();
            if(removedRows==0){
                throw new InstanceNotFoundException(responseId,Response.class.getName());
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
