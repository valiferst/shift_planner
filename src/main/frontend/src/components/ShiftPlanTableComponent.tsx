/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React, {useEffect, useState} from 'react';

import {Button} from "primereact/button";
import {Card} from 'primereact/card';
import {InputMaskChangeEvent} from "primereact/inputmask";
import 'primeicons/primeicons.css';

import ShiftPlanListComponent from "./ShiftPlanListComponent";
import ShiftPlanDialog from "./ShiftPlanDialog";
import ShiftPlanPublishDialog from "./ShiftPlanPublishDialog";
import ShiftPlanDeleteDialog from "./ShiftPlanDeleteDialog";

import {ShiftPlan, ShiftPlanDTO, ShiftPlanState} from "../DTO/ShiftPlan";
import {ShiftPlanCrud} from "../utilities/ShiftPlanCrud";
import {createShiftPlanFromInterfaces} from '../factories/shiftPlanFactory';
import {createDepartmentFromInterfaces} from '../factories/departmentFactory';
import {Nullable} from "primereact/ts-helpers";
import {ValidationError} from "../DTO/ValidationError";
import {DepartmentCrud} from "../utilities/DepartmentCrud";
import {Department, DepartmentDTO} from "../DTO/Department";
import {DropdownChangeEvent} from "primereact/dropdown";

/**
 * Component for managing shiftPlans.
 */
