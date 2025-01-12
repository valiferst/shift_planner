import {Department} from "../DTO/Department";
import React from "react";
import {Button} from "primereact/button";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";

interface DepartmentListProps {
    departments: Department[];
    loading: boolean;
    onEditDepartment: (department: Department) => void;
    onDeleteDepartment: (department: Department) => void;
}

const DepartmentListComponent: React.FC<DepartmentListProps> = ({
                                                                    departments,
                                                                    loading,
                                                                    onEditDepartment,
                                                                    onDeleteDepartment}) => {

    /**
     * Renders the edit button for a department.
     * @param rowData
     */
    const editButtonTemplate = (rowData: Department) => {
        return (<Button
            label={"Details"}
            icon="pi pi-external-link"
            onClick={() => onEditDepartment(rowData)}
            aria-label={`Details for ${rowData.name}`}
        />);
    };


    /**
     * Renders the delete button for a department.
     * @param rowData
     */
    const deleteButtonTemplate = (rowData: Department) => {
        return (
            <Button
                label={"Delete"}
                icon="pi pi-trash"
                className="p-button-rounded p-button-danger"
                onClick={() => onDeleteDepartment(rowData)}
                aria-label={`Delete ${rowData.name}`}
            />
        );
    }

    //TODO missing datatable columns: opening and closing time

    return (
        <DataTable value={departments} loading={loading}>
            <Column field="name" header="Name" sortable></Column>
            <Column field="managerName" header="Manager Name" sortable></Column>
        </DataTable>

    )
};

export default DepartmentListComponent;