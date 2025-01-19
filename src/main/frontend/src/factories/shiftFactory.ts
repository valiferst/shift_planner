/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {ShiftDTO, Shift} from "../DTO/Shift";
/**
 * Create a ShiftPlan object from an ShiftPlanDTO object
 * @param data
 *
 * @returns Shift
 */
export const createShiftFromInterfaces = (data: ShiftDTO): Shift => {
    return new Shift(data);
}

