package nl.novi.theartroom.exceptions;

public class RecordNotFoundException extends RuntimeException {

    // Make an exception with a message
    public RecordNotFoundException(String message) {
        super(message);
    }

    // Make a default exception
    public RecordNotFoundException() {
        super();
    }
}
