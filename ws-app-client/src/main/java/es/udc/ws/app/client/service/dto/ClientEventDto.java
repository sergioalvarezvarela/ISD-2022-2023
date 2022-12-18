package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientEventDto {


    private Long eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime celebrationDate;
    private LocalDateTime endDate;


    private Boolean eventState;

    private Integer attendance;
    private Integer totalAttendance;


    public ClientEventDto() {
    }

    public ClientEventDto(Long eventId, String eventName, String eventDescription, LocalDateTime celebrationDate,
                          LocalDateTime endDate, Boolean eventState, Integer attendance, Integer totalAttendance) {
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

    public Boolean getEventState() {
        return eventState;
    }

    public void setEventState(Boolean eventState) {
        this.eventState = eventState;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(Integer totalAttendance) {
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


