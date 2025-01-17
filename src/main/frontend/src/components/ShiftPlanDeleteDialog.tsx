/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {Button} from "primereact/button";
import React from "react";
import {Dialog} from "primereact/dialog";
import {ShiftPlanDTO} from "../DTO/ShiftPlan";

interface ShiftPlanDeleteDialogProps {
    visible: boolean;
    onHide: () => void;
    onDelete: () => void;
    shiftPlan: ShiftPlanDTO | null;
}

/**
 * Dialog for deleting an shiftPlan.
 *
 * @param visible whether the dialog is visible
 * @param onHide callback when the dialog is hidden
 * @param onDelete callback when the shiftPlan is deleted
 * @param shiftPlan the shiftPlan to be deleted
 *
 * @returns the delete dialog
 */
const ShiftPlanDeleteDialog: React.FC<ShiftPlanDeleteDialogProps> = ({ visible, onHide, onDelete, shiftPlan }) => {

    /**
     * Renders the contents of the delete dialog.
     */
    const renderDeleteDialogContents = () => {
        return (
            <div>
                <h2>Delete ShiftPlan</h2>
                <p>Are you sure you want to delete this shiftPlan?</p>
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
            header="Delete ShiftPlan"
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderDeleteDialogFooter}
        >
            {renderDeleteDialogContents()}
        </Dialog>
    );
}

export default ShiftPlanDeleteDialog;
