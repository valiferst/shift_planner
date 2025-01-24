package at.qe.skeleton.services;

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
    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, ShiftRepository shiftRepository, ShiftService shiftService, DepartmentService departmentService) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.departmentService = departmentService;
        this.shiftRepository = shiftRepository;
    }

    /**
     * Returns a collection of all shift plans.
     *
     * @return the collection of shift plans
     */
    // TODO might want to change authority back to admin
    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<ShiftPlan> getAllShiftPlans() {
        return shiftPlanRepository.findAll();
    }

    /**
     * Find all shifts of published shiftplans of user and add to new User-ShiftPlan
     *
     * @param user of whom we want the shifts
     * @return shiftPlan with all user shifts which are part of a published shiftplan
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public ShiftPlan getAllPublishedUserShifts(Userx user) {
        Collection<Shift> userShifts = shiftRepository.findByShiftWorker(user);
        Collection<ShiftPlan> shiftPlan = shiftPlanRepository.findByState_Published();
        List<Shift> publishedUserShifts = new ArrayList<>();
        for (Shift shift : userShifts) {
            for (ShiftPlan plan : shiftPlan) {
                if(shift.getShiftPlan().equals(plan)) {
                    publishedUserShifts.add(shift);
                }
            }
        }
        ShiftPlan plan = new ShiftPlan();
        plan.setShifts(publishedUserShifts);
        return plan;
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

    public boolean validateShiftPlan(ShiftPlan shiftPlan) {
        return true;
    }

    /**
     * publishes the ShiftPlan
     * change State of old PUBLISHED shift plan to CANCELLED
     * changes state of shift plan to be published to PUBLISHED
     *
     * @param shiftPlan the shift plan to be published
     * @return the published ShiftPlan
     */

    @PreAuthorize("hasAuthority ('MANAGER')")
    public ShiftPlan publishShiftPlan(ShiftPlan shiftPlan) {
        if (!validateShiftPlan(shiftPlan)){
            throw new IllegalArgumentException("Shift plan is not valid");
        }

        ShiftPlan oldShiftPlan = departmentService.getPublishedShiftPlan(shiftPlan.getDepartment().getId());

        if (oldShiftPlan != null) {
            oldShiftPlan.setState(CANCELLED);
            shiftPlanRepository.save(oldShiftPlan);
        }

        shiftPlan.setState(PUBLISHED);
        return shiftPlanRepository.save(shiftPlan);
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
