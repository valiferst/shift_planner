/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from "react";
import {UserDTO, UserxRole} from "../DTO/Userx";
import {InputMask, InputMaskChangeEvent} from "primereact/inputmask";
import {InputText} from "primereact/inputtext";
import {Password} from "primereact/password";
import {MultiSelect} from "primereact/multiselect";
import {Checkbox, CheckboxChangeEvent} from "primereact/checkbox";


interface UserFormProps {
    user: UserDTO,
    isNewUser: boolean,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
    onRolesChange: (event: { value: string[] }) => void,
    onUserEnabledChange: (event: CheckboxChangeEvent) => void
}

/**
 * Form for creating or editing a user.
 * @param user the user to be edited
 * @param isNewUser whether the user is new
 * @param onInputChange callback when the input changes
 * @param onRolesChange callback when the roles change
 * @param onUserEnabledChange callback when the user is enabled or disabled
 */
const UserForm: React.FC<UserFormProps> =
    ({
        user,
        isNewUser,
        onInputChange,
        onRolesChange,
        onUserEnabledChange
    }) => {
        const userRoles = Object.values(UserxRole).map(role => ({ label: role, value: role }));

        return (
            <div>
                <h1>{user.firstName} {user.lastName}</h1>
                <h5><span className="p-text-secondary">{user.username}</span></h5>
                {/* create form */}
                <div className="card p-fluid flex flex-wrap gap-3">
                    <div className="flex-auto mb-3">
                        <label htmlFor="username" className="font-bold block">Username</label>
                        <InputText id="username" name="username" value={user.username}
                            onChange={onInputChange} required={true}
                            placeholder="Username" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="firstName" className="font-bold block">First
                            Name</label>
                        <InputText id="firstName" name="firstName" value={user.firstName}
                            onChange={onInputChange}
                            placeholder="First Name" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="lastName" className="font-bold block">Last Name</label>
                        <InputText id="lastName" name="lastName" value={user.lastName}
                            onChange={onInputChange}
                            placeholder="Last Name" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="email" className="font-bold block">E-Mail</label>
                        <InputText id="email" name="email" value={user.email ?? ''}
                            onChange={onInputChange} placeholder="E-Mail" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="password" className="font-bold block">Password</label>
                        <Password id="password" name="password" value={user.password}
                            onChange={onInputChange}
                            placeholder="Password" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="roles" className="font-bold block">Roles</label>
                        <MultiSelect name="roles" value={user.roles} onChange={onRolesChange}
                            options={userRoles} optionLabel="label"
                            placeholder="Select Roles"
                            className="w-full md:w-20rem" />
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="phone" className="font-bold block">Phone</label>
                        <InputMask id="phone" name="phone" mask="+99 999 9999999"
                            onChange={onInputChange}
                            placeholder="+43 123 1234567"
                            value={user.phone ?? ''}></InputMask>
                    </div>
                    <div className="flex-auto mb-3">
                        <label htmlFor="enabled" className="font-bold block">Enabled</label>
                        <Checkbox id="enabled" name="enabled"
                            style={{ float: "right" }}
                            onChange={onUserEnabledChange}
                            checked={user.enabled}></Checkbox>
                    </div>
                </div>
            </div>
        )

    }

export default UserForm;
