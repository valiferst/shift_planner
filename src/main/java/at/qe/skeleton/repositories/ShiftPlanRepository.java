package at.qe.skeleton.repositories;

import at.qe.skeleton.model.ShiftPlan;

import java.util.Collection;

/**
 * Repository for managing {@link ShiftPlan} entities.
 */

public interface ShiftPlanRepository extends AbstractRepository<ShiftPlan, Long> {
    Collection<ShiftPlan> findByDepartment_Id(Long departmentId);

    Collection<ShiftPlan> findByState_Published ();
}
