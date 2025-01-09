/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */

/**
 * Department DTO
 */

export interface DepartmentDTO {
    id?: number;
    name: string;
    openingTime: Date;
    closingTime: Date;
    managerId: number;
}

// TODO do we need the same JSON serialzitaiton capabilities as in the Userx.ts