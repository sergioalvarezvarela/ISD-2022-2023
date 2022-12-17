package Response;

import es.udc.ws.util.exceptions.InstanceNotFoundException;



import es.udc.ws.util.exceptions.InputValidationException;

import java.sql.Connection;
import java.util.List;

public interface SqlResponseDao {
    Response create(Connection connection, Response Response);

    List<Response> findByEmployee(Connection connection, String email, Boolean asistencia);

    Boolean existsResponse(Connection connection,Long responseId, String email);
    void remove(Connection connection, Long ResponseId) throws InstanceNotFoundException;

    Response findResponseById(Connection connection, Long responseId)
            throws InstanceNotFoundException;
}