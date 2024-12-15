/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {Button} from "primereact/button";
import React from "react";
import {Dialog} from "primereact/dialog";
import {UserDTO} from "../DTO/Userx";

interface DeleteDialogProps {
    visible: boolean;
    onHide: () => void;
    onDelete: () => void;
    user: UserDTO | null;
}

/**
 * Dialog for deleting a user.
 *
 * @param visible whether the dialog is visible
 * @param onHide callback when the dialog is hidden
 * @param onDelete callback when the user is deleted
 * @param user the user to be deleted
 *
 * @returns the delete dialog
 */
const DeleteDialog: React.FC<DeleteDialogProps> = ({ visible, onHide, onDelete, user }) => {

    /**
     * Renders the contents of the delete dialog.
     */
    const renderDeleteDialogContents = () => {
        return (
            <div>
                <h2>{user?.firstName} {user?.lastName}</h2>
                <h5><span className="p-text-secondary">{user?.username}</span></h5>
                <p>Are you sure you want to delete this user?</p>
            </div>
        );
    }

    /**
     * Renders the footer of the delete dialog.
     */
    const renderDeleteDialogFooter = () => {
        return (
            <div>
                <Button label="Cancel" icon="pi pi-times"
                    onClick={onHide}
                    className="p-button-text" />
                <Button label="Delete" icon="pi pi-trash" onClick={onDelete} autoFocus />
            </div>
        );
    }

    return (
        <Dialog
            header="Delete User"
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderDeleteDialogFooter}
        >
            {renderDeleteDialogContents()}
        </Dialog>
    );
}

export default DeleteDialog;
