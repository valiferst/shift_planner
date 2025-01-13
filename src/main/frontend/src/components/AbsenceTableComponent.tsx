/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React, {useEffect, useState} from 'react';

import {Button} from "primereact/button";
import {Card} from 'primereact/card';
import {InputMaskChangeEvent} from "primereact/inputmask";
import 'primeicons/primeicons.css';

import AbsenceListComponent from "./AbsenceListComponent";
import AbsenceDialog from "./AbsenceDialog";
import AbsenceDeleteDialog from "./AbsenceDeleteDialog";

import {AbsenceDTO, Absence} from "../DTO/Absence";
import {AbsenceCrud} from "../utilities/AbsenceCrud";
import {
    createAbsenceFromInterfaces
} from '../factories/absenceFactory';

/**
 * Component for managing absences.
 */
const AbsenceTable = () => {
    const [absences, setAbsences] = useState<Absence[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [selectedAbsence, setSelectedAbsence] = useState<AbsenceDTO | null>(null);
    const [isNewAbsence, setIsNewAbsence] = useState<boolean>(false);
    const [dialogVisible, setDialogVisible] = useState<boolean>(false);
    const [deleteDialogVisible, setDeleteDialogVisible] = useState<boolean>(false);

    /**
     * Fetch all absences from the backend on mount once.
     */
    useEffect(() => {
        const fetchAbsences = async () => {
            try {
                const absenceData = await AbsenceCrud.fetchAllAbsences();
                const absenceInstances = absenceData.map((absence: AbsenceDTO) => createAbsenceFromInterfaces(absence));
                setAbsences(absenceInstances);
            } catch (error: any) {
                console.error('Error fetching absences:', error);
            } finally {
                setLoading(false); // Set loading to false regardless of success or failure
            }
        };
        fetchAbsences();
    }, []); // empty dependency array means this effect will only run once on mount

    /**
     * Validate the absence object.
     * @param absence
     */
    const validateAbsence = (absence: AbsenceDTO | null): boolean => {
        if (!absence) return false;
        return absence.validFrom !== null &&
            absence.validUntil !== null &&
            absence.absentFrom !== null &&
            absence.absentUntil !== null &&
            absence.absentDay !== null &&
            absence.validFrom <= absence.validUntil;
    }

    /**
     * Handle the submit event for the absence dialog.
     */
    const handleSubmit = async () => {
        if (!validateAbsence(selectedAbsence)) {
            // Display an error message or handle the validation error
            console.error('Please fill out all required fields.');
            return;
        }

        if (isNewAbsence) {
            await createAbsence();
        } else {
            await updateAbsence();
        }
        hideDialog();
    };

    /**
     * Create a new absence and update the state.
     */
    const createAbsence = async () => {
        if (!selectedAbsence) return;

        try {
            const newAbsence: Absence = await AbsenceCrud.createAbsence(selectedAbsence);
            setAbsences([...absences, newAbsence]);
        } catch (error: any) {
            console.error('Error saving absence:', error);
            // Add toast message for error
        }
    }

    /**
     * Update an existing absence and update the state.
     */
    const updateAbsence = async () => {
        if (!selectedAbsence) return;

        try {
            const updatedAbsence: Absence = await AbsenceCrud.updateAbsence(selectedAbsence);
            setAbsences(absences.map((absence: Absence) => absence.id === updatedAbsence.id ? updatedAbsence : absence));
            hideDialog();
        } catch (error: any) {
            console.error('Error updating absence:', error);
        }
    }


    /**
     * Delete an absence and update the state.
     */
    const deleteAbsence = async () => {
        if (!selectedAbsence) return;

        try {
            await AbsenceCrud.deleteAbsence(selectedAbsence);
            setAbsences(absences.filter((absence: Absence) => absence.id !== selectedAbsence.id));
            hideDialog();
        } catch (error) {
            console.error('Error deleting absence:', error);
            // TODO: Add toast message for error
        }
        setDeleteDialogVisible(false);
    }

    /**
     * Open the delete dialog for an absence.
     * @param absence
     */
    const openDeleteDialog = (absence: Absence) => {
        setSelectedAbsence(absence);
        setDeleteDialogVisible(true);
    }

    /**
     * Open the edit dialog for an absence.
     * @param absence
     */
    const openEditDialog = (absence: Absence) => {
        setSelectedAbsence(absence);
        setIsNewAbsence(false);
        showDialog()
    };

    /**
     * Open the dialog for creating a new absence.
     */
    const openNewAbsenceDialog = () => {
        setSelectedAbsence(Absence.empty());
        showDialog()
        setIsNewAbsence(true);
    }

    /**
     * Show the dialog.
     */
    const showDialog = () => {
        setDialogVisible(true);
    }

    /**
     * Hide the dialog.
     */
    const hideDialog = () => {
        setDialogVisible(false);
    };

    /**
     * Handle input changes for the absence dialog.
     * @param event
     */
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => {
        if (!selectedAbsence) return;

        const {name, value} = event.target;

        setSelectedAbsence({...selectedAbsence, [name]: value});
    }


    return (<Card title="Absence List" className="m-4">
            {/* Button that opens a new absence dialog on click */}
            <Button label="Add Absence" icon="pi pi-plus" className="p-button-raised p-button-rounded"
                    style={{marginBottom: "10px"}} onClick={openNewAbsenceDialog}/>
            <AbsenceListComponent absences={absences} loading={loading} onEditAbsence={openEditDialog}
                                  onDeleteAbsence={openDeleteDialog}/>

            {/* Dialog for creating or editing an absence */}
            <AbsenceDialog visible={dialogVisible} absence={selectedAbsence} isNewAbsence={isNewAbsence}
                           onHide={hideDialog} onSubmit={handleSubmit}
                           onInputChange={handleInputChange}/>
            {/* Dialog for deleting an absence */}
            <AbsenceDeleteDialog
                visible={deleteDialogVisible}
                onHide={() => setDeleteDialogVisible(false)}
                onDelete={deleteAbsence}
                absence={selectedAbsence}/>
        </Card>
    );
};

export default AbsenceTable;

