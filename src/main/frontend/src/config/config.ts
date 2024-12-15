/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import axios from "axios";

export const API_BASE_URL = process.env.REACT_APP_BACKEND_SERVER_URL

// Or set globally for all requests
axios.defaults.withCredentials = true;

export const AUTH_STATUS = {
    UNKNOWN: 3,
    AUTHENTICATED: 1,
    UNAUTHENTICATED: 0
}

export const CURRENT_USER_LOCAL_STORAGE_KEY: string = 'currentUser';
