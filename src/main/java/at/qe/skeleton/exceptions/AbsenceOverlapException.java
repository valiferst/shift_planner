package at.qe.skeleton.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AbsenceOverlapException extends RuntimeException {
    public AbsenceOverlapException(String message) {
        super(message);
    }
}
