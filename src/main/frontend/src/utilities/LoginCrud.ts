/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */

import {LoginDTO} from "../DTO/LoginDTO";
import axios, {AxiosRequestConfig} from "axios";

import {API_BASE_URL} from "../config/config";


/**
 * Try to log in a user
 * @param login the login data of the user (username and password)
 *
 * @returns Promise with the status and data of the response
 * @throws Error if the request fails
 */
const tryUserLogin = async (login: LoginDTO) => {
    try {
        // Set the headers and credentials (this can be done globally for all requests (look it up))
        const config: AxiosRequestConfig = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            withCredentials: true,
        };

        const params = new URLSearchParams(); // URI encoding included
        params.append('username', login.username);
        params.append('password', login.password);

        // Send the request, await the response
        const response = await axios.post(
            `${API_BASE_URL}/api/authentication`,
            params,
            config
        );

        // Return the response
        return {status: response.status, data: response.data};

    } catch (error: any) {
        if (axios.isAxiosError(error)) {
            console.error('Login error:', error.message);
            throw new Error(`Error logging in: ${error.response?.data}`);
        } else {
            throw new Error(`Error logging in: ${error.message}`);
        }
    }
}

export const LoginCrud = {
    tryUserLogin
}
