package at.qe.skeleton.services.AttributeUpdaters;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;

public class DepartmentUpdater implements AttributeUpdater<ShiftPlan, Department> {
    @Override
    public void update(ShiftPlan shiftPlan, Department department) {
        shiftPlan.setDepartment(department);
    }
}
