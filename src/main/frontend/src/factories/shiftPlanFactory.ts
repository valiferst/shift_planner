/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {ShiftPlanDTO, ShiftPlan} from "../DTO/ShiftPlan";

/**
 * Create a ShiftPlan object from an ShiftPlanDTO object
 * @param data
 *
 * @returns ShiftPlan
 */
export const createShiftPlanFromInterfaces = (data: ShiftPlanDTO): ShiftPlan => {
    return new ShiftPlan(data);
}

