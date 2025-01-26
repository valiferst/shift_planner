/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {Button} from "primereact/button";
import React from "react";
import {Dialog} from "primereact/dialog";
import {DepartmentDTO} from "../DTO/Department";

interface DepartmentDeleteDialogProps {
    visible: boolean;
    onHide: () => void;
    onDelete: () => void;
    department: DepartmentDTO | null;
}

/**
 * Dialog for deleting a department.
 *
 * @param visible whether the dialog is visible
 * @param onHide callback when the dialog is hidden
 * @param onDelete callback when the department is deleted
 * @param department the department to be deleted
 *
 * @returns the delete dialog
 */
const DepartmentDeleteDialog: React.FC<DepartmentDeleteDialogProps> = ({ visible, onHide, onDelete, department }) => {

    /**
     * Renders the contents of the delete dialog.
     */
    const renderDepartmentDeleteDialogContents = () => {
        return (
            <div>
                <h2>{department?.name}</h2>
                <h5><span className="p-text-secondary">{department?.fullManagerName}</span></h5>
                <p>Are you sure you want to delete this department?</p>
            </div>
        );
    }

    /**
     * Renders the footer of the delete dialog.
     */
    const renderDepartmentDeleteDialogFooter = () => {
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
            header="Delete User"
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderDepartmentDeleteDialogFooter}
        >
            {renderDepartmentDeleteDialogContents()}
        </Dialog>
    );
}

export default DepartmentDeleteDialog;
