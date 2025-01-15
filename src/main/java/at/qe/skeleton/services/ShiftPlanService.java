package at.qe.skeleton.services;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

import static at.qe.skeleton.model.ShiftPlanState.PUBLISHED;
import static at.qe.skeleton.model.ShiftPlanState.CANCELLED;

/**
 * Service for accessing and manipulating shift plans.
 */
@Component
@Scope("application")
public class ShiftPlanService {

    private final ShiftPlanRepository shiftPlanRepository;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
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

    // TODO implement validate shiftplan (is this the right spot)?

    public boolean validateShiftPlan(ShiftPlan shiftPlan) {
        return true;
    }

    //TODO create publish method
    // State will be set to PUBLISHED, previously published plan will be set to CANCELLED (concerning only the department)
    // method calls to department service

    /**
     * Validates the shift plan
     * change State of old PUBLISHED shift plan to CANCELLED
     * changes state of shift plan to be published to PUBLISHED
     *
     * @param shiftPlan the shift plan to be published
     */

    @PreAuthorize("hasAuthority ('MANAGER')")
    public void publishShiftPlan(ShiftPlan shiftPlan) {
        if (!validateShiftPlan(shiftPlan)){
            throw new IllegalArgumentException("Shift plan is not valid");
        }

        ShiftPlan oldShiftPlan = DepartmentService.getPublishedShiftPlan(shiftPlan.getDepartment().getId());

        if (oldShiftPlan != null) {
            oldShiftPlan.setState(CANCELLED);
            shiftPlanRepository.save(oldShiftPlan);
        } else {
            throw new IllegalArgumentException("No published shift plan found for the department.");
        }

        shiftPlan.setState(PUBLISHED);
        shiftPlanRepository.save(shiftPlan);
    }

    // TODO create methods that update individual parts of a shiftplan

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
