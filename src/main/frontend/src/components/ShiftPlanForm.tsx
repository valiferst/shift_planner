/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import { ShiftPlanDTO } from "../DTO/ShiftPlan";
import { InputMaskChangeEvent } from "primereact/inputmask";
import { InputText } from "primereact/inputtext";


interface ShiftPlanFormProps {
    shiftPlan: ShiftPlanDTO,
    isNewShiftPlan: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
}
// TODO: make ShiftPlan form essentially a shift table view? Then this form would be obsolete

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
                        <InputText
                            id="startDate"
                            name="startDate"
                            value={shiftPlan.startDate}
                            onChange={onInputChange}
                            placeholder="YYYY-MM-DD"
                        />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="endDate" className="font-bold block">End Date</label>
                        <InputText
                            id="endDate"
                            name="endDate"
                            value={shiftPlan.endDate}
                            onChange={onInputChange}
                            placeholder="YYYY-MM-DD"
                        />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="assignedUserIds" className="font-bold block">Assigned Users</label>
                        <InputText
                            id="assignedUserIds"
                            name="assignedUserIds"
                            value={shiftPlan.assignedUserIds.join(', ')}
                            onChange={onInputChange}
                            placeholder="Enter user IDs, comma-separated"
                        />
                    </div>
                </div>
            </div>

        )

    }

export default ShiftPlanForm;
