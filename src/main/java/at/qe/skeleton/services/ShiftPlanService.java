package at.qe.skeleton.services;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.repositories.UserxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserxRepository userRepository;

    @Autowired
    public ShiftPlanService(ShiftPlanRepository shiftPlanRepository, UserxRepository userRepository) {
        this.shiftPlanRepository = shiftPlanRepository;
        this.userRepository = userRepository;
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

    /**
     * Loads a single shift plan identified by its id.
     *
     * @param id the id to search for
     * @return the shift plan with the id
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<ShiftPlan> loadShiftPlan(Long id) {
        return shiftPlanRepository.findById(id);
    }

    /**
     * Saves the shift plan. This method will also set createDate for new entities
     * or updateDate for updated entities. The user requesting this operation will
     * also be stored as createUser or updateUser respectively.
     *
     * @param shiftPlan the shift plan to save
     * @return the updated shift plan
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public ShiftPlan saveShiftPlan(ShiftPlan shiftPlan) {
        if (shiftPlan.isNew()) {
            shiftPlan.setCreateUser(getAuthenticatedUser());
        } else {
            shiftPlan.setUpdateUser(getAuthenticatedUser());
        }
        return shiftPlanRepository.save(shiftPlan);
    }

    /**
     * Deletes the shift plan.
     *
     * @param shiftPlan the shift plan to delete
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteShiftPlan(ShiftPlan shiftPlan) {
        // :TODO: write some audit log stating who and when this shift plan was permanently deleted.
        Optional<ShiftPlan> shiftPlanOpt = shiftPlanRepository.findById(shiftPlan.getId());
        shiftPlanOpt.ifPresent(plan -> shiftPlanRepository.delete(plan));
    }

    /**
     * Returns the currently authenticated user.
     *
     * @return the authenticated user or null
     */
    public Userx getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName()).orElse(null);
    }
}
