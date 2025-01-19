/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import React from 'react';
import { Dialog } from 'primereact/dialog';
import { Button } from "primereact/button";
import { ShiftPlanDTO } from "../DTO/ShiftPlan";
import { InputMaskChangeEvent } from "primereact/inputmask";

interface ShiftPlanPublishDialogProps {
    visible: boolean,
    shiftPlan: ShiftPlanDTO | null,
    onHide: () => void,
    onPublish: () => void,
    onInputChange: (event: React.ChangeEvent<HTMLInputElement> | InputMaskChangeEvent) => void,
}

/**
 * Dialog for creating or editing an shiftPlan.
 * @param visible whether the dialog is visible
 * @param shiftPlan the shiftPlan to be published
 * @param onHide callback when the dialog is hidden
 * @param onPublish callback when the shiftPlan is published
 * @param onInputChange callback when the input changes
 */
const ShiftPlanDialog: React.FC<ShiftPlanPublishDialogProps> = ({
    visible,
    shiftPlan,
    onHide,
    onPublish,
    onInputChange,
}) => {

    /**
     * Renders the footer of the dialog.
     */
    const renderFooter = () => (
        <div>
            <Button label="Cancel" icon="pi pi-times" onClick={onHide} className="p-button-text" />
            <Button label="Publish" icon="pi pi-check" onClick={onPublish}
                autoFocus />
        </div>
    );

    return (
        <Dialog
            header="Publish ShiftPlan"
            visible={visible}
            style={{ width: '50vw' }}
            onHide={onHide}
            footer={renderFooter}
        >
            {/*{shiftPlan && (*/}
            {/*    <ShiftPlanForm*/}
            {/*        shiftPlan={shiftPlan}*/}
            {/*        isNewShiftPlan={isNewShiftPlan}*/}
            {/*        onInputChange={onInputChange}*/}
            {/*    />*/}
            {/*)}*/}
        </Dialog>
    );
};

export default ShiftPlanDialog;
