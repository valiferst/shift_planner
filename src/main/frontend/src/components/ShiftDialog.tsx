import React from 'react';
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import ShiftForm from './ShiftForm';
import { ShiftDTO } from '../DTO/Shift';
import { Userx } from '../DTO/Userx';
import {Nullable} from "primereact/ts-helpers";

interface ShiftDialogProps {
    visible: boolean;
    shift: ShiftDTO | null;
    employees: Userx[];
    onHide: () => void;
    onSubmit: () => void;
    onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    onTimeChange: (name: 'startTime' | 'endTime', event: Nullable<Date>) => void;
    onEmployeesChange: (event: {value: Userx[]}) => void;
}

const ShiftDialog: React.FC<ShiftDialogProps> = ({ visible, shift, employees, onHide, onSubmit, onInputChange, onTimeChange, onEmployeesChange }) => {
    const renderFooter = () => (
        <div>
            <Button label="Cancel" icon="pi pi-times" onClick={onHide} className="p-button-text" />
            <Button label="Save" icon="pi pi-check" onClick={onSubmit} autoFocus />
        </div>
    );

    return (
        <Dialog header="Shift Details" visible={visible} style={{ width: '50vw' }} onHide={onHide} footer={renderFooter}>
            {shift && <ShiftForm shift={shift} employees={employees} onInputChange={onInputChange} onTimeChange={onTimeChange} onEmployeesChange={onEmployeesChange} />}
        </Dialog>
    );
};

export default ShiftDialog;
