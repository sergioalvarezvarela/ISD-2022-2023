package es.udc.ws.app.client.service.dto;

public class ClientResponseDto {

    private Long responseId;
    private Long eventId;

    private String workerEmail;

    private Boolean attendance;


    public ClientResponseDto() {
    }


    public ClientResponseDto(Long responseId, Long eventId, String workerEmail, Boolean attendance) {
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


    public Boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "ClientResponseDto{" +
                "responseId=" + responseId +
                ", eventId=" + eventId +
                ", workerEmail='" + workerEmail + '\'' +
                ", attendance=" + attendance +
                '}';
    }
}


