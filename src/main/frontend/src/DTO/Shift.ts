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


export class ShiftDTO implements ShiftDTO{
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
}