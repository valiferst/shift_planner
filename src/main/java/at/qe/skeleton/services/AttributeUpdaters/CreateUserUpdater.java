package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;

public class CreateUserUpdater implements AttributeUpdater<ShiftPlan, Userx> {
    @Override
    public void update(ShiftPlan shiftPlan, Userx createUser) {
        shiftPlan.setCreateUser(createUser);
    }
}
