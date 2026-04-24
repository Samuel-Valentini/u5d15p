package samuelvalentini.u5d15p.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super("Record not found in the database: " + message);
    }
}
