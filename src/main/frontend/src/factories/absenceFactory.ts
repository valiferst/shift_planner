/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {AbsenceDTO, Absence} from "../DTO/Absence";

/**
 * Create an Absence object from an AbsenceDTO object
 * @param data
 *
 * @returns Absence
 */
export const createAbsenceFromInterfaces = (data: AbsenceDTO): Absence => {
    return new Absence(data);
}

