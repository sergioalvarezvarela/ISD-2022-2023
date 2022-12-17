package es.udc.ws.app.restservice.dto;

import java.time.LocalDateTime;

public class RestResponseDto {

    private Long responseId;
    private Long eventId;

    private String workerEmail;

    private Boolean attendance;


    public RestResponseDto() {
    }


    public RestResponseDto(Long responseId, Long eventId, String workerEmail, Boolean attendance) {
        this.responseId = responseId;
        this.eventId = eventId;
        this.workerEmail = workerEmail;
        this.attendance = attendance;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getWorkerEmail() {
        return workerEmail;
    }

    public void setWorkerEmail(String workerEmail) {
        this.workerEmail = workerEmail;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "RestResponseDto{" +
                "responseId=" + responseId +
                ", eventId=" + eventId +
                ", workerEmail='" + workerEmail + '\'' +
                ", attendance=" + attendance +
                '}';
    }
}


