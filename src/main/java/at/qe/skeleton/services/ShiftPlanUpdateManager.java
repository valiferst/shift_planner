package at.qe.skeleton.services;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.services.AttributeUpdaters.*;

import java.util.HashMap;
import java.util.Map;

public class ShiftPlanUpdateManager {

    private Map<String, AttributeUpdater<ShiftPlan, ?>> updaters;

    public ShiftPlanUpdateManager() {
        updaters = new HashMap<>();
        // Register all updaters
        updaters.put("department", new DepartmentUpdater());
        updaters.put("shifts", new ShiftsUpdater());
        updaters.put("state", new StateUpdater());
        updaters.put("date", new DateUpdater());
        updaters.put("createUser", new CreateUserUpdater());
        updaters.put("createDate", new CreateDateUpdater());
        updaters.put("updateUser", new UpdateUserUpdater());
        updaters.put("updateDate", new UpdateDateUpdater());
        updaters.put("name", new NameUpdater());
        updaters.put("startDate", new StartDateUpdater());
        updaters.put("endDate", new EndDateUpdater());
        updaters.put("assignedUsers", new AssignedUsersUpdater());
    }

    // Update method to apply any update based on the attribute name
    public <V> void updateAttribute(ShiftPlan shiftPlan, String attributeName, V value) {
        AttributeUpdater<ShiftPlan, V> updater = (AttributeUpdater<ShiftPlan, V>) updaters.get(attributeName);
        if (updater != null) {
            updater.update(shiftPlan, value);
        } else {
            throw new IllegalArgumentException("No updater found for attribute: " + attributeName);
        }
    }
}
