package at.qe.skeleton.model;

public class ShiftPlanValidationError {

        private final ValidationError error;
        private final Shift shift;
        private final Userx user;

        public ShiftPlanValidationError(ValidationError error, Shift shift, Userx user) {
            this.error = error;
            this.shift = shift;
            this.user = user;
        }

        public ValidationError getValidationError() {
            return error;
        }

        public Shift getShift() {
            return shift;
        }

        public Userx getUser() {
            return user;
        }


}
