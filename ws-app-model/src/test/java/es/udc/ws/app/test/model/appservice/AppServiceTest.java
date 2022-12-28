package es.udc.ws.app.test.model.appservice;

import Event.Event;
import Event.SqlEventDao;
import Event.SqlEventDaoFactory;
import EventService.EventService;
import EventService.EventServiceFactory;
import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.AlreadyResponseException;
import EventService.Exception.OutOfTimeException;
import Response.Response;
import Response.SqlResponseDao;
import Response.SqlResponseDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;
import static org.junit.jupiter.api.Assertions.*;

public class AppServiceTest {

    private static EventService eventService = null;
    private static SqlEventDao eventDao = null;

    private static SqlResponseDao responseDao = null;


    @BeforeAll
    public static void init() {
        DataSource dataSource = new SimpleDataSource();
        DataSourceLocator.addDataSource(APP_DATA_SOURCE, dataSource);
        eventService = EventServiceFactory.getService();
        eventDao = SqlEventDaoFactory.getDao();
        responseDao = SqlResponseDaoFactory.getDao();
    }

    private LocalDateTime formatearfecha(String fecha_input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(fecha_input, formatter);
    }

    private LocalDate formatearfechaAMD(String fecha_input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha_input, formatter);
    }


    private Event getValidEvent(String title) {
        return new Event(title, "descripcion", 36, formatearfecha("21/12/2023 22:44:45"));
    }

    private Event getValidEvent() {
        return getValidEvent("Event Name");
    }

    private Event createEvent(Event event) {

        Event addedEvent = null;
        try {
            addedEvent = eventService.addEvent(event);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedEvent;
    }

    private Response getValidResponse(Long eventId, Boolean asist) {
        return new Response(eventId, "usuario@gmail.com", asist);
    }

    private Response createResponse(Response response) throws InstanceNotFoundException, AlreadyCanceledException, OutOfTimeException, AlreadyResponseException {

        Response addedResponse = null;
        try {
            addedResponse = eventService.addResponse(response);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedResponse;
    }


    private void removeEvent(Long eventId) {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                eventDao.remove(connection, eventId);
                connection.commit();
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException();
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeResponse(Long responseId) {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        try (Connection connection = dataSource.getConnection()) {
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                responseDao.remove(connection, responseId);
                connection.commit();
            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException();
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Response findResponse(Long responseId) {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        try (Connection connection = dataSource.getConnection()) {
            return responseDao.findResponseById(connection, responseId);
        } catch (InstanceNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateEvent(Event event) {

        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                eventDao.update(connection, event);

                /* Commit. */
                connection.commit();

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw new RuntimeException(e);
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


    @Test

    public void testAddEventandFindEvent() throws InputValidationException, InstanceNotFoundException {
        Event event = getValidEvent();
        Event addedEvent = null;
        try {
            addedEvent = eventService.addEvent(event);
            assertEquals(addedEvent, eventService.findEvent(addedEvent.getEventId()));
        } finally {
            // Clear Database
            if (addedEvent != null) {
                removeEvent(addedEvent.getEventId());
            }
        }
    }

    @Test
    public void testAddInvalidEvent() {
        //Check event title not null
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setName(null);
            Event event1 = eventService.addEvent(event);
        });
        // Check event title not empty
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setName("");
            Event event1 = eventService.addEvent(event);

        });
        //Check description not null
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setDescription(null);
            Event event1 = eventService.addEvent(event);
        });
        //Check description not empty
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setDescription("");
            Event event1 = eventService.addEvent(event);
        });
        //Check runtime not <0
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setRuntime(-1);
            Event event1 = eventService.addEvent(event);
        });
        //Check runtime not =0
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setRuntime(0);
            Event event1 = eventService.addEvent(event);
        });

        //Check celebrationdate not null
        assertThrows(InputValidationException.class, () -> {
            Event event = getValidEvent();
            event.setCelebrationDate(null);
            Event event1 = eventService.addEvent(event);
        });
    }

    @Test
    public void RemoveEventTest() {
        Event event = getValidEvent();
        event = createEvent(event);
        removeEvent(event.getEventId());
        Event finalEvent = event;
        assertThrows(InstanceNotFoundException.class, () -> eventService.findEvent(finalEvent.getEventId()));
    }

    @Test
    public void RemoveEventTestException() {
        assertThrows(RuntimeException.class, () -> removeEvent(1000L));
    }


    @Test
    public void TestUpdateEvent() throws InstanceNotFoundException {
        Event event = getValidEvent();
        try {
            event = createEvent(event);
            event.setAttendance(10);
            event.setRuntime(25);
            event.setEventState(false);
            event.setDescription("Evento de prueba para actualizar");
            event.setName("Evento test");
            updateEvent(event);
            assertEquals(event, eventService.findEvent(event.getEventId()));
        } finally {
            if (event != null) {
                removeEvent(event.getEventId());
            }
        }
    }

    @Test
    public void TestUpdateEventException() {
        Event event1 = getValidEvent();
        Long id = null;
        try {
            event1 = createEvent(event1);
            id = event1.getEventId();
            event1.setEventId(1000L);
            Event finalEvent = event1;
            assertThrows(RuntimeException.class, () -> updateEvent(finalEvent));
        } finally {
            if (event1 != null) {
                removeEvent(id);
            }
        }
    }

    @Test
    public void testFindByDateorKeyword() throws InputValidationException {
        Event event = getValidEvent();
        Event event2 = getValidEvent();
        Event event3 = getValidEvent();
        Event event4 = getValidEvent();
        Event event5 = getValidEvent();
        Event event6 = getValidEvent();
        try {
            List<Event> eventlist = new ArrayList<>();
            event = createEvent(event);
            eventlist.add(event);
            event2.setCelebrationDate(formatearfecha("22/12/2023 22:44:45"));
            event2 = createEvent(event2);
            eventlist.add(event2);
            event3.setCelebrationDate(formatearfecha("31/12/2023 20:34:45"));
            event3 = createEvent(event3);
            event4.setCelebrationDate(formatearfecha("27/12/2023 22:44:44"));
            event4= createEvent(event4);
            assertEquals(eventlist, eventService.findEventsbyDate(formatearfechaAMD("20/12/2023"), formatearfechaAMD("26/12/2023"), null));


            //Check Keyword
            List<Event> eventlist2 = new ArrayList<>();
            event5.setCelebrationDate(formatearfecha("23/12/2023 22:44:45"));
            event5.setDescription("La clave para aprobar");
            event5 = createEvent(event5);
            eventlist2.add(event5);
            event6.setCelebrationDate(formatearfecha("25/12/2023 22:44:45"));
            event6.setDescription("Fiesta CLAVE");
            event6 = createEvent(event6);
            eventlist2.add(event6);
            assertEquals(eventlist2, eventService.findEventsbyDate(formatearfechaAMD("20/12/2023"), formatearfechaAMD("26/12/2023"), "clave"));
            //Check Keyword not found
            List<Event> eventlist3 = new ArrayList<>();
            assertEquals(eventlist3, eventService.findEventsbyDate(formatearfechaAMD("20/12/2023"), formatearfechaAMD("26/12/2023"), "NOT FOUND"));
            //Check dates not found
            List<Event> eventlist4 = new ArrayList<>();
            assertEquals(eventlist4, eventService.findEventsbyDate(formatearfechaAMD("20/12/2022"), formatearfechaAMD("26/12/2022"), ""));


        } finally {
            removeEvent(event.getEventId());
            removeEvent(event2.getEventId());
            removeEvent(event3.getEventId());
            removeEvent(event4.getEventId());
            removeEvent(event5.getEventId());
            removeEvent(event6.getEventId());
        }
    }

    @Test
    public void testFindByDateorKeywordException() {
        assertThrows(InputValidationException.class, () -> eventService.findEventsbyDate(formatearfechaAMD("20/12/2022"),null,null));
    }

    @Test
    public void testCancel() throws InputValidationException, AlreadyCanceledException, InstanceNotFoundException, OutOfTimeException {
        Event event = getValidEvent();
        try {
            event = createEvent(event);
            eventService.CancelEvent(event.getEventId());
            Event event2 = eventService.findEvent(event.getEventId());
            event.setEventState(false);
            assertEquals(event2, event);

        } finally {
            if (event != null) {
                removeEvent(event.getEventId());
            }
        }
    }

    @Test
    public void testCancelException() {
        Event event = getValidEvent();
        event = createEvent(event);
        Event finalEvent = event;
        assertThrows(AlreadyCanceledException.class, () -> {
            eventService.CancelEvent(finalEvent.getEventId());
            eventService.CancelEvent(finalEvent.getEventId());
        });
        Event finalEvent1 = event;
        assertThrows(InstanceNotFoundException.class, () -> {
            eventService.CancelEvent(finalEvent1.getEventId() + 100);
            removeEvent(finalEvent1.getEventId());
        });
        Event finalEvent3 = event;
        assertThrows(OutOfTimeException.class, () -> {
            finalEvent3.setCelebrationDate(formatearfecha("02/11/2022 11:22:22"));
            updateEvent(finalEvent3);
            eventService.CancelEvent(finalEvent3.getEventId());

        });
        removeEvent(event.getEventId());
    }


    @Test
    public void testAddResponseandFindResponse() throws InstanceNotFoundException, AlreadyCanceledException, OutOfTimeException, AlreadyResponseException {
        Event event = getValidEvent();
        try {
            event = createEvent(event);
            Response response1 = getValidResponse(event.getEventId(), true);
            Response response2 = getValidResponse(event.getEventId(), false);
            response1 = createResponse(response1);
            response2.setEmail("prueba2@gmail.com");
            response2 = createResponse(response2);
            assertEquals(response1, findResponse(response1.getResponseId()));
            assertEquals(response2, findResponse(response2.getResponseId()));
            assertNotEquals(event, eventService.findEvent(event.getEventId()));
            removeResponse(response1.getResponseId());
            removeResponse(response2.getResponseId());
        } finally {

            if (event != null) {
                removeEvent(event.getEventId());
            }
        }
    }

    @Test
    public void testAddResponseandFindResponseException() {
        Event event = getValidEvent();
        Event event1 = createEvent(event);
        assertThrows(InstanceNotFoundException.class, () -> {
            Response response1 = getValidResponse(1000L, true);
            response1 = createResponse(response1);
            removeResponse(response1.getResponseId());
        });
        assertThrows(AlreadyCanceledException.class, () -> {
            eventService.CancelEvent(event1.getEventId());
            Response response1 = getValidResponse(event1.getEventId(), true);
            response1 = createResponse(response1);
        });
        assertThrows(AlreadyResponseException.class, () -> {
            event1.setEventState(true);
            updateEvent(event1);
            Response response1 = getValidResponse(event1.getEventId(), true);
            response1 = createResponse(response1);
            Response response2 = createResponse(response1);
        });
        assertThrows(OutOfTimeException.class, () -> {
            event1.setCelebrationDate(LocalDateTime.now().plusHours(1));
            updateEvent(event1);
            Response response1 = getValidResponse(event1.getEventId(), true);
            response1 = createResponse(response1);
        });
        assertThrows(NullPointerException.class, () -> {
            Response response1 = getValidResponse(event1.getEventId(), null);
        });
        assertThrows(InputValidationException.class, () -> {
            Response response1 = getValidResponse(event1.getEventId(), true);
            response1.setEmail("novalido");
            response1= eventService.addResponse(response1);
        });
        assertThrows(RuntimeException.class, () -> findResponse(1000L));
        removeEvent(event1.getEventId());

    }

    @Test
    public void RemoveResponseTest() throws AlreadyResponseException, InstanceNotFoundException, OutOfTimeException, AlreadyCanceledException {
        Event event = getValidEvent();
        try {
            event = createEvent(event);
            Response response1 = getValidResponse(event.getEventId(), true);
            response1 = createResponse(response1);
            removeResponse(response1.getResponseId());
            Response finalResponse = response1;
            assertThrows(RuntimeException.class, () -> findResponse(finalResponse.getResponseId()));
        } finally {

            if (event != null) {
                removeEvent(event.getEventId());
            }
        }
    }

    @Test
    public void RemoveResponseTestException() {
        assertThrows(RuntimeException.class, () -> removeResponse(1000L));
    }

    @Test
    public void testfindByEmail() throws InputValidationException, InstanceNotFoundException, OutOfTimeException, AlreadyResponseException, AlreadyCanceledException {
        Event event = getValidEvent();
        try {
            List<Response> responses1 = new ArrayList<>();
            List<Response> responses2 = new ArrayList<>();
            List<Response> responses3 = new ArrayList<>();
            event = createEvent(event);
            Event event1 = createEvent(event);
            Event event2 = createEvent(event);
            Event event3 = createEvent(event);
            Event event4 = createEvent(event);
            Response response1 = getValidResponse(event.getEventId(), true);
            Response response2 = getValidResponse(event1.getEventId(), false);
            Response response3 = getValidResponse(event2.getEventId(), false);
            Response response4 = getValidResponse(event3.getEventId(), true);
            Response response5 = getValidResponse(event4.getEventId(), true);
            Response response6 = getValidResponse(event4.getEventId(), true);
            response1 = createResponse(response1);
            response2 = createResponse(response2);
            response3 = createResponse(response3);
            response4 = createResponse(response4);
            response5 = createResponse(response5);
            response6.setEmail("usuario2@gmail.com");
            response6 = createResponse(response6);
            responses3.add(response1);
            responses3.add(response2);
            responses3.add(response3);
            responses3.add(response4);
            responses3.add(response5);
            responses1.add(response1);
            responses1.add(response4);
            responses1.add(response5);
            assertEquals(responses2, eventService.findResponsebyEmail("moli@gmail.com", true));
            assertEquals(responses1, eventService.findResponsebyEmail(response1.getEmail(), true));
            assertEquals(responses3, eventService.findResponsebyEmail(response1.getEmail(), false));
            removeResponse(response1.getResponseId());
            removeResponse(response2.getResponseId());
            removeResponse(response3.getResponseId());
            removeResponse(response4.getResponseId());
            removeResponse(response5.getResponseId());
            removeResponse(response6.getResponseId());
            removeEvent(event1.getEventId());
            removeEvent(event2.getEventId());
            removeEvent(event3.getEventId());
            removeEvent(event4.getEventId());
        } finally {
            if (event != null) {
                removeEvent(event.getEventId());
            }
        }
    }

    @Test
    public void TestFindByEmailException() {
        assertThrows(InputValidationException.class, () ->
                eventService.findResponsebyEmail("pepe@gmail.com", null));
    }


}
