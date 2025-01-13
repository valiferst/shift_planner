/**
 * Shift DTO
 */
export interface ShiftDTO {
    id?: number;
    startTime: number;
    endTime: number;
    toShiftPlan: number;
    shiftWorkers: string[];
}


export class Shift implements ShiftDTO{
    id?: number;
    startTime: number;
    endTime: number;
    toShiftPlan: number;
    shiftWorkers: string[];

    /**
     * Constructor for the Shift class
     * @param data :ShiftDTO object
     */
    constructor(data: ShiftDTO) {
        this.id = data.id;
        this.startTime = data.startTime;
        this.endTime = data.endTime;
        this.toShiftPlan = data.toShiftPlan;
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
    
}