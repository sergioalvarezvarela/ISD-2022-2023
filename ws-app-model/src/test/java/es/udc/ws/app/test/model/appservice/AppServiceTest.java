package es.udc.ws.app.test.model.appservice;

import Event.Event;
import EventService.Exception.AlreadyDoneException;
import EventService.Exception.OutOfTimeException;
import Response.SqlResponseDao;
import Response.SqlResponseDaoFactory;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import EventService.EventServiceFactory;
import EventService.EventService;
import Event.SqlEventDaoFactory;
import Event.SqlEventDao;

import javax.sql.DataSource;

import static es.udc.ws.app.model.util.ModelConstants.APP_DATA_SOURCE;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private void removeEvent(Long eventId) throws InstanceNotFoundException {
        DataSource dataSource = DataSourceLocator.getDataSource(APP_DATA_SOURCE);
        try(Connection connection=dataSource.getConnection()){
            try {
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);
                eventDao.remove(connection,eventId);
                connection.commit();
            } catch (SQLException e){
                connection.rollback();
                throw new RuntimeException();
            } catch (RuntimeException | Error e){
                connection.rollback();
                throw e;
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime formatearfecha(String fecha_input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(fecha_input, formatter);
    }

    @Test

    public void testAddEventandFindEvent() throws InputValidationException, InstanceNotFoundException {

        Event event1 = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
        event1 = eventService.addEvent(event1);
        assertEquals(event1, eventService.findEvent(event1.getEventId()));
        removeEvent(event1.getEventId());

    }

    @Test
    public void RemoveTest() throws InputValidationException, InstanceNotFoundException {
        Event event1 = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
        event1 = eventService.addEvent(event1);
        removeEvent(event1.getEventId());
        Event finalEvent = event1;
        assertThrows(InstanceNotFoundException.class, () -> eventService.findEvent(finalEvent.getEventId()));
    }


    @Test
    public void NotRemovableTest() throws InputValidationException, InstanceNotFoundException {
        Event event1 = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
        event1 = eventService.addEvent(event1);
        Event finalEvent = event1;
        assertThrows(InstanceNotFoundException.class, () -> eventService.findEvent(finalEvent.getEventId()+100));
        removeEvent(event1.getEventId());
    }

    @Test
    public void testAddInvalidEvent() {
        //Check event title not null
        assertThrows(InputValidationException.class, () -> {
            Event event = new Event(null, "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
        });
        //Check description not null
        assertThrows(InputValidationException.class, () -> {
            Event event = new Event("Evento 1", null, 36,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
        });

        //Check runtime not <0
        assertThrows(InputValidationException.class, () -> {
            Event event = new Event("Evento 1", "Fiesta muy divertida", -1,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
        });

        //Check celebrationdate not null
        assertThrows(InputValidationException.class, () -> {
            Event event = new Event("Evento 1", "Fiesta muy divertida", 36,null);
            Event event1= eventService.addEvent(event);
        });

    }
    @Test
    public void testCancel() throws InputValidationException, AlreadyDoneException, InstanceNotFoundException, OutOfTimeException {
        Event event1 = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
        event1 = eventService.addEvent(event1);
        eventService.CancelEvent(event1.getEventId(),false);
        Event event2= eventService.findEvent(event1.getEventId());
        event1.setEventState(false);
        assertEquals(event2,event1);
    }
    @Test
    public void testCancelException() {
        assertThrows(AlreadyDoneException.class, () -> {
            Event event = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
             eventService.CancelEvent(event1.getEventId(),false);
             eventService.CancelEvent(event1.getEventId(),false);
        });
        assertThrows(InstanceNotFoundException.class, () -> {
            Event event = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
            eventService.CancelEvent(event1.getEventId()+100,false);
        });
        assertThrows(InputValidationException.class, () -> {
            Event event = new Event("Evento 1", "Fiesta muy divertida", 36,formatearfecha("21/12/2022 22:44:45"));
            Event event1= eventService.addEvent(event);
            eventService.CancelEvent(event1.getEventId(),true);
        });
    }


}
