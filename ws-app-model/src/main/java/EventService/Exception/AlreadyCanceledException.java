package EventService.Exception;

import java.time.LocalDateTime;

public class AlreadyCanceledException extends Exception {

    private Long eventId;
    public AlreadyCanceledException(Long eventId) {
        super(" Error, this event is already canceled: "+ eventId);
        this.eventId= eventId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
