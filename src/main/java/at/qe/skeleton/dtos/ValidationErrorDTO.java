package at.qe.skeleton.dtos;

import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.model.ValidationErrorType;

public record ValidationErrorDTO (
    ValidationErrorType error,
    Shift shift,
    Userx user
){
    public String toFormattedString(){
        return String.format("Error: %s, Shift:  %s, User: %s", error, shift, user);
    }
}


