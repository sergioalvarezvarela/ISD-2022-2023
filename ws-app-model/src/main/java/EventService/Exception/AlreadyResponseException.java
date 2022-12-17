package EventService.Exception;

public class AlreadyResponseException extends Exception {
    private Long eventId;
    public AlreadyResponseException(Long eventId ) {
        super("Error, you have already response to this event: "+ eventId);
        this.eventId = eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}