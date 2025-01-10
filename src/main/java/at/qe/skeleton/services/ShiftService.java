package at.qe.skeleton.services;

import at.qe.skeleton.exceptions.ShiftDuplicateException;
import at.qe.skeleton.exceptions.ShiftOverlapException;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Scope("application")
public class ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    /**
     * Returns a collection of all shifts of one user.
     *
     * @param user the user whose shifts we want
     * @return the shift collection
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<Shift> getAllUserShifts(Userx user) {
        return shiftRepository.findByShiftWorker(user);
    }

    /**
     * Returns a collection of all shifts of one shiftPlan.
     *
     * @param shiftPlan the shiftPlan of which we want the shifts
     * @return the shift collection
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Collection<Shift> getAllShiftPlanShifts(ShiftPlan shiftPlan) {
        return shiftRepository.findByShiftPlan(shiftPlan); }

    /**
     * Loads a single shift identified by its id.
     *
     * @param id the id to search for
     * @return the shift with the id
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Optional<Shift> loadShift(Long id) {
        return shiftRepository.findById(id);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    public Shift saveShift(Shift shift) {
        if (shift.isNew()) {
            if (shiftRepository.existsByStartTime(shift.getStartTime())) {
                throw new ShiftDuplicateException("Shift starting at " + shift.getStartTime() + " not available");
            }
        }
        return shiftRepository.save(shift);
    }

    /**
     * Deletes the shift.
     *
     * @param shift the shift to delete
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public void deleteShift(Shift shift) {
        // :TODO: write some audit log stating who and when this user was permanently deleted.
        Optional<Shift> shiftOpt = shiftRepository.findById(shift.getId());
        shiftOpt.ifPresent(shiftx -> shiftRepository.delete(shiftx));
    }

    /**
     *To get the copy of given shift at newTime
     *
     * @param shift the shift to duplicate
     * @param newTime updated time for new usage
     * @return newShift at newTime
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Shift copyShift(Shift shift, LocalDateTime newTime){
        if(shift.isNew()){
            throw new IllegalArgumentException("Shift " + shift.getId() + " does not exist");
        } else if (newTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Time " + newTime + "is in the past");
        }
        Shift newShift = new Shift();
        newShift.setStartTime(newTime);
        newShift.setShiftPlan(shift.getShiftPlan());
        // TODO: shiftworker and calculate endTime

        return newShift;
    }

    /**
     * For a given shift, throw exception if the user already has another shift at that time
     *
     * @param shift the shift to check if it overlaps with others
     * @param user whose shift we check if it overlaps
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public void overlapUserShift(Shift shift, Userx user){
        List<Shift> shifts = shiftRepository.findByShiftWorker(user);
        for (Shift otherShift : shifts){
            if(otherShift.getStartTime().isAfter(shift.getStartTime()) && otherShift.getStartTime().isBefore(shift.getEndTime())){
                throw new ShiftOverlapException("The shift " + shift.getId() + " for user " + user.getUsername() + "is overlapping with another shift");
            }
        }
    }
}
