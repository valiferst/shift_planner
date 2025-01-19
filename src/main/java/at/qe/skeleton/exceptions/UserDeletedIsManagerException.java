package at.qe.skeleton.exceptions;

public class UserDeletedIsManagerException extends RuntimeException {
    public UserDeletedIsManagerException(String message) {
        super(message);
    }
}
