/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */

import {Shift} from "./Shift";
import {Userx} from "./Userx";

/**
 * ValidationError roles
 */
export enum ConflictType {
    ABSENCE_CONFILCT = 'ABSENCE_CONFILCT',
    SHIFT_CONFLICT = 'SHIFT_CONFLICT',
}

/**
 * ValidationError DTO
 */
export interface ValidationErrorDTO {
    error: ConflictType;
    shift: Shift;
    user: Userx;
}

/**
 * ValidationError class with methods for serialization
 */
export class ValidationError implements ValidationErrorDTO {
    error: ConflictType;
    shift: Shift;
    user: Userx;

    /**
     * Constructor for the ValidationError class
     * @param data :ValidationErrorDTO object
     */
    constructor(data: ValidationErrorDTO) {
        this.error = data.error;
        this.shift = data.shift;
        this.user = data.user;
    }

    /**
     * Create a ValidationError instance from a JSON object
     * @param json
     * @returns ValidationError instance
     */
    static fromJSON(json: any): ValidationError {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for ValidationError');
        }
        return new ValidationError(json);
    }
}
