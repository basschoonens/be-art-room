package nl.novi.theartroom.exception.model;

public class ArtworkNotFoundException extends RuntimeException{

    public ArtworkNotFoundException(String message) {
        super(message);
    }

    public ArtworkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
