/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";
import {DepartmentDTO, Department} from "../DTO/Department";
import {createDepartmentFromInterfaces} from "../factories/departmentFactory";

import {API_BASE_URL} from "../config/config";

/**
 * This file provides utility functions for CRUD operations on departments.
 */

/**
 * Fetch all departments from the backend
 * @returns Promise<DepartmentDTO[]> a promise that resolves with an array of DepartmentDTO objects
 * @throws Error if the request fails
 */
const fetchAllDepartments = async (): Promise<DepartmentDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/departments`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching departments: ${error.message}`);
    }
}

/**
 * Fetch all departments from the backend
 * @returns Promise<DepartmentDTO[]> a promise that resolves with an array of DepartmentDTO objects
 * @throws Error if the request fails
 */
const fetchManagerDepartments= async (): Promise<DepartmentDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/departments/my`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching departments: ${error.message}`);
    }
}


/**
 * Create a new department
 * @param selectedDepartment the department to create
 * @returns Promise<Department> a promise that resolves with the created department
 * @throws Error if the request fails
 */
const createDepartment = async (selectedDepartment: DepartmentDTO): Promise<Department> => {
    try {
        const departmentInstance = createDepartmentFromInterfaces(selectedDepartment);
        const response = await axios.post(`${API_BASE_URL}/api/departments/`, departmentInstance.toCreateJSON(), {
            withCredentials: true
        });
        return Department.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error saving department: ${error.message}`);
    }
}

/**
 * Update an existing department
 * @param selectedDepartment the department to update
 * @returns Promise<Department> a promise that resolves with the updated department
 * @throws Error if the request fails
 */
const updateDepartment = async (selectedDepartment: DepartmentDTO): Promise<Department> => {
    try {
        const departmentInstance = createDepartmentFromInterfaces(selectedDepartment);
        const response = await axios.patch(`${API_BASE_URL}/api/departments/${selectedDepartment.id}`, departmentInstance.toUpdateJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return Department.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error updating department: ${error.message}`);
    }
}

/**
 * Delete an existing department
 * @param selectedDepartment the department to delete
 * @returns Promise<any> a promise that resolves with the response data
 * @throws Error if the request fails
 */
const deleteDepartment = async (selectedDepartment: DepartmentDTO) => {
    try {
        return await axios.delete(`${API_BASE_URL}/api/departments/${selectedDepartment.id}`, {
            withCredentials: true
        });
    } catch (error: any) {
        throw new Error(`Error deleting department: ${error.message}`);
    }
}

export const DepartmentCrud = {
    createDepartment,
    updateDepartment,
    deleteDepartment,
    fetchManagerDepartments,
    fetchAllDepartments
}
