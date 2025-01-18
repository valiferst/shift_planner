package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;

import java.util.Set;

public class AssignedUsersUpdater implements AttributeUpdater<ShiftPlan, Set<Userx>> {
    @Override
    public void update(ShiftPlan shiftPlan, Set<Userx> assignedUsers) {
        shiftPlan.setAssignedUsers(assignedUsers);
    }
}
