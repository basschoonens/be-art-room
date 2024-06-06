package nl.novi.theartroom.exception;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
