package nl.novi.theartroom.exceptions;

public class UserNotFoundException extends RuntimeException {

    //TODO toevoegen aan mijn ExceptionController

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String username) {
        super("Cannot find user " + username);
    }

}