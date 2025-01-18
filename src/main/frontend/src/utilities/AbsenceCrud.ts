/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";
import {AbsenceDTO, Absence} from "../DTO/Absence";
import {createAbsenceFromInterfaces} from "../factories/absenceFactory";

import {API_BASE_URL} from "../config/config";

/**
 * This file provides utility functions for CRUD operations on absences.
 */

/**
 * Fetch all absences from the backend
 * @returns Promise<AbsenceDTO[]> a promise that resolves with an array of AbsenceDTO objects
 * @throws Error if the request fails
 */
const fetchAllAbsences = async (): Promise<AbsenceDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users/absences`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching absences: ${error.message}`);
    }
}

/**
 * Create a new absence
 * @param selectedAbsence the absence to create
 * @returns Promise<Absence> a promise that resolves with the created absence
 * @throws Error if the request fails
 */
const createAbsence = async (selectedAbsence: AbsenceDTO): Promise<Absence> => {
    try {
        const absenceInstance = createAbsenceFromInterfaces(selectedAbsence);
        const response = await axios.post(`${API_BASE_URL}/api/users/absences`, absenceInstance.toCreateJSON(), {
            withCredentials: true
        });
        return Absence.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error saving absence: ${error.message}`);
    }
}

/**
 * Update an existing absence
 * @param selectedAbsence the absence to update
 * @returns Promise<Absence> a promise that resolves with the updated absence
 * @throws Error if the request fails
 */
const updateAbsence = async (selectedAbsence: AbsenceDTO): Promise<Absence> => {
    try {
        const absenceInstance = createAbsenceFromInterfaces(selectedAbsence);
        const response = await axios.patch(`${API_BASE_URL}/api/users/absences/${selectedAbsence.id}`, absenceInstance.toUpdateJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return Absence.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error updating absence: ${error.message}`);
    }
}

/**
 * Delete an existing absence
 * @param selectedAbsence the absence to delete
 * @returns Promise<any> a promise that resolves with the response data
 * @throws Error if the request fails
 */
const deleteAbsence = async (selectedAbsence: AbsenceDTO) => {
    try {
        return await axios.delete(`${API_BASE_URL}/api/users/absences/${selectedAbsence.id}`, {
            withCredentials: true
        });
    } catch (error: any) {
        throw new Error(`Error deleting absence: ${error.message}`);
    }
}

export const AbsenceCrud = {
    createAbsence,
    updateAbsence,
    deleteAbsence,
    fetchAllAbsences
}
