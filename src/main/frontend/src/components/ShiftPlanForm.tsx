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
                    {shiftPlan.absentFrom && shiftPlan.absentUntil && shiftPlan.absentDay ? `ShiftPlan from ${shiftPlan.absentFrom?.toLocaleTimeString()} until ${shiftPlan.absentUntil?.toLocaleTimeString()} on ${shiftPlan.absentDay}`
                        : "No shiftPlan selected"}
                </h1>
                {/* create form */}

                <div className="card p-fluid flex flex-wrap gap-3">

                    <div className="flex-auto mb-3">
                        <label htmlFor="absentDay" className="font-bold block">Absent Day</label>
                        <InputText id="absentDay" name="absentDay" value={shiftPlan.absentDay}
                            onChange={onInputChange}
                            placeholder="Weekday" />
                    </div>
                </div>
            </div>
        )

    }

export default ShiftPlanForm;
