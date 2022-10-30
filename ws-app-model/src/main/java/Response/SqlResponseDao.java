package Response;

import es.udc.ws.util.exceptions.InstanceNotFoundException;



import es.udc.ws.util.exceptions.InputValidationException;

import java.sql.Connection;
import java.util.List;

public interface SqlResponseDao {
    public Response create(Connection connection, Response Response) throws InputValidationException;

    public List<Response> findByEmployee(Connection connection, String email, boolean asistencia) throws InputValidationException;

    public boolean ifExistsResponse(Connection connection, String email, Long idEvento) throws InstanceNotFoundException;

    public void remove(Connection connection, Long ResponseId) throws InstanceNotFoundException;

}