package EventService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;

import javax.sql.DataSource;


import Event.Event;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.AlreadyResponseException;
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


public class EventServiceImpl implements EventService {
    private final DataSource dataSource;
    private SqlEventDao eventDao = null;
    private SqlResponseDao responseDao = null;

    //Validaciones InputValidationException
    //  -Same addResponse
    //  -dia actual - response.responseDate < 24 h
    public EventServiceImpl() {
        dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        eventDao = SqlEventDaoFactory.getDao();
        responseDao = SqlResponseDaoFactory.getDao();
    }

    private void validateEvent(Event event) throws InputValidationException {
        PropertyValidator.validateMandatoryString("title", event.getName());
        PropertyValidator.validateMandatoryString("description", event.getDescription());

        if ((event.getRuntime() <= 0) || event.getCelebrationDate() == null || (LocalDateTime.now().plusDays(1).isAfter(event.getCelebrationDate()))) {
            throw new InputValidationException("Los datos introducidos no son válidos");
        }
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

    private void validateEmail(String email) throws InputValidationException {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(regex)) ;
        else {
            throw new InputValidationException("El email introducido no es válido");
        }
    }


    @Override
    public void CancelEvent(Long eventId, Boolean status) throws
            InputValidationException, InstanceNotFoundException, AlreadyCanceledException, OutOfTimeException {
        if (status) {
            throw new InputValidationException("El valor de status no es correcto para ser cancelado");
        }
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                Event evento1 = eventDao.findEventById(connection, eventId);
                if (LocalDateTime.now().isAfter(evento1.getCelebrationDate())) {
                    connection.rollback();
                    throw new OutOfTimeException("El tiempo de cancelación ha expirado para el evento: ", eventId);
                }
                Boolean iscancelled = eventDao.isAlreadyCancelled(connection, eventId, status);
                if (iscancelled) {
                    connection.rollback();
                    throw new AlreadyCanceledException("El evento ya ha sido cancelado", eventId);
                }
                /* Do work. */
                eventDao.CancelEvent(connection, eventId, status);

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

    public Response addResponse(Response response) throws
            InputValidationException, InstanceNotFoundException, OutOfTimeException, AlreadyCanceledException, AlreadyResponseException {
        validateEmail(response.getEmail());
        response.setResponseDate(LocalDateTime.now());


        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                Event event1 = eventDao.findEventById(connection, response.getEventId());
                if (LocalDateTime.now().isAfter(event1.getCelebrationDate().minusDays(1))) {
                    connection.rollback();
                    throw new OutOfTimeException("Imposible responder a este evento fuera de tiempo: ", event1.getEventId());
                }
                if (!event1.getEventState()) {
                    connection.rollback();
                    throw new AlreadyCanceledException("No se puede responder a un evento ya cancelado: ", event1.getEventId());
                }
                if (response.getAsistencia()) {
                    eventDao.updateAttendance(connection, event1.getEventId(), response.getAsistencia(), event1.getAttendance() + 1);
                } else {
                    eventDao.updateAttendance(connection, event1.getEventId(), response.getAsistencia(), event1.getNot_Attendance() + 1);
                }
                if (responseDao.existsResponse(connection, response.getEventId(), response.getEmail())) {
                    connection.rollback();
                    throw new AlreadyResponseException("No puedes responder a un evento al que ya has respondido: ", event1.getEventId());
                }

                /* Do work. */
                Response createdResponse = responseDao.create(connection, response);


                /* Commit. */
                connection.commit();

                return createdResponse;
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
    public List<Event> findEventsbyDate(LocalDate dateIn, LocalDate dateEnd, String keywords) throws InputValidationException {
        if ((dateIn == null) || (dateEnd == null)){
            throw new InputValidationException("Las datas no pueden ser nulas");
        }
        if(keywords==null){
            keywords= "";
        }
        try (Connection connection = dataSource.getConnection()) {
            return eventDao.findEventByDateOrAndKeyWord(connection, dateIn, dateEnd, keywords);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Response> findResponsebyEmail(String email, Boolean Asist) throws InputValidationException {
        validateEmail(email);
        if (Asist==null) {
            throw new InputValidationException("El valor no puede ser nulo");
        }
        try (Connection connection = dataSource.getConnection()) {
            return responseDao.findByEmployee(connection, email, Asist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
