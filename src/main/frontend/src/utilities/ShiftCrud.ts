/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";
import {ShiftDTO, Shift} from "../DTO/Shift";
import {createShiftFromInterfaces} from "../factories/shiftFactory";

import {API_BASE_URL} from "../config/config";

/**
 * This file provides utility functions for CRUD operations on shifts.
 */

/**
 * Fetch all shiftPlans from the backend
 * @returns Promise<ShiftPlanDTO[]> a promise that resolves with an array of ShiftPlanDTO objects
 * @throws Error if the request fails
 */
const fetchAllShifts = async (): Promise<ShiftDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/shifts`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching shifts: ${error.message}`);
    }
}


/**
 * Create a new shift
 * @param selectedShift the shift to create
 * @returns Promise<Shift> a promise that resolves with the created shift
 * @throws Error if the request fails
 */
const createShift = async (selectedShift: ShiftDTO): Promise<Shift> => { try { const shiftInstance = createShiftFromInterfaces(selectedShift);
    const response = await axios.post(`${API_BASE_URL}/api/shifts`, shiftInstance.toCreateJSON(), {
        withCredentials: true
    });
    return Shift.fromJSON(response.data);
} catch (error: any) {
    throw new Error(`Error saving shift: ${error.message}`);
}
}

/**
 * Update an existing shift
 * @param selectedShift the shift to update
 * @returns Promise<Shift> a promise that resolves with the updated shift
 * @throws Error if the request fails
 */
const updateShift = async (selectedShift: ShiftDTO): Promise<Shift> => {
    try {
        const shiftInstance = createShiftFromInterfaces(selectedShift);
        const response = await axios.patch(`${API_BASE_URL}/api/shifts/${selectedShift.id}`, shiftInstance.toJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return Shift.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error updating shift: ${error.message}`);
    }
}


/**
 * Delete an existing shift
 * @param selectedShift the shift to delete
 * @returns Promise<any> a promise that resolves with the response data
 * @throws Error if the request fails
 */
const deleteShift = async (selectedShift: ShiftDTO) => {
    try {
        return await axios.delete(`${API_BASE_URL}/api/shift/${selectedShift.id}`, {
            withCredentials: true
        });
    } catch (error: any) {
        throw new Error(`Error deleting shift: ${error.message}`);
    }
}

export const ShiftCrud = {
    createShift,
    updateShift,
    deleteShift,
    fetchAllShifts
}
