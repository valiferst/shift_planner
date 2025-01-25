/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {DepartmentDTO, Department} from "../DTO/Department";

/**
 * Create a Department object from a DepartmentDTO object
 * @param data
 *
 * @returns Department
 */
export const createDepartmentFromInterfaces = (data: DepartmentDTO): Department => {
    return new Department(data);
}
