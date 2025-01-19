/**
 * Shift DTO
 */
export interface ShiftDTO {
    id?: number;
    startTime: Date | null;
    endTime: Date | null;
    toShiftPlan: number | null;
    shiftWorkers: string[];
}


export class Shift implements ShiftDTO{
    id?: number;
    startTime: Date | null;
    endTime: Date | null;
    toShiftPlan: number| null;
    shiftWorkers: string[];

    /**
     * Constructor for the Shift class
     * @param data :ShiftDTO object
     */
    constructor(data: ShiftDTO) {
        this.id = data.id;
        this.startTime = data.startTime ? new Date(data.startTime) : null;
        this.endTime = data.endTime ? new Date(data.endTime) : null;
        this.toShiftPlan = data.toShiftPlan ?? null;
        this.shiftWorkers = data.shiftWorkers;
    }

    /**
     * Serialize the Shift instance to JSON
     * @returns JSON object
     */
    toJSON(): ShiftDTO {
        return {
            id: this.id,
            startTime: this.startTime,
            endTime: this.endTime,
            toShiftPlan: this.toShiftPlan,
            shiftWorkers: this.shiftWorkers
        };
    }

    /**
     * Serialize the Shift instance to JSON for creating a new shift
     * @returns JSON object with the fields required for creating a new shift
     */
    toCreateJSON(): Pick<ShiftDTO, 'startTime' | 'endTime' | 'toShiftPlan' | 'shiftWorkers' > {
        return {
            startTime: this.startTime,
            endTime: this.endTime,
            toShiftPlan: this.toShiftPlan,
            shiftWorkers: this.shiftWorkers
        };
    }

    /**
     * Serialize the Shift instance to JSON for updating an existing shift
     * @returns JSON object with the fields required for updating a shift
     */
    toUpdateJSON(): Pick<ShiftDTO, 'id' | 'startTime' | 'endTime' | 'toShiftPlan' | 'shiftWorkers'> {
        return {
            id: this.id,
            startTime: this.startTime,
            endTime: this.endTime,
            toShiftPlan: this.toShiftPlan,
            shiftWorkers: this.shiftWorkers
        };
    }

    /**
     * Create an empty Shift instance
     * @returns Shift instance with empty fields
     */
    static empty(): Shift {
        return new Shift({
            id: undefined,
            startTime: null,
            endTime: null,
            toShiftPlan: null,
            shiftWorkers: []
        });
    }

    /**
     * Create a Shift instance from a JSON object
     * @param json
     * @returns Shift instance
     */
    static fromJSON(json: any): Shift {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for Shift');
        }
        return new Shift(json);
    }
}