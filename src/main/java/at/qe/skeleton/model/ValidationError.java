package at.qe.skeleton.model;

import at.qe.skeleton.dtos.ValidationErrorDTO;

public class ValidationError {

        private final ValidationErrorType error;
        private final Shift shift;
        private final Userx user;

        public ValidationError(ValidationErrorType error, Shift shift, Userx user) {
            this.error = error;
            this.shift = shift;
            this.user = user;
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

        public ValidationErrorDTO mapToDto() {
            return new ValidationErrorDTO(
                    this.getValidationErrorType(),
                    this.getShift(),
                    this.getUser()
            );
        }

}
