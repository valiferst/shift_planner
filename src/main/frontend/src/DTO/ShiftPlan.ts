export const ShiftPlanState = {
    DRAFT: 'DRAFT',
    PUBLISHED: 'PUBLISHED',
    CANCELLED: 'CANCELLED',
};

export interface ShiftPlanDTO {
    id: number | null;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: string;
    endDate: string;
    state: string;
    assignedUserIds: number[];
}

export class ShiftPlan implements ShiftPlanDTO {
    constructor(data: ShiftPlanDTO) {
        this.id = data.id;
        this.createDate = data.createDate;
        this.updateDate = data.updateDate;
        this.name = data.name;
        this.startDate = data.startDate;
        this.endDate = data.endDate;
        this.state = data.state;
        this.assignedUserIds = data.assignedUserIds;
    }

    static fromJSON(json: any) {
        return new ShiftPlan({
            id: json.id ?? null,
            createDate: json.createDate ?? null,
            updateDate: json.updateDate ?? null,
            name: json.name ?? '',
            startDate: json.startDate ?? '',
            endDate: json.endDate ?? '',
            state: json.state ?? ShiftPlanState.DRAFT,
            assignedUserIds: json.assignedUserIds ?? [],
        });
    }

    static empty() {
        return new ShiftPlan({
            id: null,
            createDate: null,
            updateDate: null,
            name: '',
            startDate: '',
            endDate: '',
            state: ShiftPlanState.DRAFT,
            assignedUserIds: [],
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
            assignedUserIds: this.assignedUserIds,
        };
    }

    toCreateJSON() {
        return {
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            state: this.state,
            assignedUserIds: this.assignedUserIds,
        };
    }

    toUpdateJSON() {
        return {
            id: this.id,
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            state: this.state,
            assignedUserIds: this.assignedUserIds,
            updateDate: this.updateDate,
        };
    }

    id: number | null;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: string;
    endDate: string;
    state: string;
    assignedUserIds: number[];
}
