/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import { AbsenceDTO } from "../DTO/Absence";
import { InputMaskChangeEvent } from "primereact/inputmask";
import { InputText } from "primereact/inputtext";
import { Calendar } from "primereact/calendar";
import { Nullable } from "primereact/ts-helpers";


interface AbsenceFormProps {
    absence: AbsenceDTO,
    isNewAbsence: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onTimeChange: (name: 'absentFrom' | 'absentUntil' | 'validFrom' | 'validUntil', event: Nullable<Date>) => void,
}
// TODO: make ShiftPlan form essentially a shift table view? Then this form would be obsolete

/**
 * Form for creating or editing an absence.
 * @param absence the absence to be edited
 * @param isNewAbsence whether the absence is new
 * @param onInputChange callback when the input changes
 * @param onTimeChange
 */
const AbsenceForm: React.FC<AbsenceFormProps> =
    ({
        absence,
        isNewAbsence,
        onInputChange,
        onTimeChange,
    }) => {
        return (
            <div>
                <h1>
                    {absence.absentFrom && absence.absentUntil && absence.absentDay ? `Absence from ${absence.absentFrom?.toLocaleTimeString()} until ${absence.absentUntil?.toLocaleTimeString()} on ${absence.absentDay}`
                        : "No absence selected"}
                </h1>
                {/* create form */}

                <div className="card p-fluid flex flex-wrap gap-3">

                    <div className="flex-auto mb-3">
                        <label htmlFor="absentFrom" className="font-bold block">Absent From</label>
                        <Calendar value={absence.absentFrom} onChange={(e) => onTimeChange('absentFrom', e.value)} timeOnly />
                    </div>

                    <div className="flex-auto mb-3">
                        <label htmlFor="absentUntil" className="font-bold block">Absent Until</label>
                        <Calendar value={absence.absentUntil} onChange={(e) => onTimeChange('absentUntil', e.value)} timeOnly />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="absentDay" className="font-bold block">Absent Day</label>
                        <InputText id="absentDay" name="absentDay" value={absence.absentDay}
                            onChange={onInputChange}
                            placeholder="Weekday" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="validFrom" className="font-bold block">Valid From</label>
                        <Calendar value={absence.validFrom} onChange={(e) => onTimeChange('validFrom', e.value)} />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="validUntil" className="font-bold block">Valid Until</label>
                        <Calendar value={absence.validUntil} onChange={(e) => onTimeChange('validUntil', e.value)} />
                    </div>
                </div>
            </div>
        )

    }

export default AbsenceForm;
