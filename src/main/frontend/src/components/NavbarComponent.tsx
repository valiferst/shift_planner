/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from 'react';
import {Menubar} from "primereact/menubar";
import {HomePageRoute, ManageAbsencesRoute, ManageDepartmentsRoute, ManageUsersRoute} from "../routes";
import {useUser} from "../Contexts/AuthenticatedUserContext";
import {UserxRole} from "../DTO/Userx";

/**
 * Navbar component.
 */
const NavbarComponent: React.FC = () => {
    const { currentUser } = useUser();

    const items = [{
        label: 'Home', icon: 'pi pi-home', url: HomePageRoute.url
    }, {
        label: 'Admin Submenu', icon: 'pi pi-star',
        items: [{
            label: 'Manage Users', icon: 'pi pi-user-edit', url: ManageUsersRoute.url
        },
            {label: 'Manage Departments', icon: 'pi pi-building', url: ManageDepartmentsRoute.url}]
    }, {
        label: 'User Submenu', icon: 'pi pi-user',
        items: [{
            label: 'Manage Absences', icon: 'pi pi-calendar-times', url: ManageAbsencesRoute.url
        }]
    }, {
        label: "Logout", icon: "pi pi-sign-out", url: "/logout"
    }];

    const getItems = () => {
        if (currentUser?.roles.includes(UserxRole.ADMIN)) {
            return items;
        } else {
            return items.filter(item => item.label !== "Admin Submenu");
        }
    }

    return (
        <div className="card">
            <Menubar model={getItems()} />
        </div>
    );
}

export default NavbarComponent;
