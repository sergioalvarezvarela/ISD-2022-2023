package EventService.Exception;

public class AlreadyCanceledException extends Exception {
    public AlreadyCanceledException(String mensaje, Long eventId) {
        super(mensaje+eventId);
    }
}
