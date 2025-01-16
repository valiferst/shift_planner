package at.qe.skeleton.services;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.repositories.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for accessing and manipulating shift plans.
 */
@Component
@Scope("application")
public class ShiftPlanService {

    private final ShiftPlanRepository shiftPlanRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, ShiftRepository shiftRepository, ShiftService shiftService) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.shiftRepository = shiftRepository;
        this.shiftService = shiftService;
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

    //TODO create publish method
    // State will be set to PUBLISHED, previously published plan will be set to CANCELLED (concerning only the department)
    // method calls to department service

    // TODO create methods that update individual parts of a shiftplan

    /**
     * Deletes the shift plan and all shifts assigned to it.
     *
     * @param shiftPlan the shift plan to delete
     */
    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteShiftPlan(ShiftPlan shiftPlan) {
        Optional<ShiftPlan> shiftPlanOptional = shiftPlanRepository.findById(shiftPlan.getId());
        if(shiftPlanOptional.isPresent()) {
            Collection<Shift> allShifts = shiftService.getAllShiftPlanShifts(shiftPlan);
            for(Shift shift : allShifts) {
                shiftService.deleteShift(shift);
            }
            // Delete the shiftPlan after all shifts in it are deleted
            shiftPlanRepository.delete(shiftPlanOptional.get());
        }
    }

}
