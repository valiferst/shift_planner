import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import ShiftDialog from './ShiftDialog';
import {Shift, ShiftDTO} from '../DTO/Shift';
import {Userx} from '../DTO/Userx';
import { ShiftCrud } from '../utilities/ShiftCrud';
import {ShiftPlanDTO} from "../DTO/ShiftPlan";
import {ShiftPlanCrud} from "../utilities/ShiftPlanCrud";
import {Nullable} from "primereact/ts-helpers";
import {InputMaskChangeEvent} from "primereact/inputmask";
import {createShiftFromInterfaces} from "../factories/shiftFactory";

interface ShiftTableProps{
    shiftPlan: ShiftPlanDTO;
    isNewShiftPlan: boolean;
    employees: Userx[];
}

const ShiftTableComponent: React.FC<ShiftTableProps> = ({
        shiftPlan,
        isNewShiftPlan,
        employees
    }) => {
    const [shifts, setShifts] = useState<Shift[]>([]);
    const [isNewShift, setIsNewShift] = useState<boolean>(false);
    const [selectedShift, setSelectedShift] = useState<ShiftDTO | null>(null);
    const [dialogVisible, setDialogVisible] = useState<boolean>(false);

    useEffect(() => {
        const fetchShifts = async () => {
            if (!isNewShiftPlan) {
                const shiftData = await ShiftPlanCrud.getShiftsForShiftPlan(shiftPlan);
                const shiftInstances = shiftData.map((shift: ShiftDTO) => createShiftFromInterfaces(shift));
                setShifts(shiftInstances);
            }
        };
        fetchShifts();
    }, []);

    const openNewShiftDialog = () => {
        setSelectedShift(Shift.empty())
        setIsNewShift(true)
        showDialog()
    };

    const openEditShiftDialog = (shift: ShiftDTO) => {
        setSelectedShift(shift);
        setIsNewShift(false)
        setDialogVisible(true);
    };

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

    const handleSubmit = async () => {
        if (!selectedShift) return;

        const targetShift = {...selectedShift, shiftPlanId: shiftPlan.id!}
        setSelectedShift(targetShift)
        if (targetShift.id) {
            await updateShift(targetShift);
        } else {
            await createShift(targetShift);
        }
        setDialogVisible(false);
    };

    /**
     * Create a new shift and update the state.
     */
    const createShift = async (createShift: {
        id?: number;
        startTime: Date | null;
        endTime: Date | null;
        shiftPlanId: number;
        shiftWorkerIds: number[];
        shiftWorkerNames: string[]
    }) => {

        try {
            const newShift: Shift = await ShiftCrud.createShift(createShift);
            setShifts([...shifts, newShift]);
        } catch (error: any) {
            console.error('Error saving shift:', error);
            // Add toast message for error
        }
    }

    /**
     * Update an existing shift and update the state.
     */
    const updateShift = async (updateShift: {
        id?: number;
        startTime: Date | null;
        endTime: Date | null;
        shiftPlanId: number;
        shiftWorkerIds: number[];
        shiftWorkerNames: string[]
    }) => {

        try {
            const updatedShift: Shift = await ShiftCrud.updateShift(updateShift);
            setShifts(shifts.map((shift: Shift) => shift.id === updatedShift.id ? updatedShift : shift));
            hideDialog();
        } catch (error: any) {
            console.error('Error updating shift:', error);
        }
    }

    const editButtonTemplate = (shift: ShiftDTO) => (
        <Button label="Edit" icon="pi pi-pencil" onClick={() => openEditShiftDialog(shift)} />
    );

    const handleEmployeeChange = (event: {value: Userx[]}) => {
        if (!selectedShift) return;

        console.log(event.value)
        const userIds = event.value.map((user: Userx) => user.id ?? 0)
        const userNames = event.value.map((user: Userx) => user.fullNameWithUsername)
        console.log(userNames)


        setSelectedShift({...selectedShift, shiftWorkerIds: userIds, shiftWorkerNames: userNames})
        console.log(selectedShift)
    }

    /**
     * Handle input changes for the shiftPlan dialog.
     * @param event
     */
    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => {
        if (!selectedShift) return;

        const { name, value } = event.target;

        setSelectedShift({ ...selectedShift, [name]: value });
    }

    /**
     * Handle input changes for the absence dialog: times.
     * @param name
     * @param event
     */
    const handleTimeChange = (name: 'startTime' | 'endTime' , event: Nullable<Date>) => {
        if (!selectedShift) return;
        setSelectedShift({ ...selectedShift, [name]: event });
    }

    const formatDate = (value: Date) => {
        return value.toLocaleString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });
    }

    const startTimeBodyTemplate = (shift: Shift) => {
        return shift.startTime === null ? formatDate(new Date('1900-01-01')): formatDate(shift.startTime);
    }

    const endTimeBodyTemplate = (shift: Shift) => {
        return shift.endTime === null ? formatDate(new Date('1900-01-01')): formatDate(shift.endTime);
    }

    return (
        <div>
            <Button disabled={isNewShiftPlan} label="Add Shift" icon="pi pi-plus" onClick={openNewShiftDialog} />
            <DataTable value={shifts} paginator rows={5} emptyMessage="No shifts available.">
                <Column field="startTime" header="Start Time" sortable body={startTimeBodyTemplate} />
                <Column field="endTime" header="End Time" sortable body={endTimeBodyTemplate} />
                <Column field="employees" header="Employees" body={(rowData: ShiftDTO) => rowData.shiftWorkerNames.join(', ')} />
                <Column body={(rowData: ShiftDTO) => editButtonTemplate(rowData)} />
            </DataTable>
            <ShiftDialog visible={dialogVisible} shift={selectedShift} employees={employees} onHide={() => setDialogVisible(false)} onSubmit={handleSubmit} onInputChange={() => {}} onTimeChange={handleTimeChange} onEmployeesChange={handleEmployeeChange} />
        </div>
    );
};

export default ShiftTableComponent;