package nl.novi.theartroom.exception.database;

import org.springframework.web.bind.annotation.ResponseStatus;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
