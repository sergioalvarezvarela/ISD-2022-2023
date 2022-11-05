package EventService.Exception;

public class AlreadyResponseException extends Exception {
    public AlreadyResponseException(String mensaje,Long responseId ) {
        super(mensaje+responseId);
    }
}