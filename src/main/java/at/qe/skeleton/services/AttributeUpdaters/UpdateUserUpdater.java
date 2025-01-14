package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;

public class UpdateUserUpdater implements AttributeUpdater<ShiftPlan, Userx> {
    @Override
    public void update(ShiftPlan shiftPlan, Userx updateUser) {
        shiftPlan.setUpdateUser(updateUser);
    }
}
