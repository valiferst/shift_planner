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



    return (
        // DataTable for displaying shiftPlans
        <DataTable value={shiftPlans} loading={loading}>
            <Column field="absentFrom" header="Absent From" sortable></Column>
            <Column field="absentUntil" header="Absent Until" sortable></Column>
            <Column field="absentDay" header="Absent Weekday" sortable></Column>
            <Column field="validFrom" header="Valid from" sortable></Column>
            <Column field="validUntil" header="Valid Until" sortable></Column>
            <Column body={editButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
            <Column body={publishButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
            <Column body={deleteButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
        </DataTable>
    )
};

export default ShiftPlanListComponent;
