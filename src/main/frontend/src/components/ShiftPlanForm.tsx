/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import { ShiftPlanDTO } from "../DTO/ShiftPlan";
import { InputMaskChangeEvent } from "primereact/inputmask";
import { InputText } from "primereact/inputtext";
import {Calendar} from "primereact/calendar";
import {Nullable} from "primereact/ts-helpers";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {ShiftDTO} from "../DTO/Shift";
import {Department} from "../DTO/Department";
import {Dropdown, DropdownChangeEvent} from "primereact/dropdown";
import ShiftTableComponent from "./ShiftTableComponent";
import {Userx} from "../DTO/Userx";


interface ShiftPlanFormProps {
    shiftPlan: ShiftPlanDTO,
    isNewShiftPlan: boolean,
    departments: Department[],
    employees: Userx[],
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onTimeChange: (name: 'startDate' | 'endDate', event: Nullable<Date>) => void,
    onDepartmentChange: (event: DropdownChangeEvent) => void
}

/**
 * Form for creating or editing an shiftPlan.
 * @param shiftPlan the shiftPlan to be edited
 * @param departments
 * @param isNewShiftPlan whether the shiftPlan is new
 * @param onInputChange callback when the input changes
 * @param onTimeChange
 * @param onDepartmentChange
 */
const ShiftPlanForm: React.FC<ShiftPlanFormProps> =
    ({
        shiftPlan,
        departments,
        employees,
        isNewShiftPlan,
        onInputChange,
        onTimeChange,
        onDepartmentChange
    }) => {
        return (
            <div>
                <h1>
                    {shiftPlan.startDate && shiftPlan.endDate && shiftPlan.name
                        ? `ShiftPlan "${shiftPlan.name}" from ${new Date(shiftPlan.startDate).toLocaleDateString()} to ${new Date(shiftPlan.endDate).toLocaleDateString()}`
                        : "No shiftPlan selected"}
                </h1>
                {/* Create form */}
                <div className="card p-fluid flex flex-wrap gap-3">
                    <div className="flex-auto mb-3">
                        <label htmlFor="name" className="font-bold block">Shift Plan Name</label>
                        <InputText
                            id="name"
                            name="name"
                            value={shiftPlan.name}
                            onChange={onInputChange}
                            placeholder="Enter shift plan name"
                        />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="departmentName" className="font-bold block">Department</label>
                        <Dropdown
                            value={departments.find(dept => dept.name === shiftPlan.departmentName) || null} // Ensure it selects the correct department object
                            onChange={onDepartmentChange}
                            options={departments}
                            optionLabel="name"
                            placeholder="Select a Department"
                            className="w-full md:w-14rem"
                        />

                        {/*<Dropdown value={shiftPlan.departmentName} onChange={onDepartmentChange} options={departments} optionLabel="name"*/}
                        {/*      placeholder="Select a Department" className="w-full md:w-14rem" />*/}
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="startDate" className="font-bold block">Start Date</label>
                        <Calendar dateFormat="dd/mm/yy" value={shiftPlan.startDate}
                                  onChange={(e) => onTimeChange('startDate', e.value)}/>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="endDate" className="font-bold block">End Date</label>
                        <Calendar dateFormat="dd/mm/yy" value={shiftPlan.endDate}
                                  onChange={(e) => onTimeChange('endDate', e.value)}/>
                    </div>
                </div>
                <div className="card">
                    <ShiftTableComponent
                    shiftPlan={shiftPlan} isNewShiftPlan={isNewShiftPlan} employees={employees}/>
                </div>
            </div>

        )

    }

export default ShiftPlanForm;
