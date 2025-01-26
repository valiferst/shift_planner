/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from 'react';
import { Dialog } from 'primereact/dialog';
import { Button } from "primereact/button";
import { ShiftPlanDTO } from "../DTO/ShiftPlan";
import ShiftPlanForm from './ShiftPlanForm';
import { InputMaskChangeEvent } from "primereact/inputmask";
import {Nullable} from "primereact/ts-helpers";
import {Department} from "../DTO/Department";
import {DropdownChangeEvent} from "primereact/dropdown";

interface ShiftPlanDialogProps {
    visible: boolean,
    shiftPlan: ShiftPlanDTO | null,
    isNewShiftPlan: boolean,
    departments: Department[],
    onHide: () => void,
    onSubmit: () => void,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onTimeChange: (name: 'startDate' | 'endDate', event: Nullable<Date>) => void,
    onDepartmentChange: (event: DropdownChangeEvent) => void,
}

/**
 * Dialog for creating or editing an shiftPlan.
 * @param visible whether the dialog is visible
 * @param shiftPlan the shiftPlan to be edited
 * @param isNewShiftPlan whether the shiftPlan is new
 * @param departments the departments assigned to the manager
 * @param onHide callback when the dialog is hidden
 * @param onSubmit callback when the shiftPlan is submitted
 * @param onInputChange callback when the input changes
 * @param onTimeChange callback when the date in datepicker field changes
 * @param onDepartmentChange
 */
const ShiftPlanDialog: React.FC<ShiftPlanDialogProps> = ({
    visible,
    shiftPlan,
    isNewShiftPlan,
    departments,
    onHide,
    onSubmit,
    onInputChange,
    onTimeChange,
    onDepartmentChange,
}) => {

    /**
     * Renders the footer of the dialog.
     */
    const renderFooter = () => (
        <div>
            <Button label="Cancel" icon="pi pi-times" onClick={onHide} className="p-button-text" />
            <Button label={isNewShiftPlan ? "Create" : "Save"} icon="pi pi-check" onClick={onSubmit}
                autoFocus />
        </div>
    );

    return (
        <Dialog
            header={isNewShiftPlan ? "Create New ShiftPlan" : "Edit ShiftPlan"}
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderFooter}
        >
            {shiftPlan && (
                <ShiftPlanForm
                    shiftPlan={shiftPlan}
                    isNewShiftPlan={isNewShiftPlan}
                    departments={departments}
                    onInputChange={onInputChange}
                    onTimeChange={onTimeChange}
                    onDepartmentChange={onDepartmentChange}
                />
            )}
        </Dialog>
    );
};

export default ShiftPlanDialog;
