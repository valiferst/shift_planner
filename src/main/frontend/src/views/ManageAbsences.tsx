/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import '../styles/App.css';
import "primereact/resources/themes/lara-light-cyan/theme.css";
import React from "react";
import NavbarComponent from "../components/NavbarComponent";
import {FooterComponent} from "../components/FooterComponent";
import AbsenceTableComponent from "../components/AbsenceTableComponent";

/**
 * Component / View for managing users.
 */

class ManageAbsences extends React.Component {

    render() {
        return (
            <div>
                <NavbarComponent />
                <AbsenceTableComponent />
                <FooterComponent />
            </div>
        );
    }
}

export default ManageAbsences;
