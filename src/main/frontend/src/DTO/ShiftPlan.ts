
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
}

export class ShiftPlan implements ShiftPlanDTO {

    id?: number;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: Date | null;
    endDate: Date | null;
    state: ShiftPlanState | null;

    constructor(data: ShiftPlanDTO) {
        this.id = data.id;
        this.createDate = data.createDate;
        this.updateDate = data.updateDate;
        this.name = data.name;
        this.startDate = data.startDate ? new Date(data.startDate) : null;
        this.endDate = data.endDate? new Date(data.endDate) : null;
        this.state = data.state;
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
        };
    }

    /**
     * Serialize the ShiftPlan instance to JSON for creating a new shiftPlan
     * @returns JSON object with the fields required for creating a new shiftPlan
     */
    toCreateJSON(): Pick<ShiftPlanDTO, 'name' | 'startDate' | 'endDate' | 'state'> {
        return {
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            state: this.state,
        };
    }

    static fromJSON(json: any): ShiftPlan {
        if (!json || typeof json !== 'object') {
            throw new Error('Invalid JSON for ShiftPlan');
        }
        return new ShiftPlan(json);
    }

}
