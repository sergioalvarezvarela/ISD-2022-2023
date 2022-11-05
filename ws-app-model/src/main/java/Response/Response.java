package Response;

import java.time.LocalDateTime;
import java.util.Objects;

public class Response {

    private Long eventId;

    private Long responseId;
    private LocalDateTime responseDate;
    private String email;
    private boolean asistencia;

    public Response(Long eventId, String email, boolean asistencia) {
        super();
        this.eventId = eventId;
        this.email = email;
        this.asistencia = asistencia;
    }

    public Response(Long responseId,Long eventId, String email,LocalDateTime responseDate, boolean asistencia) {
        this(eventId,email,asistencia);
        this.responseId = responseId;
        this.responseDate = (responseDate!= null) ? responseDate.withNano(0):null;
    }

    public Response(Response response) {
        this(response.getResponseId(),response.getEventId(), response.getEmail(),response.getResponseDate(), response.getAsistencia());
    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }

    public LocalDateTime getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = (responseDate!=null) ? responseDate.withNano(0):null;
    }

    @Override
    public String toString() {
        return "Response [eventId=" + eventId + ", responseId=" + responseId + ", email=" + email + ", responseDate=" + responseDate + ", asistencia=" + asistencia +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return asistencia == response.asistencia && Objects.equals(eventId, response.eventId) && Objects.equals(responseId, response.responseId) && Objects.equals(responseDate, response.responseDate) && Objects.equals(email, response.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, responseId, responseDate, email, asistencia);
    }
}