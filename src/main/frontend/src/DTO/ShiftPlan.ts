
export enum ShiftPlanState {
    DRAFT = 'DRAFT',
    PUBLISHED = 'PUBLISHED',
    CANCELLED = 'CANCELLED',
}

export interface ShiftPlanDTO {
    id?: number;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: Date | null;
    endDate: Date | null;
    state: ShiftPlanState | null;
    departmentName: string;
}

export class ShiftPlan implements ShiftPlanDTO {

    id?: number;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: Date | null;
    endDate: Date | null;
    state: ShiftPlanState | null;
    departmentName: string;

    constructor(data: ShiftPlanDTO) {
        this.id = data.id;
        this.createDate = data.createDate;
        this.updateDate = data.updateDate;
        this.name = data.name;
        this.startDate = data.startDate ? new Date(data.startDate) : null;
        this.endDate = data.endDate? new Date(data.endDate) : null;
        this.state = data.state;
        this.departmentName = data.departmentName;
    }

    static empty() {
        return new ShiftPlan({
            id: undefined,
            createDate: null,
            updateDate: null,
            name: '',
            startDate: null,
            endDate: null,
            state: null,
            departmentName: ''
        });
    }

    toJSON() {
        return {
            id: this.id,
            createDate: this.createDate,
            updateDate: this.updateDate,
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            state: this.state,
            departmentName: this.departmentName
        };
    }

    /**
     * Serialize the ShiftPlan instance to JSON for creating a new shiftPlan
     * @returns JSON object with the fields required for creating a new shiftPlan
     */
    toCreateJSON(): Pick<ShiftPlanDTO, 'name' | 'startDate' | 'endDate' | 'state' | 'departmentName'> {
        return {
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            state: this.state,
            departmentName: this.departmentName
        };
    }

    static fromJSON(json: any): ShiftPlan {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for ShiftPlan');
        }
        return new ShiftPlan(json);
    }

}
