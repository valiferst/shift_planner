/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from 'react';
import {Menubar} from "primereact/menubar";
import {
    HomePageRoute,
    ManageAbsencesRoute,
    ManageDepartmentsRoute,
    ManageShiftplansRoute,
    ManageUsersRoute
} from "../routes";
import {useUser} from "../Contexts/AuthenticatedUserContext";
import {UserxRole} from "../DTO/Userx";

/**
 * Navbar component.
 */
const NavbarComponent: React.FC = () => {
    const {currentUser} = useUser();

    const items = [{
        label: 'Home', icon: 'pi pi-home', url: HomePageRoute.url
    }, {
        label: 'Admin Submenu', icon: 'pi pi-star',
        items: [{
            label: 'Manage Users', icon: 'pi pi-user-edit', url: ManageUsersRoute.url
        },
            {label: 'Manage Departments', icon: 'pi pi-building', url: ManageDepartmentsRoute.url}]
    }, {
        label: 'Manager Submenu', icon: 'pi pi-briefcase',
        items: [{
            label: 'Manage Shiftplans', icon: 'pi pi-calendar-clock', url: ManageShiftplansRoute.url
        }]
    }, {
        label: 'Employee Submenu', icon: 'pi pi-user',
        items: [{
            label: 'Manage Absences', icon: 'pi pi-calendar-times', url: ManageAbsencesRoute.url
        }]
    }, {
        label: "Logout", icon: "pi pi-sign-out", url: "/logout"
    }];


    const getItems = () => {
        const filterLabels= ["Employee Submenu", "Home", "Logout"]
        if (currentUser?.roles.includes(UserxRole.ADMIN)) {
            filterLabels.push("Admin Submenu")
        }
        if (currentUser?.roles.includes(UserxRole.MANAGER)){
            filterLabels.push("Manager Submenu")
        }
        return items.filter(item => filterLabels.includes(item.label));

    }

    return (
        <div className="card">
            <Menubar model={getItems()}/>
        </div>
    );
}

export default NavbarComponent;
