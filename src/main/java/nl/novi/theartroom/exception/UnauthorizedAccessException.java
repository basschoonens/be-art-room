package nl.novi.theartroom.exception;

public class UnauthorizedAccessException extends RuntimeException{

        public UnauthorizedAccessException(String message) {
            super(message);
        }

        public UnauthorizedAccessException() {
            super();
        }
}
