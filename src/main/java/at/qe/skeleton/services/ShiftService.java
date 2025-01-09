package at.qe.skeleton.services;

import at.qe.skeleton.exceptions.UsernameDuplicateException;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("application")
public class ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<Shift> getAllUserShifts(Userx user) {
        return shiftRepository.findByShiftWorker(user);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<Shift> getAllShiftPlanShifts(ShiftPlan shiftPlan) {
        return shiftRepository.findByShiftPlan(shiftPlan); }



}
