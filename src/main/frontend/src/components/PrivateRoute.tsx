/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {Navigate, Outlet} from 'react-router-dom';
import {LoginsRoute} from '../routes';
import React, {useEffect, useState} from 'react';

import {ProgressSpinner} from 'primereact/progressspinner';

import {API_BASE_URL, AUTH_STATUS} from '../config/config';

/**
 * Private route component that checks if the user is authenticated. Used to protect routes.
 */
const PrivateRoute = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(AUTH_STATUS.UNKNOWN); // null -> Status unknonw, true/false for authentication

    useEffect(() => {
        const checkAuthentication = async () => {
            try {
                const response = await fetch(`${API_BASE_URL}/api/users/authenticated`, {
                    method: 'GET',
                    credentials: 'include', // add cookies to request
                });

                if (response.status === 200) {
                    console.log('status 200');
                    setIsAuthenticated(AUTH_STATUS.AUTHENTICATED);
                } else if (response.status === 401) {
                    console.log('status 401');
                    setIsAuthenticated(AUTH_STATUS.UNAUTHENTICATED);
                } else {
                    console.log('status: ', response.status);
                    setIsAuthenticated(AUTH_STATUS.UNAUTHENTICATED);
                }
            } catch (error: any) {
                console.warn('Backend not available:', error);
                setIsAuthenticated(AUTH_STATUS.UNAUTHENTICATED);
            }
        };

        checkAuthentication();
    }, []); // an empty dependency array signals that the effect is executed only once on mount

    // loading spinner
    if (isAuthenticated === AUTH_STATUS.UNKNOWN) {
        return <ProgressSpinner />;
    }

    return isAuthenticated === AUTH_STATUS.AUTHENTICATED ? <Outlet /> : <Navigate to={LoginsRoute.url} />;
};

export default PrivateRoute;
