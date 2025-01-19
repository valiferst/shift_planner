/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";

import {Button} from "primereact/button";
import {Column} from "primereact/column";
import {DataTable} from "primereact/datatable";

import {ShiftPlan} from "../DTO/ShiftPlan";

interface ShiftPlanListProps {
    shiftPlans: ShiftPlan[];
    loading: boolean;
    onPublishShiftPlan: (shiftPlan: ShiftPlan) => void;
    onEditShiftPlan: (shiftPlan: ShiftPlan) => void;
    onDeleteShiftPlan: (shiftPlan: ShiftPlan) => void;
}


/**
 * Component for displaying a list of shiftPlans in a DataTable.
 * @param shiftPlans the shiftPlans to display
 * @param loading whether the shiftPlans are loading
 * @param onEditShiftPlan callback when an shiftPlan is edited
 * @param onPublishShiftPlan callback when an shiftPlan is published
 * @param onDeleteShiftPlan callback when an shiftPlan is deleted
 */
const ShiftPlanListComponent: React.FC<ShiftPlanListProps> = ({ shiftPlans, loading, onEditShiftPlan, onPublishShiftPlan, onDeleteShiftPlan }) => {

    /**
     * Renders the edit button for an shiftPlan.
     * @param rowData
     */
    const editButtonTemplate = (rowData: ShiftPlan) => {
        return (<Button
            label={"Details"}
            icon="pi pi-external-link"
            onClick={() => onEditShiftPlan(rowData)}
            aria-label={`ShiftPlan Details`}
        />);
    };

    /**
     * Renders the publish button for an shiftPlan.
     * @param rowData
     */
    const publishButtonTemplate = (rowData: ShiftPlan) => {
        return (<Button
            label={"Publish"}
            icon="pi pi-check-circle"
            onClick={() => onPublishShiftPlan(rowData)}
            aria-label={`Publish ShiftPlan`}
        />);
    };

    /**
     * Renders the delete button for an shiftPlan.
     * @param rowData
     */
    const deleteButtonTemplate = (rowData: ShiftPlan) => {
        return (
            <Button
                label={"Delete"}
                icon="pi pi-trash"
                className="p-button-rounded p-button-danger"
                onClick={() => onDeleteShiftPlan(rowData)}
                aria-label={`Delete ShiftPlan`}
            />
        );
    }

    const formatDate = (value: Date) => {
        return value.toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });
    }

    const startDateBodyTemplate = (shiftPlan: ShiftPlan) => {
        return shiftPlan.startDate === null ? formatDate(new Date('1900-01-01')): formatDate(shiftPlan.startDate);
    }

    const endDateBodyTemplate = (shiftPlan: ShiftPlan) => {
        return shiftPlan.endDate === null ? formatDate(new Date('1900-01-01')): formatDate(shiftPlan.endDate);
    }

    return (
        // DataTable for displaying shiftPlans
        <DataTable value={shiftPlans} loading={loading}>
            <Column field="id" header="ID" sortable></Column>
            <Column field="name" header="Name" sortable></Column>
            <Column field="startDate" header="Start Date" sortable dataType="date" body={startDateBodyTemplate}></Column>
            <Column field="endDate" header="End Date" sortable dataType="date" body={endDateBodyTemplate}></Column>
            <Column field="createDate" header="Created Date" sortable></Column>
            <Column field="updateDate" header="Updated Date" sortable></Column>
            <Column field="departmentName" header="Department" sortable></Column>
            <Column field="state" header="Status" sortable></Column>
            <Column body={editButtonTemplate} exportable={false} style={{ minWidth: '8rem' }}></Column>
            <Column body={publishButtonTemplate} exportable={false} style={{ minWidth: '8rem' }}></Column>
            <Column body={deleteButtonTemplate} exportable={false} style={{ minWidth: '8rem' }}></Column>
        </DataTable>
    )
};

export default ShiftPlanListComponent;
