package nl.novi.theartroom.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    //TODO toevoegen aan mijn ExceptionController

    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("Cannot find user " + username);
    }

}