package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.ShiftPlan;

import java.time.LocalDateTime;

public class EndDateUpdater implements AttributeUpdater<ShiftPlan, LocalDateTime> {
    @Override
    public void update(ShiftPlan shiftPlan, LocalDateTime endDate) {
        shiftPlan.setEndDate(endDate);
    }
}
