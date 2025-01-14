package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;

import java.time.LocalDateTime;

public class StartDateUpdater implements AttributeUpdater<ShiftPlan, LocalDateTime> {
    @Override
    public void update(ShiftPlan shiftPlan, LocalDateTime startDate) {
        shiftPlan.setStartDate(startDate);
    }
}
