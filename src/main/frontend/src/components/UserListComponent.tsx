/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";

import {Button} from "primereact/button";
import {Column} from "primereact/column";
import {DataTable} from "primereact/datatable";
import {Tag} from "primereact/tag";

import {Userx} from "../DTO/Userx";
import {Checkbox} from "primereact/checkbox";
import {rolesBodyTemplate} from "../utilities/rolesBodyTemplate";

interface UserListProps {
    users: Userx[];
    loading: boolean;
    onEditUser: (user: Userx) => void;
    onDeleteUser: (user: Userx) => void;
}


/**
 * Component for displaying a list of users in a DataTable.
 * @param users the users to display
 * @param loading whether the users are loading
 * @param onEditUser callback when a user is edited
 * @param onDeleteUser callback when a user is deleted
 */
const UserListComponent: React.FC<UserListProps> = ({ users, loading, onEditUser, onDeleteUser }) => {

    /**
     * Renders the edit button for a user.
     * @param rowData
     */
    const editButtonTemplate = (rowData: Userx) => {
        return (<Button
            label={"Details"}
            icon="pi pi-external-link"
            onClick={() => onEditUser(rowData)}
            aria-label={`Details for ${rowData.username}`}
        />);
    };

    /**
     * Renders the delete button for a user.
     * @param rowData
     */
    const deleteButtonTemplate = (rowData: Userx) => {
        return (
            <Button
                label={"Delete"}
                icon="pi pi-trash"
                className="p-button-rounded p-button-danger"
                onClick={() => onDeleteUser(rowData)}
                aria-label={`Delete ${rowData.username}`}
            />
        );
    }

    /**
     * Renders the enable button for a user.
     * @param rowData
     */
    const enableButtonTemplate = (rowData: Userx) => {
        return (
            <Checkbox checked={rowData.enabled} disabled={true}
                      className="p-mr-2"/>
        )
    }


    return (
        // DataTable for displaying users
        <DataTable value={users} loading={loading}>
            <Column field="username" header="Username" sortable></Column>
            <Column field="firstName" header="First Name" sortable></Column>
            <Column field="lastName" header="Last Name" sortable></Column>
            <Column field="roles" header="Roles" body={rolesBodyTemplate}></Column>
            <Column field="enabled" header="Enabled" body={enableButtonTemplate}></Column>
            <Column body={editButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
            <Column body={deleteButtonTemplate} exportable={false}
                    style={{minWidth: '8rem'}}></Column>
        </DataTable>
    )
};

export default UserListComponent;
