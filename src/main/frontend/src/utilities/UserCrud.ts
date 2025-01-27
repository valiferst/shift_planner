/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";
import {UserDTO, Userx} from "../DTO/Userx";
import {createUserxFromInterfaces} from "../factories/userxFactory";

import {API_BASE_URL} from "../config/config";

/**
 * This file provides utility functions for CRUD operations on users.
 */

/**
 * Fetch all users from the backend
 * @returns Promise<UserDTO[]> a promise that resolves with an array of UserDTO objects
 * @throws Error if the request fails
 */
const fetchAllUsers = async (): Promise<UserDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/admin`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching users: ${error.message}`);
    }
}

/**
 * Fetch all users from the backend
 * @returns Promise<UserDTO[]> a promise that resolves with an array of UserDTO objects
 * @throws Error if the request fails
 */
const fetchAllEmployees= async (): Promise<UserDTO[]> => {
    try {
        const response = await axios.get(`${API_BASE_URL}/api/users/employees`, {
            withCredentials: true
        });
        return response.data;
    } catch (error: any) {
        throw new Error(`Error fetching users: ${error.message}`);
    }
}

/**
 * Create a new user
 * @param selectedUser the user to create
 * @returns Promise<Userx> a promise that resolves with the created user
 * @throws Error if the request fails
 */
const createUser = async (selectedUser: UserDTO): Promise<Userx> => {
    try {
        const userxInstance = createUserxFromInterfaces(selectedUser);
        const response = await axios.post(`${API_BASE_URL}/api/admin`, userxInstance.toCreateJSON(), {
            withCredentials: true
        });
        return Userx.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error saving user: ${error.message}`);
    }
}

/**
 * Update an existing user
 * @param selectedUser the user to update
 * @returns Promise<Userx> a promise that resolves with the updated user
 * @throws Error if the request fails
 */
const updateUser = async (selectedUser: UserDTO): Promise<Userx> => {
    try {
        const userxInstance = createUserxFromInterfaces(selectedUser);
        const response = await axios.patch(`${API_BASE_URL}/api/admin/${selectedUser.id}`, userxInstance.toUpdateJSON(), {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        });
        return Userx.fromJSON(response.data);
    } catch (error: any) {
        throw new Error(`Error updating user: ${error.message}`);
    }
}

/**
 * Delete an existing user
 * @param selectedUser the user to delete
 * @returns Promise<any> a promise that resolves with the response data
 * @throws Error if the request fails
 */
const deleteUser = async (selectedUser: UserDTO) => {
    try {
        return await axios.delete(`${API_BASE_URL}/api/admin/${selectedUser.id}`, {
            withCredentials: true
        });
    } catch (error: any) {
        throw new Error(`Error deleting user: ${error.message}`);
    }
}

export const UserCrud = {
    createUser,
    updateUser,
    deleteUser,
    fetchAllUsers,
    fetchAllEmployees
}