const ShiftPlanTable = () => {
    const [shiftPlans, setShiftPlans] = useState<ShiftPlan[]>([]);
    const [departments, setDepartments] = useState<Department[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [selectedShiftPlan, setSelectedShiftPlan] = useState<ShiftPlanDTO | null>(null);
    const [isNewShiftPlan, setIsNewShiftPlan] = useState<boolean>(false);
    const [dialogVisible, setDialogVisible] = useState<boolean>(false);
    const [publishDialogVisible, setPublishDialogVisible] = useState<boolean>(false);
    const [deleteDialogVisible, setDeleteDialogVisible] = useState<boolean>(false);

    useEffect(() => {
        const fetchShiftPlans = async () => {
            try {
                // TODO: implement using fetch shiftplans for manager
                const shiftPlanData = await ShiftPlanCrud.fetchAllShiftPlans();
                const shiftPlanInstances = shiftPlanData.map((shiftPlan: ShiftPlanDTO) => createShiftPlanFromInterfaces(shiftPlan));
                setShiftPlans(shiftPlanInstances);
            } catch (error: any) {
                console.error('Error fetching shiftPlans:', error);
            } finally {
                setLoading(false); // Set loading to false regardless of success or failure
            }
        };
        const fetchManagerDepartments = async () => {
            try {
                const departmentData= await DepartmentCrud.fetchManagerDepartments();
                const departmentInstances = departmentData.map((department: DepartmentDTO) => createDepartmentFromInterfaces(department));
                setDepartments(departmentInstances);
            } catch (error: any) {
                console.error('Error fetching departments:', error);
            }
        };
        fetchShiftPlans();
        fetchManagerDepartments();
    }, []); // empty dependency array means this effect will only run once on mount

    /**
     * Validate the shiftPlan object.
     * @param shiftPlan
     */
    const  validateShiftPlan = (shiftPlan: ShiftPlanDTO | null): boolean => {
        if (!shiftPlan) return false;
        return shiftPlan.startDate !== null &&
            shiftPlan.endDate !== null &&
            shiftPlan.name !== null && shiftPlan.startDate <= shiftPlan.endDate;
    }


    /**
     * Handle the submit event for the shiftPlan dialog.
     */
    const handleSubmit = async () => {
        if (!validateShiftPlan(selectedShiftPlan)) {
            // Display an error message or handle the validation error
            console.error('Error submitting ShiftPlan, please check for overlaps in Shifts.');
            return;
        }
        if (isNewShiftPlan) {
            await createShiftPlan();
        } else {
            await updateShiftPlan();
        }
        hideDialog();
    };

    /**
     * Create a new shiftPlan and update the state.
     */
    const createShiftPlan = async () => {
        if (!selectedShiftPlan) return;

        try {
            const newShiftPlan: ShiftPlan = await ShiftPlanCrud.createShiftPlan(selectedShiftPlan);
            setShiftPlans([...shiftPlans, newShiftPlan]);
        } catch (error: any) {
            console.error('Error saving shiftPlan:', error);
            // Add toast message for error
        }
    }

    /**
     * Update an existing shiftPlan and update the state.
     */
    const updateShiftPlan = async () => {
        if (!selectedShiftPlan) return;

        try {
            const updatedShiftPlan: ShiftPlan = await ShiftPlanCrud.updateShiftPlan(selectedShiftPlan);
            setShiftPlans(shiftPlans.map((shiftPlan: ShiftPlan) => shiftPlan.id === updatedShiftPlan.id ? updatedShiftPlan : shiftPlan));
            hideDialog();
        } catch (error: any) {
            console.error('Error updating shiftPlan:', error);
        }
    }

        /**
         * Publish an existing shiftPlan and update the state.
         */
        const publishShiftPlan = async () => {
            if (!selectedShiftPlan) return;

            try {
                const validationErrors: ValidationError[] = await ShiftPlanCrud.publishShiftPlan(selectedShiftPlan);
                if (validationErrors.length === 0) {
                    shiftPlans.filter(shiftPlan => shiftPlan.departmentId === selectedShiftPlan.departmentId)
                        .forEach(shiftPlan => {
                            if (shiftPlan.state === ShiftPlanState.PUBLISHED) shiftPlan.state = ShiftPlanState.CANCELLED
                        });
                    selectedShiftPlan.state = ShiftPlanState.PUBLISHED
                    setPublishDialogVisible(false)
                } else {
                    validationErrors.forEach(valError => console.error(`${valError.error} for user: ${valError.user.fullNameWithUsername} in shift ${valError.shift.shiftIdentification}`))
                }

            } catch (error: any) {
                console.error('Error publishing shiftPlan:', error);
            }
        }

    /**
     * Delete a shiftPlan and update the state.
     */
    const deleteShiftPlan = async () => {
        if (!selectedShiftPlan) return;

        try {
            await ShiftPlanCrud.deleteShiftPlan(selectedShiftPlan);
            setShiftPlans(shiftPlans.filter((shiftPlan: ShiftPlan) => shiftPlan.id !== selectedShiftPlan.id));
            hideDialog();
        } catch (error) {
            console.error('Error deleting shiftPlan:', error);
            // TODO: Add toast message for error
        }
        setDeleteDialogVisible(false);
    }

    /**
     * Open the delete dialog for a shiftPlan.
     * @param shiftPlan
     */
    const openDeleteDialog = (shiftPlan: ShiftPlan) => {
        setSelectedShiftPlan(shiftPlan);
        setDeleteDialogVisible(true);
    }

    /**
     * Open the edit dialog for an shiftPlan.
     * @param shiftPlan
     */
    const openEditDialog = (shiftPlan: ShiftPlan) => {
        setSelectedShiftPlan(shiftPlan);
        setIsNewShiftPlan(false);
        showDialog()
    };

    /**
     * Open the publish dialog for a shiftPlan.
     * @param shiftPlan
     */
    const openPublishDialog = (shiftPlan: ShiftPlan) => {
        setSelectedShiftPlan(shiftPlan);
        setPublishDialogVisible(true);
    };

    /**
     * Hide the publish dialog.
     */
    const hidePublishDialog = () => {
        setPublishDialogVisible(false);
    };

    /**
     * Open the dialog for creating a new shiftPlan.
     */
    const openNewShiftPlanDialog = () => {
        setSelectedShiftPlan(ShiftPlan.empty());
        showDialog()
        setIsNewShiftPlan(true);
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
     * Handle input changes for the shiftPlan dialog.
     * @param event
     */
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => {
        if (!selectedShiftPlan) return;

        const { name, value } = event.target;

        setSelectedShiftPlan({ ...selectedShiftPlan, [name]: value });
    }

    /**
     * Handle input changes for the absence dialog: times.
     * @param name
     * @param event
     */
    const handleTimeChange = (name: 'startDate' | 'endDate' , event: Nullable<Date>) => {
        if (!selectedShiftPlan) return;
        setSelectedShiftPlan({ ...selectedShiftPlan, [name]: event });
    }


    const handleDepartmentChange = (event: DropdownChangeEvent) => {
        if (!selectedShiftPlan) return;

        console.log(event)
        const selectedDepartment = event.value
        console.log(selectedDepartment)


        // console.log(selectedShiftPlan)
        setSelectedShiftPlan({...selectedShiftPlan, departmentId: selectedDepartment.id, departmentName: selectedDepartment.name});
        console.log(selectedShiftPlan)
    }

    return (<Card title="ShiftPlan List" className="m-4">
        {/* Button that opens a new shiftPlan dialog on click */}
        <Button label="Add ShiftPlan" icon="pi pi-plus" className="p-button-raised p-button-rounded"
            style={{ marginBottom: "10px" }} onClick={openNewShiftPlanDialog} />
        <ShiftPlanListComponent shiftPlans={shiftPlans} loading={loading} onEditShiftPlan={openEditDialog}
            onPublishShiftPlan={openPublishDialog} onDeleteShiftPlan={openDeleteDialog} />

        {/* Dialog for creating or editing an shiftPlan */}
        <ShiftPlanDialog visible={dialogVisible} shiftPlan={selectedShiftPlan} isNewShiftPlan={isNewShiftPlan}
            departments={departments} onHide={hideDialog} onSubmit={handleSubmit}
            onInputChange={handleInputChange} onTimeChange={handleTimeChange}
            onDepartmentChange={handleDepartmentChange}/>

        {/* Dialog for creating or publishing an shiftPlan */}
        <ShiftPlanPublishDialog visible={publishDialogVisible} shiftPlan={selectedShiftPlan}
                         onHide={hidePublishDialog} onPublish={publishShiftPlan}
                         onInputChange={handleInputChange} />
        {/* Dialog for deleting an shiftPlan */}
        <ShiftPlanDeleteDialog
            visible={deleteDialogVisible}
            onHide={() => setDeleteDialogVisible(false)}
            onDelete={deleteShiftPlan}
            shiftPlan={selectedShiftPlan} />
    </Card>
    );
};

export default ShiftPlanTable;

