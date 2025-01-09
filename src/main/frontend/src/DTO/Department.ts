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
    openingTime: Date | null;
    closingTime: Date | null;
    managerId?: number| null; // TODO research optional values in ts
}

/**
 * Department class with methods for serialization
 */
export class Department implements DepartmentDTO {

    id?: number | undefined; //TODO what does undefined mean in this context? research optional values in ts/js
    name: string;
    openingTime: Date | null;
    closingTime: Date | null;
    managerId?: number| null;

    /**
     * Constructor for the Department class
     * @param data :DepartmentDTO object
     */
    constructor(data: DepartmentDTO) {
        this.id = data.id;
        this.name = data.name;
        this.openingTime = data.openingTime;
        this.closingTime = data.closingTime;
        this.managerId = data.managerId;
    }

    /**
     * Serialize the Department instance to JSON
     * @returns JSON object
     */
    toJSON(): DepartmentDTO {
        return {
            id: this.id,
            name: this.name,
            openingTime: this.openingTime,
            closingTime: this.closingTime
        };
    }

    // TODO implement Department creation via frontend with the manager as a manager name or user object and not just the id of the user object of the manager
    /**
     * Serialize the Department instance to JSON for creating a new Department
     * @returns JSON object with the fields required for creating a new Department
     */
    toCreateJSON(): Pick<DepartmentDTO, 'name' | 'openingTime' | 'closingTime' | 'managerId' > {
        return {
            name: this.name,
            openingTime: this.openingTime,
            closingTime: this.closingTime,
            managerId: this.managerId
        };
    }

    // TODO, again, how do we translate an actual manager (user object with name e.g. John Doe) to a managerId?
    /**
     * Serialize the Department instance to JSON for updating an existing department
     * @returns JSON object with the fields required for updating a department
     */
    toUpdateJSON(): Pick<DepartmentDTO, 'id'| 'name' | 'openingTime' | 'closingTime' | 'managerId'  > {
        return {
            id: this.id,
            name: this.name,
            openingTime: this.openingTime,
            closingTime: this.closingTime,
            managerId: this.managerId,
        };
    }

    /**
     * Create an empty Department instance
     * @returns Department instance with empty fields
     */
    static empty(): Department {
        return new Department({
            id: undefined,
            name: '',
            openingTime: null,
            closingTime: null,
            managerId: null
        });
    }

    /**
     * Create a Department instance from a JSON object
     * @param json
     * @returns Department instance
     */
    static fromJSON(json: any): Department {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for Department');
        }
        return new Department(json);
    }
}
