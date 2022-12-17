package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientEventDto {


    private Long eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime celebrationDate;
    private LocalDateTime endDate;


    private boolean eventState;

    private int attendance;
    private int totalAttendance;


    public ClientEventDto() {
    }

    public ClientEventDto(Long eventId, String eventName, String eventDescription, LocalDateTime celebrationDate,
                          LocalDateTime endDate, boolean eventState, int attendance, int totalAttendance) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.celebrationDate = celebrationDate;
        this.endDate = endDate;
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


    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public void seteventState(boolean eventState) {
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
        return "ClientEventDto{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", celebrationDate=" + celebrationDate +
                ", endDate=" + endDate +
                ", eventState=" + eventState +
                ", attendance=" + attendance +
                ", totalAttendance=" + totalAttendance +
                '}';
    }
}


