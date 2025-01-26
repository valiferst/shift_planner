/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";
import {ShiftPlanDTO, ShiftPlan} from "../DTO/ShiftPlan";
import {createShiftPlanFromInterfaces} from "../factories/shiftPlanFactory";

import {API_BASE_URL} from "../config/config";
import {ValidationError} from "../DTO/ValidationError";

/**
 * This file provides utility functions for CRUD operations on shiftPlans.
 */

/**
 * Fetch all shiftPlans from the backend
 * @returns Promise<ShiftPlanDTO[]> a promise that resolves with an array of ShiftPlanDTO objects
 * @throws Error if the request fails
 */
const fetchAllShiftPlans = async (): Promise<ShiftPlanDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/shiftplans`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching shiftPlans: ${error.message}`);
    }
}

/**
 * Create a new shiftPlan
 * @param selectedShiftPlan the shiftPlan to create
 * @returns Promise<ShiftPlan> a promise that resolves with the created shiftPlan
 * @throws Error if the request fails
 */
const createShiftPlan = async (selectedShiftPlan: ShiftPlanDTO): Promise<ShiftPlan> => {
    try {
        const shiftPlanInstance = createShiftPlanFromInterfaces(selectedShiftPlan);
        const response = await axios.post(`${API_BASE_URL}/api/shiftplans`, shiftPlanInstance.toCreateJSON(), {
            withCredentials: true
        });
        return ShiftPlan.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error saving shiftPlan: ${error.message}`);
    }
}

/**
 * Update an existing shiftPlan
 * @param selectedShiftPlan the shiftPlan to update
 * @returns Promise<ShiftPlan> a promise that resolves with the updated shiftPlan
 * @throws Error if the request fails
 */
const updateShiftPlan = async (selectedShiftPlan: ShiftPlanDTO): Promise<ShiftPlan> => {
    try {
        const shiftPlanInstance = createShiftPlanFromInterfaces(selectedShiftPlan);
        const response = await axios.patch(`${API_BASE_URL}/api/shiftplans/${selectedShiftPlan.id}`, shiftPlanInstance.toJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return ShiftPlan.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error updating shiftPlan: ${error.message}`);
    }
}

const publishShiftPlan = async (selectedShiftPlan: ShiftPlanDTO): Promise<ValidationError[]> => {
    try {
        const shiftPlanInstance = createShiftPlanFromInterfaces(selectedShiftPlan);
        const response = await axios.patch(`${API_BASE_URL}/api/shiftplans/${selectedShiftPlan.id}/publish`, shiftPlanInstance.toJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error publishing shiftPlan: ${error.message}`);
    }
}



/**
 * Delete an existing shiftPlan
 * @param selectedShiftPlan the shiftPlan to delete
 * @returns Promise<any> a promise that resolves with the response data
 * @throws Error if the request fails
 */
const deleteShiftPlan = async (selectedShiftPlan: ShiftPlanDTO) => {
    try {
        return await axios.delete(`${API_BASE_URL}/api/shiftplans/${selectedShiftPlan.id}`, {
            withCredentials: true
        });
    } catch (error: any) {
        throw new Error(`Error deleting shiftPlan: ${error.message}`);
    }
}

export const ShiftPlanCrud = {
    createShiftPlan,
    updateShiftPlan,
    publishShiftPlan,
    deleteShiftPlan,
    fetchAllShiftPlans
}
