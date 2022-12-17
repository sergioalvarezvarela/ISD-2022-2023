package es.udc.ws.app.restservice.json;

import EventService.Exception.AlreadyCanceledException;
import EventService.Exception.AlreadyResponseException;
import EventService.Exception.OutOfTimeException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class AppExceptionToJsonConversor {

    public static ObjectNode toAlreadyCanceledException(AlreadyCanceledException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

            exceptionObject.put("errorType", "AlreadyCanceledException");
        exceptionObject.put("eventId", (ex.getEventId() != null) ? ex.getEventId() : null);
        return exceptionObject;
    }

    public static ObjectNode toAlreadyResponseException(AlreadyResponseException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

        exceptionObject.put("errorType", "AlreadyResponseException");
        exceptionObject.put("eventId", (ex.getEventId() != null) ? ex.getEventId() : null);
        return exceptionObject;
    }

    public static ObjectNode toOutOfTimeException(OutOfTimeException ex) {

        ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

            exceptionObject.put("errorType", "OutOfTimeException");
        exceptionObject.put("Id", (ex.getId() != null) ? ex.getId() : null);
        return exceptionObject;
    }
}
