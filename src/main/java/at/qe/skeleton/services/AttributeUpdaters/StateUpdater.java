package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.ShiftPlanState;

public class StateUpdater implements AttributeUpdater<ShiftPlan, ShiftPlanState> {
    @Override
    public void update(ShiftPlan shiftPlan, ShiftPlanState state) {
        shiftPlan.setState(state);
    }
}
