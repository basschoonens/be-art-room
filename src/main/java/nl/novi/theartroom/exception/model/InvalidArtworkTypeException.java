package nl.novi.theartroom.exception.model;

public class InvalidArtworkTypeException extends RuntimeException{
    public InvalidArtworkTypeException(String message) {
        super(message);
    }
}
