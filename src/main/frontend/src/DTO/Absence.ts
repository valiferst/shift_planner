/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */


export enum DayOfWeek {
    MONDAY = 'MONDAY',
    TUESDAY = 'TUESDAY',
    WEDNESDAY = 'WEDNESDAY',
    FRIDAY = 'FRIDAY',
    SATURDAY = 'SATURDAY',
    SUNDAY = 'SUNDAY'
}

/**
 * Absence DTO
 */
export interface AbsenceDTO {
    id?: number;
    userId: number | null;
    validFrom: Date | null;
    validUntil: Date | null;
    absentFrom: Date | null;
    absentUntil: Date | null;
    absentDay: DayOfWeek | null;
}

/**
 * Absence class with methods for serialization
 */
export class Absence implements AbsenceDTO {
    id?: number;
    userId: number | null;
    validFrom: Date | null;
    validUntil: Date | null;
    absentFrom: Date | null;
    absentUntil: Date | null;
    absentDay: DayOfWeek | null;

    /**
     * Constructor for the Absence class
     * @param data :AbsenceDTO object
     */
    constructor(data: AbsenceDTO) {
        this.id = data.id;
        this.userId = data.userId ?? null;
        this.validFrom = data.validFrom ? new Date(data.validFrom) : null;
        this.validUntil = data.validUntil ? new Date(data.validUntil) : null;
        this.absentFrom = data.absentFrom ? new Date(data.absentFrom) : null;
        this.absentUntil = data.absentUntil ? new Date(data.absentUntil) : null;
        this.absentDay = data.absentDay;
    }


    /**
     * Serialize the Absence instance to JSON
     * @returns JSON object
     */
    toJSON(): AbsenceDTO {
        return {
            id: this.id,
            userId: this.userId,
            validFrom: this.validFrom,
            validUntil: this.validUntil,
            absentFrom: this.absentFrom,
            absentUntil: this.absentUntil,
            absentDay: this.absentDay
        };
    }

    /**
     * Serialize the Absence instance to JSON for creating a new absence
     * @returns JSON object with the fields required for creating a new absence
     */
    toCreateJSON(): Pick<AbsenceDTO, 'userId' | 'validFrom' | 'validUntil' | 'absentFrom' | 'absentUntil' | 'absentDay' > {
        return {
            userId: this.userId,
            validFrom: this.validFrom,
            validUntil: this.validUntil,
            absentFrom: this.absentFrom,
            absentUntil: this.absentUntil,
            absentDay: this.absentDay
        };
    }

    /**
     * Serialize the Absence instance to JSON for updating an existing user
     * @returns JSON object with the fields required for updating a user
     */
    toUpdateJSON(): Pick<AbsenceDTO, 'userId' | 'validFrom' | 'validUntil' | 'absentFrom' | 'absentUntil' | 'absentDay'> {
        return {
            userId: this.userId,
            validFrom: this.validFrom,
            validUntil: this.validUntil,
            absentFrom: this.absentFrom,
            absentUntil: this.absentUntil,
            absentDay: this.absentDay
        };
    }

    /**
     * Create an empty Absence instance
     * @returns Absence instance with empty fields
     */
    static empty(): Absence {
        return new Absence({
            id: undefined,
            userId: null,
            validFrom: null,
            validUntil: null,
            absentFrom: null,
            absentUntil: null,
            absentDay: null
        });
    }

    /**
     * Create an Absence instance from a JSON object
     * @param json
     * @returns Absence instance
     */
    static fromJSON(json: any): Absence {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for Absence');
        }
        return new Absence(json);
    }
}
