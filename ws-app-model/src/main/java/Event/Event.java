package Event;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event {
    private Long eventId;
    private String eventName;
    private LocalDateTime celebrationDate;
    private LocalDateTime creationDate;
    private int runtime;
    private String eventDescription;

    private boolean eventState;

    private int attendance;
    private int not_attendance;


    public Event(String eventName, String eventDescription, int runtime, LocalDateTime celebrationDate) {
        super();
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.runtime = runtime;
        this.celebrationDate = (celebrationDate!=null) ? celebrationDate.withNano(0): null;
    }

    public Event(Long eventId, String eventName, String eventDescription,LocalDateTime celebrationDate, LocalDateTime creationDate, int runtime,  boolean event_state, int attendance, int not_attendance) {
        this(eventName, eventDescription, runtime, celebrationDate);
        this.eventId = eventId;
        this.creationDate = (creationDate!=null) ? creationDate.withNano(0) :null;
        this.eventState = event_state;
        this.attendance = attendance;
        this.not_attendance = not_attendance;

    }

    public Event(Event event) {
        this(event.getEventId(), event.getName(), event.getDescription(), event.getCelebrationDate(), event.getCreationDate(), event.getRuntime(), event.getEventState(), event.getAttendance(), event.getNot_Attendance());
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return eventName;
    }

    public void setName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return eventDescription;
    }

    public void setDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Boolean getEventState() {
        return eventState;
    }

    public void setEventState(Boolean eventState) {
        this.eventState = eventState;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getNot_Attendance() {
        return not_attendance;
    }

    public void setNot_Attendance(int not_attendance) {
        this.not_attendance = not_attendance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate=  (creationDate!= null) ? creationDate.withNano(0):null;
    }

    public LocalDateTime getCelebrationDate() {
        return celebrationDate;
    }

    public void setCelebrationDate(LocalDateTime celebrationDate) {

        this.celebrationDate = (celebrationDate != null) ? celebrationDate.withNano(0):null;
    }



    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", name=" + eventName + ", creationDate=" + creationDate + ", celebrationDate=" + celebrationDate + ",runtime=" + runtime + ",eventState=" + eventState + ",attendance=" + attendance + ", not attendance=" + not_attendance+ "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return runtime == event.runtime && eventState == event.eventState && attendance == event.attendance && not_attendance == event.not_attendance && Objects.equals(eventId, event.eventId) && Objects.equals(eventName, event.eventName) && Objects.equals(celebrationDate, event.celebrationDate) && Objects.equals(creationDate, event.creationDate) && Objects.equals(eventDescription, event.eventDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, celebrationDate, creationDate, runtime, eventDescription, eventState, attendance, not_attendance);
    }
}

