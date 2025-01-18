/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";

import {Button} from "primereact/button";
import {Column} from "primereact/column";
import {DataTable} from "primereact/datatable";

import {Absence} from "../DTO/Absence";

interface AbsenceListProps {
    absences: Absence[];
    loading: boolean;
    onEditAbsence: (absence: Absence) => void;
    onDeleteAbsence: (absence: Absence) => void;
}


/**
 * Component for displaying a list of absences in a DataTable.
 * @param absences the absences to display
 * @param loading whether the absences are loading
 * @param onEditAbsence callback when an absence is edited
 * @param onDeleteAbsence callback when an absence is deleted
 */
const AbsenceListComponent: React.FC<AbsenceListProps> = ({ absences, loading, onEditAbsence, onDeleteAbsence }) => {

    /**
     * Renders the edit button for an absence.
     * @param rowData
     */
    const editButtonTemplate = (rowData: Absence) => {
        return (<Button
            label={"Details"}
            icon="pi pi-external-link"
            onClick={() => onEditAbsence(rowData)}
            aria-label={`Absence Details`}
        />);
    };

    /**
     * Renders the delete button for an absence.
     * @param rowData
     */
    const deleteButtonTemplate = (rowData: Absence) => {
        return (
            <Button
                label={"Delete"}
                icon="pi pi-trash"
                className="p-button-rounded p-button-danger"
                onClick={() => onDeleteAbsence(rowData)}
                aria-label={`Delete Absence`}
            />
        );
    }



    return (
        // DataTable for displaying absences
        <DataTable value={absences} loading={loading}>
            <Column field="absentFrom" header="Absent From" sortable></Column>
            <Column field="absentUntil" header="Absent Until" sortable></Column>
            <Column field="absentDay" header="Absent Weekday" sortable></Column>
            <Column field="validFrom" header="Valid from" sortable></Column>
            <Column field="validUntil" header="Valid Until" sortable></Column>
            <Column body={editButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
            <Column body={deleteButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
        </DataTable>
    )
};

export default AbsenceListComponent;
