package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.ShiftPlan;

import java.util.List;

public class ShiftsUpdater implements AttributeUpdater<ShiftPlan, List<Shift>> {
    @Override
    public void update(ShiftPlan shiftPlan, List<Shift> shifts) {
        shiftPlan.setShifts(shifts);
    }
}
