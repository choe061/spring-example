package bk.asyncexample;

public class EmailSentException extends RuntimeException {
    public EmailSentException(final String message) {
        super(message);
    }
}
