package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;

public class NameUpdater implements AttributeUpdater<ShiftPlan, String> {
    @Override
    public void update(ShiftPlan shiftPlan, String name) {
        shiftPlan.setName(name);
    }
}
