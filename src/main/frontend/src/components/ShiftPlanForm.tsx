/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import { ShiftPlanDTO } from "../DTO/ShiftPlan";
import { InputMaskChangeEvent } from "primereact/inputmask";
import { InputText } from "primereact/inputtext";
import {Calendar} from "primereact/calendar";
import {Nullable} from "primereact/ts-helpers";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {ShiftDTO} from "../DTO/Shift";


interface ShiftPlanFormProps {
    shiftPlan: ShiftPlanDTO,
    isNewShiftPlan: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onTimeChange: (name: 'startDate' | 'endDate', event: Nullable<Date>) => void,
}

/**
 * Form for creating or editing an shiftPlan.
 * @param shiftPlan the shiftPlan to be edited
 * @param isNewShiftPlan whether the shiftPlan is new
 * @param onInputChange callback when the input changes
 * @param onTimeChange
 */
const ShiftPlanForm: React.FC<ShiftPlanFormProps> =
    ({
        shiftPlan,
        isNewShiftPlan,
        onInputChange,
        onTimeChange
    }) => {
        return (
            <div>
                <h1>
                    {shiftPlan.startDate && shiftPlan.endDate && shiftPlan.name
                        ? `ShiftPlan "${shiftPlan.name}" from ${new Date(shiftPlan.startDate).toLocaleDateString()} to ${new Date(shiftPlan.endDate).toLocaleDateString()}`
                        : "No shiftPlan selected"}
                </h1>
                {/* Create form */}
                <div className="card p-fluid flex flex-wrap gap-3">
                    <div className="flex-auto mb-3">
                        <label htmlFor="name" className="font-bold block">Shift Plan Name</label>
                        <InputText
                            id="name"
                            name="name"
                            value={shiftPlan.name}
                            onChange={onInputChange}
                            placeholder="Enter shift plan name"
                        />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="startDate" className="font-bold block">Start Date</label>
                        <Calendar dateFormat="dd/mm/yy" value={shiftPlan.startDate}
                                  onChange={(e) => onTimeChange('startDate', e.value)}/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="endDate" className="font-bold block">End Date</label>
                        <Calendar dateFormat="dd/mm/yy" value={shiftPlan.endDate}
                                  onChange={(e) => onTimeChange('endDate', e.value)}/>
                    </div>
                </div>
                <div className="card">
                    <h2>Shifts</h2>
                    <DataTable value={shiftPlan.shifts || []} paginator rows={5} emptyMessage="No shifts available.">
                        <Column field="id" header="ID"/>
                        <Column
                            field="startTime"
                            header="Start Time"
                            body={(rowData: ShiftDTO) => rowData.startTime ? new Date(rowData.startTime).toLocaleString() : ''}
                        />
                        <Column
                            field="endTime"
                            header="End Time"
                            body={(rowData: ShiftDTO) => rowData.endTime ? new Date(rowData.endTime).toLocaleString() : ''}
                        />
                        <Column
                            field="shiftWorkers"
                            header="Workers"
                            body={(rowData: ShiftDTO) => rowData.shiftWorkerNames?.join(', ') || 'No workers assigned'}
                        />
                    </DataTable>
                </div>
            </div>

        )

    }

export default ShiftPlanForm;
