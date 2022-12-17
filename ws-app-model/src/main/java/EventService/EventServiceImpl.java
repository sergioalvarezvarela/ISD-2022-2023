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
    public void CancelEvent(Long eventId) throws
            InstanceNotFoundException, AlreadyCanceledException, OutOfTimeException {
        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                Event evento1 = eventDao.findEventById(connection, eventId);
                if (LocalDateTime.now().isAfter(evento1.getCelebrationDate())) {
                    connection.rollback();
                    throw new OutOfTimeException(eventId);
                }
                Boolean isnotcanceled = eventDao.findEventById(connection, eventId).getEventState();
                if (!isnotcanceled) {
                    connection.rollback();
                    throw new AlreadyCanceledException(eventId);
                }
                /* Do work. */
                eventDao.CancelEvent(connection, eventId);

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
                    throw new OutOfTimeException(event1.getEventId());
                }
                if (!event1.getEventState()) {
                    connection.rollback();
                    throw new AlreadyCanceledException(event1.getEventId());
                }
                if (responseDao.existsResponse(connection, response.getEventId(), response.getEmail())) {
                    connection.rollback();
                    throw new AlreadyResponseException(event1.getEventId());
                }
                if (response.getAsistencia()) {
                    event1.setAttendance(event1.getAttendance()+1);
                    eventDao.update(connection, event1);
                } else {
                    event1.setNot_Attendance(event1.getNot_Attendance() + 1);
                    eventDao.update(connection,event1);
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
        if  (dateEnd == null){
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
        if (Asist==null){
            throw new InputValidationException("El valor introducido no es válido");
        }
        try(Connection connection = dataSource.getConnection()){
            return responseDao.findByEmployee(connection,email, Asist);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


}
