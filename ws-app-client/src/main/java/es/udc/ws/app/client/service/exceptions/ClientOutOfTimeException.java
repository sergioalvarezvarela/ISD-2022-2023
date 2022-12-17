package es.udc.ws.app.client.service.exceptions;

public class ClientOutOfTimeException extends Exception {
    private Long id;

    public ClientOutOfTimeException(Long id) {
        super("Error, acción fuera de rango de tiempo permitido para: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
