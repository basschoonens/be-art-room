package nl.novi.theartroom.exception.model;

public class RatingNotFoundException extends RuntimeException{

    public RatingNotFoundException(String message) {
        super(message);
    }

    public RatingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
