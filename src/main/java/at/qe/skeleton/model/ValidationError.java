package at.qe.skeleton.model;

public class ValidationError {

        private final ValidationErrorType error;
        private final Shift shift;
        private final Userx user;
        private final Absence absence;

        public ValidationError(ValidationErrorType error, Shift shift, Userx user, Absence absence) {
            this.error = error;
            this.shift = shift;
            this.user = user;
            this.absence = absence;
        }

        public ValidationErrorType getValidationErrorType() {
            return error;
        }

        public Shift getShift() {
            return shift;
        }

        public Userx getUser() {
            return user;
        }

        public Absence getAbsence() {
            return absence;
        }
}
