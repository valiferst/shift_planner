import {Department, DepartmentDTO} from "../DTO/Department";
import React, {useEffect, useState} from "react";
import {DepartmentCrud} from "../utilities/DepartmentCrud";
import {createDepartmentFromInterfaces} from "../factories/departmentFactory"
import {InputMaskChangeEvent} from "primereact/inputmask";
import {Card} from "primereact/card";
import {Button} from "primereact/button";
import DepartmentDeleteDialog from "./DepartmentDeleteDialog";
import DepartmentListComponent from "./DepartmentListComponent";
import DepartmentDialog from "./DepartmentDialog";

const DepartmentTable = () => {
    const [departments, setDepartments] = useState<Department[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [selectedDepartment, setSelectedDepartment] = useState<DepartmentDTO | null>(null);
    const [isNewDepartment, setIsNewDepartment] = useState<boolean>(false);
    const [dialogVisible, setDialogVisible] = useState<boolean>(false);
    const [deleteDialogVisible, setDepartmentDeleteDialogVisible] = useState<boolean>(false);

    /**
     * Fetch all departments from the backend on mount once.
     */
    useEffect(() => {
        const fetchDepartments = async () => {
            try {
                const departmentData = await DepartmentCrud.fetchAllDepartments();
                const departmentInstances = departmentData.map((department: DepartmentDTO) => createDepartmentFromInterfaces(department));
                setDepartments(departmentInstances);
            } catch (error: any) {
                console.error('Error fetching departments:', error);
            } finally {
                setLoading(false); // Set loading to false regardless of success or failure
            }
        };
        fetchDepartments();
    }, []); // empty dependency array means this effect will only run once on mount

    /**
     * Validate the department object.
     * Ensures that all required fields are non-empty strings.
     * @param department - The department object to validate.
     * @returns True if the department is valid, false otherwise.
     */
    const validateDepartment = (department: DepartmentDTO | null): boolean => {
        if (!department) return false;

        const { name, openingTime, closingTime, fullManagerName } = department;

        return name.trim() !== '' && openingTime instanceof Date && !isNaN(openingTime.getTime()) && closingTime instanceof Date && !isNaN(closingTime.getTime()) && true && fullManagerName.trim() !== '';
    };

    /**
     * Handle the submit event for the department dialog.
     */
    const handleSubmit = async () => {
        if (!validateDepartment(selectedDepartment)) {
            // Display an error message or handle the validation error
            console.error('Please fill out all required fields.');
            return;
        }

        if (isNewDepartment) {
            await createDepartment();
        } else {
            await updateDepartment();
        }
        hideDialog();
    };

    /**
     * Create a new department and update the state.
     */
    const createDepartment = async () => {
        if (!selectedDepartment) return;

        try {
            const newDepartment: Department = await DepartmentCrud.createDepartment(selectedDepartment);
            setDepartments([...departments, newDepartment]);
        } catch (error: any) {
            console.error('Error saving department:', error);
            //TODO Add toast message for error
        }
    }

    /**
     * Update an existing department and update the state.
     */
    const updateDepartment = async () => {
        if (!selectedDepartment) return;

        try {
            const updatedDepartment: Department = await DepartmentCrud.updateDepartment(selectedDepartment);
            setDepartments(departments.map((department: Department) => department.id === updatedDepartment.id ? updatedDepartment : department));
            hideDialog();
        } catch (error: any) {
            console.error('Error updating department:', error);
        }
    }

    /**
     * Delete a department and update the state.
     */
    const deleteDepartment = async () => {
        if (!selectedDepartment) return;

        try {
            await DepartmentCrud.deleteDepartment(selectedDepartment);
            setDepartments(departments.filter((department: Department) => department.id !== selectedDepartment.id));
            hideDialog();
        } catch (error) {
            console.error('Error deleting department:', error);
            // TODO: Add toast message for error
        }
        setDepartmentDeleteDialogVisible(false);
    }

    /**
     * Open the delete dialog for a department.
     * @param department
     */
    const openDepartmentDeleteDialog = (department: Department) => {
        setSelectedDepartment(department);
        setDepartmentDeleteDialogVisible(true);
    }

    /**
     * Open the edit dialog for a department.
     * @param department
     */
    const openEditDialog = (department: Department) => {
        setSelectedDepartment(department);
        setIsNewDepartment(false);
        showDialog()
    };

    /**
     * Open the dialog for creating a new department.
     */
    const openNewDepartmentDialog = () => {
        setSelectedDepartment(Department.empty());
        showDialog()
        setIsNewDepartment(true);
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
     * Handle input changes for the department dialog.
     * @param event
     */
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => {
        if (!selectedDepartment) return;

        const {name, value} = event.target;

        setSelectedDepartment({...selectedDepartment, [name]: value});
    }

    const handleDateChange = () => {
        // TODO implement handleDateChange
    }

    const handleManagerChange = () => {
        // TODO implement DateChange
    }

    return (<Card title="Department List" className="m-4">
            {/* Button that opens a new department dialog on click */}
            <Button label="Add Department" icon="pi pi-plus" className="p-button-raised p-button-rounded"
                    style={{marginBottom: "10px"}} onClick={openNewDepartmentDialog}/>
            <DepartmentListComponent departments={departments} loading={loading} onEditDepartment={openEditDialog}
                               onDeleteDepartment={openDepartmentDeleteDialog}/>

            {/* Dialog for creating or editing a department */}
            <DepartmentDialog visible={dialogVisible} department={selectedDepartment} isNewDepartment={isNewDepartment}
                        onHide={hideDialog} onSubmit={handleSubmit}
                        onInputChange={handleInputChange} onDateChange={handleDateChange} onManagerChange={handleManagerChange}
                        />
            {/* Dialog for deleting a department */}
            <DepartmentDeleteDialog
                visible={deleteDialogVisible}
                onHide={() => setDepartmentDeleteDialogVisible(false)}
                onDelete={deleteDepartment}
                department={selectedDepartment}/>
        </Card>
    );



};

export default DepartmentTable;