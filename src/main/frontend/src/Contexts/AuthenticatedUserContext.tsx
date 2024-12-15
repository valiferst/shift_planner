/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React, {createContext, useContext, useEffect, useState} from 'react';
import {UserDTO, Userx} from '../DTO/Userx';
import {CURRENT_USER_LOCAL_STORAGE_KEY} from "../config/config";

/**
 * A context allows us to access the current user from any component in the component tree.
 * This is useful for components that need to know the current user, but are not directly
 * connected to the component that manages the current user state.
 * For more information, please refer to the React documentation:
 * https://react.dev/learn/passing-data-deeply-with-context
 */


/**
 * The UserContextType defines the shape of the context object, which is used to provide
 * the current user state to the components in the component tree.
 */
interface UserContextType {
    currentUser: UserDTO | null;
    setCurrentUser: (user: UserDTO) => void;
    isLoading: boolean;
    logout: () => void;
    error: Error | null;
}

// Create a new context object
export const UserContext = createContext<UserContextType | null>(null);


/**
 * The UserProvider component is a wrapper component that provides the current user state
 * to all components in the component tree.
 * It also provides functions to update the current user state.
 *
 * @param children The child components of the UserProvider
 * @returns The UserContext.Provider component
 */
export function UserProvider({children}: { children: React.ReactNode }) {

    /**
     * Load the initial state of the current user from the local storage if available.
     *
     * @returns The initial state of the current user or an empty user object
     */
    const loadInitialState = (): UserDTO => {
        if (typeof window === 'undefined') return Userx.empty();

        const savedUser = localStorage.getItem(CURRENT_USER_LOCAL_STORAGE_KEY);
        return savedUser ? JSON.parse(savedUser) : Userx.empty();
    };

    // States the UserProvider manages
    // Docs: https://react.dev/reference/react/useState
    const [currentUser, setCurrentUser] = useState<UserDTO>(loadInitialState);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<Error | null>(null);


    // Persist state changes to localStorage when the currentUser changes
    // Docs: https://react.dev/reference/react/useEffect
    useEffect(() => {
        localStorage.setItem(CURRENT_USER_LOCAL_STORAGE_KEY, JSON.stringify(currentUser));
    }, [currentUser]);

    /**
     * Logout the current user by setting the currentUser state to an empty user object.
     */
    const logout = () => {
        setCurrentUser(Userx.empty());
    };

    return (
        <UserContext.Provider
            value={{
                currentUser,
                setCurrentUser,
                isLoading,
                logout,
                error
            }}
        >
            {children}
        </UserContext.Provider>
    );
}

/**
 * A custom hook that provides access to the current user state.
 * This hook can be used in any component that is a child of the UserProvider.
 *
 * @returns The current user state and functions to update the current user state
 */
export function useUser() {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error('useUser must be used within a UserProvider');
    }
    return context;
}
