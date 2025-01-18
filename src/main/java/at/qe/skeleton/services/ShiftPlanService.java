package at.qe.skeleton.services;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for accessing and manipulating shift plans.
 */
@Component
@Scope("application")
public class ShiftPlanService {

    private final ShiftPlanRepository shiftPlanRepository;
    private ShiftPlanUpdateManager updateManager;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.updateManager = new ShiftPlanUpdateManager();
    }

    /**
     * Returns a collection of all shift plans.
     *
     * @return the collection of shift plans
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<ShiftPlan> getAllShiftPlans() {
        return shiftPlanRepository.findAll();
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public Collection<ShiftPlan> getAllShiftPlansForDepartment(Department department) {
        return null; // TODO implement getting list of shift plans per department
    }

    /**
     * Loads a single shift plan identified by its id.
     *
     * @param id the id to search for
     * @return the shift plan with the id
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Optional<ShiftPlan> loadShiftPlan(Long id) {
        return shiftPlanRepository.findById(id);
    }

    /**
     * Saves the shift plan.
     *
     * @param shiftPlan the shift plan to save
     * @return the updated shift plan
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public ShiftPlan saveShiftPlan(ShiftPlan shiftPlan) {
        return shiftPlanRepository.save(shiftPlan);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    public void updateShiftPlan(ShiftPlan shiftPlan, String attributeName, Object value) {
        updateManager.updateAttribute(shiftPlan, attributeName, value);
    }
    // TODO implement validate shiftplan (is this the right spot)?

    //TODO create publish method
    // State will be set to PUBLISHED, previously published plan will be set to CANCELLED (concerning only the department)
    // method calls to department service

    /**
     * Deletes the shift plan.
     *
     * @param shiftPlan the shift plan to delete
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteShiftPlan(ShiftPlan shiftPlan) {
        Optional<ShiftPlan> shiftPlanOpt = shiftPlanRepository.findById(shiftPlan.getId());
        shiftPlanOpt.ifPresent(plan -> shiftPlanRepository.delete(plan));
    }
}

