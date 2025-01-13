/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from 'react';
import { Dialog } from 'primereact/dialog';
import { Button } from "primereact/button";
import { AbsenceDTO } from "../DTO/Absence";
import AbsenceForm from './AbsenceForm';
import { InputMaskChangeEvent } from "primereact/inputmask";
import { Nullable } from 'primereact/ts-helpers';

interface AbsenceDialogProps {
    visible: boolean,
    absence: AbsenceDTO | null,
    isNewAbsence: boolean,
    onHide: () => void,
    onSubmit: () => void,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    //onTimeChange: (name: 'absentFrom' | 'absentUntil', event: FormEvent<(Date | null)[], SyntheticEvent<Element, Event>>) => void,
    onTimeChange: (name: 'absentFrom' | 'absentUntil' | 'validFrom' | 'validUntil', event: Nullable<Date>) => void,
}

/**
 * Dialog for creating or editing an absence.
 * @param visible whether the dialog is visible
 * @param absence the absence to be edited
 * @param isNewAbsence whether the absence is new
 * @param onHide callback when the dialog is hidden
 * @param onSubmit callback when the absence is submitted
 * @param onInputChange callback when the input changes
 */
const AbsenceDialog: React.FC<AbsenceDialogProps> = ({
    visible,
    absence,
    isNewAbsence,
    onHide,
    onSubmit,
    onInputChange,
    onTimeChange,
}) => {

    /**
     * Renders the footer of the dialog.
     */
    const renderFooter = () => (
        <div>
            <Button label="Cancel" icon="pi pi-times" onClick={onHide} className="p-button-text" />
            <Button label={isNewAbsence ? "Create" : "Save"} icon="pi pi-check" onClick={onSubmit}
                autoFocus />
        </div>
    );

    return (
        <Dialog
            header={isNewAbsence ? "Create New Absence" : "Edit Absence"}
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderFooter}
        >
            {absence && (
                <AbsenceForm
                    absence={absence}
                    isNewAbsence={isNewAbsence}
                    onInputChange={onInputChange}
                    onTimeChange={onTimeChange}
                />
            )}
        </Dialog>
    );
};

export default AbsenceDialog;
