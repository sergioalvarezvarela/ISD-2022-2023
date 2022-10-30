package EventService.Exception;

public class AlreadyDoneException extends Exception {
    public AlreadyDoneException(String mensaje,Long eventId ) {
        super(mensaje+eventId);
    }
}
