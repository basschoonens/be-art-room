package nl.novi.theartroom.exception.model;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String username) {
        super("Cannot find user " + username);
    }


}