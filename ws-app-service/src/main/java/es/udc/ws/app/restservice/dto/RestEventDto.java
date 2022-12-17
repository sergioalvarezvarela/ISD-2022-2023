package es.udc.ws.app.restservice.dto;

import java.time.LocalDateTime;

public class RestEventDto {


    private Long eventId;
    private String eventName;
    private LocalDateTime celebrationDate;
    private int runtime;
    private String eventDescription;

    private boolean eventState;

    private int attendance;
    private int totalAttendance;


    public RestEventDto() {
    }

    public RestEventDto(Long eventId, String eventName, LocalDateTime celebrationDate,
                          int runtime, String eventDescription, boolean eventState, int attendance, int totalAttendance) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.celebrationDate = celebrationDate;
        this.runtime = runtime;
        this.eventDescription = eventDescription;
        this.eventState = eventState;
        this.attendance = attendance;
        this.totalAttendance = totalAttendance;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long saleId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(Long movieId) {
        this.eventName = eventName;
    }

    public LocalDateTime getCelebrationDate() {
        return celebrationDate;
    }

    public void setCelebrationDate(LocalDateTime celebrationDate) {
        this.celebrationDate = celebrationDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public boolean geteventState() {
        return eventState;
    }

    public void setEventState(boolean eventState) {
        this.eventState = eventState;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    public int getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    @Override
    public String toString() {
        return "RestEventDto{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", celebrationDate=" + celebrationDate +
                ", runtime=" + runtime +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventState=" + eventState +
                ", attendance=" + attendance +
                ", totalAttendance=" + totalAttendance +
                '}';
    }
}


