package es.udc.ws.app.client.service.exceptions;

public class ClientAlreadyResponseException extends Exception {
    private Long eventId;

    public ClientAlreadyResponseException(Long eventId) {
        super("Error, you have already response to this event: " + eventId);
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
