package at.qe.skeleton.services;

import at.qe.skeleton.model.*;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private final DepartmentService departmentService;
    private final ShiftService shiftService;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, ShiftService shiftService, DepartmentService departmentService) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.departmentService = departmentService;
        this.shiftService = shiftService;
    }

    /**
     * Returns a collection of all shift plans.
     *
     * @return the collection of shift plans
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<ShiftPlan> getAllShiftPlans() {
        return shiftPlanRepository.findAll();
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

    /**
     * Validates the shift plan to ensure that there are no conflicts between shifts and absences for users.
     *
     * @param shiftPlan The ShiftPlan to validate, containing a list of shifts and assigned users.
     * @return A list of ValidationErrors encountered during the shift plan validation. If no errors are found, an empty list is returned.
     */

    public List<ValidationError> validateShiftPlan(ShiftPlan shiftPlan) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<Shift> shifts = shiftPlan.getShifts();
            for (Shift shift : shifts) {
                List<ValidationError> shiftErrors = shiftService.validateShift(shift);
                validationErrors.addAll(shiftErrors);
                }
        return validationErrors;
    }

    /**
     * publishes the ShiftPlan and sets previously published ShiftPlan to CANCELLED
     *
     * @param shiftPlan the shift plan to be published
     * @return the published ShiftPlan
     */

    @PreAuthorize("hasAuthority ('MANAGER')")
    public List<ValidationError> publishShiftPlan(ShiftPlan shiftPlan) {
        List<ValidationError> validationErrors = validateShiftPlan(shiftPlan);
        if (validationErrors.isEmpty()) {
            ShiftPlan oldShiftPlan = departmentService.getPublishedShiftPlan(shiftPlan.getDepartment().getId());
            if (oldShiftPlan != null) {
                oldShiftPlan.setState(CANCELLED);
                shiftPlanRepository.save(oldShiftPlan);
            }
            shiftPlan.setState(PUBLISHED);
        }
        return validationErrors;
    }

    /**
     * Get all shift plans for a department.
     * @param departmentId The ID of the department.
     * @return A collection of shift plans for the department.
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<ShiftPlan> getShiftPlansByDepartmentId(Long departmentId) {
        // findByDepartment_Id was suggested by Intellij not sure if this is correct
        return shiftPlanRepository.findByDepartment_Id(departmentId);
    }

    /**
     * Deletes the shift plan.
     *
     * @param shiftPlan the shift plan to delete
     */
    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteShiftPlan(ShiftPlan shiftPlan) {
        Optional<ShiftPlan> shiftPlanOpt = shiftPlanRepository.findById(shiftPlan.getId());
        shiftPlanOpt.ifPresent(shiftPlanRepository::delete);
    }

}
