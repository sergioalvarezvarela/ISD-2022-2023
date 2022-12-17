package es.udc.ws.app.client.service.exceptions;

public class ClientAlreadyCanceledException extends Exception {

    private Long eventId;

    public ClientAlreadyCanceledException(Long eventId) {
        super(" Error, this event is already canceled: " + eventId);
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}