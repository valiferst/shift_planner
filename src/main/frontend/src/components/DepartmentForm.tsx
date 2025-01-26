import {DepartmentDTO} from "../DTO/Department";
import React from "react";
import {InputText} from "primereact/inputtext";
import {InputMaskChangeEvent} from "primereact/inputmask";

interface DepartmentFormProps {
    department: DepartmentDTO
    isNewDepartment: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onDateChange: () => void, //TODO implement callback for opening hour change
    onManagerChange: () => void // TODO implement callback for manager change
}
// TODO comment
const DepartmentForm: React.FC<DepartmentFormProps> =
    ({
         department,
         isNewDepartment, // TODO unused, but it's also unused in the UserForm.tsx
         onInputChange,
         onDateChange,
         onManagerChange

     }) => {
        const managerList = ["Hans", "Peter"] // TODO implement a function to get a list of all the manager-names
        // TODO form field for manager (dropdown list), openingTime and closingTime
        return (
            <div>
                <h1>{department.name}</h1>
                <h5><span className="p-text-secondary">{department.fullManagerName}</span></h5>
                {/* create form */}
                <div className="card p-fluid flex flex-wrap gap-3">
                    <div className="flex-auto mb-3">
                        <label htmlFor="name" className="font-bold block">Departmentname</label>
                        <InputText id="name" name="name" value={department.name}
                                   onChange={onInputChange} required={true}
                                   placeholder="Departmentname"/>

                    </div>
                </div>
            </div>

        )
    }

export default DepartmentForm;