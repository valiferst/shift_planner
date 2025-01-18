/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {Button} from "primereact/button";
import React from "react";
import {Dialog} from "primereact/dialog";
import {AbsenceDTO} from "../DTO/Absence";

interface AbsenceDeleteDialogProps {
    visible: boolean;
    onHide: () => void;
    onDelete: () => void;
    absence: AbsenceDTO | null;
}

/**
 * Dialog for deleting an absence.
 *
 * @param visible whether the dialog is visible
 * @param onHide callback when the dialog is hidden
 * @param onDelete callback when the absence is deleted
 * @param absence the absence to be deleted
 *
 * @returns the delete dialog
 */
const AbsenceDeleteDialog: React.FC<AbsenceDeleteDialogProps> = ({ visible, onHide, onDelete, absence }) => {

    /**
     * Renders the contents of the delete dialog.
     */
    const renderDeleteDialogContents = () => {
        return (
            <div>
                <h2>Delete Absence</h2>
                <p>Are you sure you want to delete this absence?</p>
            </div>
        );
    }

    /**
     * Renders the footer of the delete dialog.
     */
    const renderDeleteDialogFooter = () => {
        return (
            <div>
                <Button label="Cancel" icon="pi pi-times"
                    onClick={onHide}
                    className="p-button-text" />
                <Button label="Delete" icon="pi pi-trash" onClick={onDelete} autoFocus />
            </div>
        );
    }

    return (
        <Dialog
            header="Delete Absence"
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderDeleteDialogFooter}
        >
            {renderDeleteDialogContents()}
        </Dialog>
    );
}

export default AbsenceDeleteDialog;
