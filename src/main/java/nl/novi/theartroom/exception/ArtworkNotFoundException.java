package nl.novi.theartroom.exception;

public class ArtworkNotFoundException extends RuntimeException{

    public ArtworkNotFoundException(String message) {
        super(message);
    }

    public ArtworkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
