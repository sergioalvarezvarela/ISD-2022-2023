package Response;

import es.udc.ws.util.exceptions.InputValidationException;

import java.sql.Connection;

public class Jdbc3CcSqlResponseDao extends AbstractSqlResponseDao {

    @Override
    public Response create(Connection connection, Response Response) throws InputValidationException {
        return null;
    }
}
