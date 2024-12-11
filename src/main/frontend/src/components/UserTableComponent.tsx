/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React, {useEffect, useState} from 'react';

import {Button} from "primereact/button";
import {Card} from 'primereact/card';
import {InputMaskChangeEvent} from "primereact/inputmask";
import 'primeicons/primeicons.css';

import UserListComponent from "./UserListComponent";
import UserDialog from "./UserDialog";
import DeleteDialog from "./DeleteDialog";

import {UserDTO, Userx} from "../DTO/Userx";
import {UserCrud} from "../utilities/UserCrud";
import {
    createUserxFromInterfaces,
    createUserxRoleArrayFromStrings
} from '../factories/userxFactory';
import {CheckboxChangeEvent} from "primereact/checkbox";

/**
 * Component for managing users.
 */
const UserTable = () => {
    const [users, setUsers] = useState<Userx[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [selectedUser, setSelectedUser] = useState<UserDTO | null>(null);
    const [isNewUser, setIsNewUser] = useState<boolean>(false);
    const [dialogVisible, setDialogVisible] = useState<boolean>(false);
    const [deleteDialogVisible, setDeleteDialogVisible] = useState<boolean>(false);

    /**
     * Fetch all users from the backend on mount once.
     */
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const userxData = await UserCrud.fetchAllUsers();
                const userxInstances = userxData.map((user: UserDTO) => createUserxFromInterfaces(user));
                setUsers(userxInstances);
            } catch (error: any) {
                console.error('Error fetching users:', error);
            } finally {
                setLoading(false); // Set loading to false regardless of success or failure
            }
        };
        fetchUsers();
    }, []); // empty dependency array means this effect will only run once on mount

    /**
     * Validate the user object.
     * @param user
     */
    const validateUser = (user: UserDTO | null): boolean => {
        if (!user) return false;
        return user.firstName !== '' && user.lastName !== '' && user.username !== '' && user.password !== '';
    }

    /**
     * Handle the submit event for the user dialog.
     */
    const handleSubmit = async () => {
        if (!validateUser(selectedUser)) {
            // Display an error message or handle the validation error
            console.error('Please fill out all required fields.');
            return;
        }

        if (isNewUser) {
            await createUser();
        } else {
            await updateUser();
        }
        hideDialog();
    };

    /**
     * Create a new user and update the state.
     */
    const createUser = async () => {
        if (!selectedUser) return;

        try {
            const newUser: Userx = await UserCrud.createUser(selectedUser);
            setUsers([...users, newUser]);
        } catch (error: any) {
            console.error('Error saving user:', error);
            // Add toast message for error
        }
    }

    /**
     * Update an existing user and update the state.
     */
    const updateUser = async () => {
        if (!selectedUser) return;

        try {
            const updatedUser: Userx = await UserCrud.updateUser(selectedUser);
            setUsers(users.map((user: Userx) => user.id === updatedUser.id ? updatedUser : user));
            hideDialog();
        } catch (error: any) {
            console.error('Error updating user:', error);
        }
    }


    /**
     * Delete a user and update the state.
     */
    const deleteUser = async () => {
        if (!selectedUser) return;

        try {
            await UserCrud.deleteUser(selectedUser);
            setUsers(users.filter((user: Userx) => user.id !== selectedUser.id));
            hideDialog();
        } catch (error) {
            console.error('Error deleting user:', error);
            // TODO: Add toast message for error
        }
        setDeleteDialogVisible(false);
    }

    /**
     * Open the delete dialog for a user.
     * @param user
     */
    const openDeleteDialog = (user: Userx) => {
        setSelectedUser(user);
        setDeleteDialogVisible(true);
    }

    /**
     * Open the edit dialog for a user.
     * @param user
     */
    const openEditDialog = (user: Userx) => {
        setSelectedUser(user);
        setIsNewUser(false);
        showDialog()
    };

    /**
     * Open the dialog for creating a new user.
     */
    const openNewUserDialog = () => {
        setSelectedUser(Userx.empty());
        showDialog()
        setIsNewUser(true);
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
     * Handle input changes for the user dialog.
     * @param event
     */
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => {
        if (!selectedUser) return;

        const {name, value} = event.target;

        setSelectedUser({...selectedUser, [name]: value});
    }

    /**
     * Handle user enabled change for the user dialog.
     * @param event
     */
    const handleUserEnabledChange = (event: CheckboxChangeEvent) => {
        if (!selectedUser) return;

        const {name, checked} = event.target;

        setSelectedUser({...selectedUser, [name]: checked});
    }

    /**
     * Handle roles change for the user dialog.
     * @param event
     */
    const handleRolesChange = (event: { value: string[] }) => {
        if (!selectedUser) return;

        const roles = createUserxRoleArrayFromStrings(event.value);

        setSelectedUser({...selectedUser, roles: roles});
    }


    return (<Card title="User List" className="m-4">
            {/* Button that opens a new user dialog on click */}
            <Button label="Add User" icon="pi pi-plus" className="p-button-raised p-button-rounded"
                    style={{marginBottom: "10px"}} onClick={openNewUserDialog}/>
            <UserListComponent users={users} loading={loading} onEditUser={openEditDialog}
                               onDeleteUser={openDeleteDialog}/>

            {/* Dialog for creating or editing a user */}
            <UserDialog visible={dialogVisible} user={selectedUser} isNewUser={isNewUser}
                        onHide={hideDialog} onSubmit={handleSubmit}
                        onInputChange={handleInputChange} onRolesChange={handleRolesChange}
                        onUserEnabledChange={handleUserEnabledChange}/>
            {/* Dialog for deleting a user */}
            <DeleteDialog
                visible={deleteDialogVisible}
                onHide={() => setDeleteDialogVisible(false)}
                onDelete={deleteUser}
                user={selectedUser}/>
        </Card>
    );
};

export default UserTable;

