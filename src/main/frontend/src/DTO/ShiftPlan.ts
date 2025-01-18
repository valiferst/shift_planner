export interface ShiftPlanDTO {
    id: number | null;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: string;
    endDate: string;
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
        this.assignedUserIds = data.assignedUserIds;
    }

    static fromJSON(json: any): ShiftPlan {
        return new ShiftPlan({
            id: json.id ?? null,
            createDate: json.createDate ?? null,
            updateDate: json.updateDate ?? null,
            name: json.name ?? '',
            startDate: json.startDate ?? '',
            endDate: json.endDate ?? '',
            assignedUserIds: json.assignedUserIds ?? []
        });
    }

    static empty(): ShiftPlan {
        return new ShiftPlan({
            id: null,
            createDate: null,
            updateDate: null,
            name: '',
            startDate: '',
            endDate: '',
            assignedUserIds: []
        });
    }

    toJSON(): any {
        return {
            id: this.id,
            createDate: this.createDate,
            updateDate: this.updateDate,
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            assignedUserIds: this.assignedUserIds
        };
    }

    toCreateJSON(): any {
        return {
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            assignedUserIds: this.assignedUserIds
        };
    }

    toUpdateJSON(): any {
        return {
            id: this.id,
            name: this.name,
            startDate: this.startDate,
            endDate: this.endDate,
            assignedUserIds: this.assignedUserIds,
            updateDate: this.updateDate
        };
    }

    id: number | null;
    createDate: string | null;
    updateDate: string | null;
    name: string;
    startDate: string;
    endDate: string;
    assignedUserIds: number[];
}
