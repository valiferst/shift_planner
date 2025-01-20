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

    /**
     * Formats the opening and closing times.
     * @param value
     */
    const formatTime = (value: Date | null) => {
        if (!value) {
            return "-"; // Fallback für null-Werte
        }
        return new Date(value).toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit',
        });
    };


    return (
        <DataTable value={departments} loading={loading} responsiveLayout="scroll">
            <Column field="name" header="Name" sortable></Column>
            <Column field="managerName" header="Manager Name" sortable></Column>
            <Column
                field="openingTime"
                header="Opening Time"
                body={(rowData: Department) => formatTime(rowData.openingTime)}
                sortable
            ></Column>
            <Column
                field="closingTime"
                header="Closing Time"
                body={(rowData: Department) => formatTime(rowData.closingTime)}
                sortable
            ></Column>
            <Column
                body={editButtonTemplate}
                header="Actions"
                style={{textAlign: 'center', width: '8rem'}}
            ></Column>
            <Column
                body={deleteButtonTemplate}
                header=""
                style={{textAlign: 'center', width: '8rem'}}
            ></Column>
        </DataTable>
    );
};

export default DepartmentListComponent;