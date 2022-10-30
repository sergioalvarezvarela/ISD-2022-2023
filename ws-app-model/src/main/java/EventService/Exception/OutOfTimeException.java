package EventService.Exception;

public class OutOfTimeException extends Exception {
    public OutOfTimeException(String mensaje,Long eventId ) {
        super(mensaje+eventId);
    }
}
