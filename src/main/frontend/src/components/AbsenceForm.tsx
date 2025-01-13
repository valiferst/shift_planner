/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import {AbsenceDTO} from "../DTO/Absence";
import {InputMaskChangeEvent} from "primereact/inputmask";
import {InputText} from "primereact/inputtext";


interface AbsenceFormProps {
    absence: AbsenceDTO,
    isNewAbsence: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
}

/**
 * Form for creating or editing an absence.
 * @param absence the absence to be edited
 * @param isNewAbsence whether the absence is new
 * @param onInputChange callback when the input changes
 */
const AbsenceForm: React.FC<AbsenceFormProps> =
    ({
        absence,
        isNewAbsence,
        onInputChange,
    }) => {
        // TODO Implement validFrom and validTo as Datepicker: see https://refine.dev/blog/react-date-picker/#select-range-within-one-component
        // TODO Implement absentFrom and absentUntil as time
        // TODO Implement Weekday as Dropdown -> change weekday do set?
        return (
            <div>
                <h1>Absence from {absence.absentFrom?.getTime()} until {absence.absentUntil?.getTime()} on {absence.absentDay}</h1>
                {/* create form */}
                <div className="card p-fluid flex flex-wrap gap-3">
                    <div className="flex-auto mb-3">
                        <label htmlFor="absentFrom" className="font-bold block">Absent From</label>
                        <InputText id="absentFrom" name="absentFrom" value={absence.absentFrom?.getTime().toString()}
                                   onChange={onInputChange}
                                   placeholder="00:00"/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="absentUntil" className="font-bold block">Absent Until</label>
                        <InputText id="absentUntil" name="absentUntil" value={absence.absentUntil?.getTime().toString()}
                                   onChange={onInputChange}
                                   placeholder="23:59"/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="absentDay" className="font-bold block">Absent Day</label>
                        <InputText id="absentDay" name="absentDay" value={absence.absentDay}
                                   onChange={onInputChange}
                                   placeholder="Weekday"/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="validFrom" className="font-bold block">Valid From</label>
                        <InputText id="validFrom" name="validFrom" value={absence.validFrom?.getDate().toString()}
                                   onChange={onInputChange}
                                   placeholder="Date of first Validity"/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="validUntil" className="font-bold block">Valid Until</label>
                        <InputText id="validUntil" name="validUntil" value={absence.validUntil?.getDate().toString()}
                                   onChange={onInputChange}
                                   placeholder="Date of last Validity"/>
                    </div>
                </div>
            </div>
        )

    }

export default AbsenceForm;
