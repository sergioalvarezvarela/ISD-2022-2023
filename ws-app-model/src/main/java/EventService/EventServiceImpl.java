package EventService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;
import javax.sql.DataSource;

import Event.Event;
import EventService.Exception.AlreadyDoneException;
import EventService.Exception.OutOfTimeException;
import Response.Response;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.validation.PropertyValidator;
import Event.SqlEventDao;
import Event.SqlEventDaoFactory;
import Response.SqlResponseDaoFactory;
import Response.SqlResponseDao;



public class EventServiceImpl implements EventService{
    private final DataSource dataSource;
    private SqlEventDao eventDao = null;
    private SqlResponseDao responseDao = null;

    public EventServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        eventDao = SqlEventDaoFactory.getDao();
        responseDao = SqlResponseDaoFactory.getDao();
    }
    private void validateEvent(Event event) throws InputValidationException {
        PropertyValidator.validateMandatoryString("title", event.getName());
        PropertyValidator.validateMandatoryString("description", event.getDescription());

        if((event.getRuntime()<0) || event.getCelebrationDate() == null || (LocalDateTime.now().plusDays(1).isAfter(event.getCelebrationDate()))){throw new InputValidationException("Los datos introducidos no son válidos");}
    }

    @Override
    public Event addEvent(Event event) throws InputValidationException {
        validateEvent(event);
        event.setCreationDate(LocalDateTime.now());
        event.setEventState(true);
        event.setAttendance(0);
        event.setNot_Attendance(0);
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Event createdEvent = eventDao.create(connection, event);

                /* Commit. */
                connection.commit();

                return createdEvent;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response addResponse(Response response) throws InputValidationException, InstanceNotFoundException, OutOfTimeException {
        return null;
    }

    @Override
    public void CancelEvent(Long eventId, Boolean status) throws InputValidationException, InstanceNotFoundException, AlreadyDoneException,OutOfTimeException {
        if (status){ throw new InputValidationException("El valor de status no es correcto para ser cancelado");}
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                Event evento1 = eventDao.findEventById(connection,eventId);
                    if(LocalDateTime.now().isAfter(evento1.getCelebrationDate())){
                        connection.rollback();
                        throw new OutOfTimeException("El tiempo de cancelación ha expirado para el evento: ", eventId);
                    }
                Boolean iscancelled = eventDao.isAlreadyCancelled(connection,eventId,status);
                if (iscancelled){
                    connection.rollback();
                    throw new AlreadyDoneException("El evento ya ha sido cancelado",eventId);
                }
                /* Do work. */
                eventDao.cancel(connection, eventId,status);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event findEvent(Long eventId) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return eventDao.findEventById(connection, eventId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findEvents(LocalDateTime dateIn, LocalDateTime dateEnd, String keywords) throws InputValidationException {
        return null;
    }

    @Override
    public List<Response> findResponse(String email, boolean Asist) throws InputValidationException {
        return null;
    }

}
