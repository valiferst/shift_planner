
import React from 'react';
import { ShiftDTO } from '../DTO/Shift';
import { Calendar } from 'primereact/calendar';
import { MultiSelect } from 'primereact/multiselect';
import { Userx } from '../DTO/Userx';
import {Nullable} from "primereact/ts-helpers";

interface ShiftFormProps {
    shift: ShiftDTO;
    employees: Userx[];
    onInputChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    onTimeChange: (name: 'startTime' | 'endTime', event: Nullable<Date>) => void;
    onEmployeesChange: (event: {value: Userx[]}) => void;
}

const ShiftForm: React.FC<ShiftFormProps> = ({ shift, employees, onInputChange, onTimeChange, onEmployeesChange }) => {
    return (
        <div>
            <h1>
                {shift.startTime && shift.endTime
                    ? `Shift from ${new Date(shift.startTime).toLocaleString()} to ${new Date(shift.endTime).toLocaleString()}`
                    : "No shiftPlan selected"}
            </h1>
            <div className="flex-auto mb-3">
                <label htmlFor="calenar-24h" className="font-bold block">Start Time</label>
                <Calendar id="calenar-24h" value={shift.startTime} onChange={(e) => onTimeChange('startTime', e.value)} showTime hourFormat="24" />
            </div>
            <div className="flex-auto mb-3">
                <label htmlFor="calenar-24h">End Time</label>
                <Calendar id="calenar-24h" value={shift.endTime} onChange={(e) => onTimeChange('endTime', e.value)} showTime hourFormat="24" />
            </div>
            <div className="field">
                <label htmlFor="shiftWorkerNames">Assign Employees</label>
                <MultiSelect id="shiftWorkerNames" value={employees.filter(employee => shift.shiftWorkerIds.includes(employee.id!)) || null} options={employees} onChange={onEmployeesChange} optionLabel="fullNameWithUsername" placeholder="Select Employees" />
            </div>
        </div>
    );
};

export default ShiftForm;
