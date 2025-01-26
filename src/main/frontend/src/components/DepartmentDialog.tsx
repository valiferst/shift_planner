import {DepartmentDTO} from "../DTO/Department";
import React from "react";
import {InputMaskChangeEvent} from "primereact/inputmask";
import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import DepartmentForm from "./DepartmentForm"

interface DepartmentDialogProps {
    visible: boolean;
    department: DepartmentDTO | null;
    isNewDepartment: boolean;
    onHide: () => void;
    onSubmit: () => void;
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void;
    onDateChange: () => void;
    onManagerChange: () => void;
}

const DepartmentDialog: React.FC<DepartmentDialogProps> = ({
    visible,
    department,
    isNewDepartment,
    onHide,
    onSubmit,
    onInputChange,
    onDateChange,
    onManagerChange
}) => {

    /**
     * Renders the footer of the dialog.
     */
    const renderFooter = () => (
        <div>
            <Button label="Cancel" icon="pi pi-times" onClick={onHide} className="p-button-text" />
            <Button label={isNewDepartment ? "Create" : "Save"} icon="pi pi-check" onClick={onSubmit}
                    autoFocus />
        </div>
    );

    return (
        <Dialog
            header={isNewDepartment ? "Create New Department" : "Edit Department"}
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderFooter}
            >
            {department && (
                <DepartmentForm
                    department={department}
                    isNewDepartment={isNewDepartment}
                    onInputChange={onInputChange}
                    onDateChange={onDateChange}
                    onManagerChange={onManagerChange}
                    />
            )}
        </Dialog>
    );
};

export default DepartmentDialog;