package at.qe.skeleton.services;

import at.qe.skeleton.exceptions.ShiftDuplicateException;
import at.qe.skeleton.exceptions.ShiftOverlapException;
import at.qe.skeleton.model.Absence;
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
import java.util.Set;

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

    /**
     * To save the shift, except it already exists in this shiftPlan.
     *
     * @param shift the shift to save
     * @return the new saved shift
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public Shift saveShift(Shift shift) {
        if (shift.isNew()) {
            List<Shift> shifts = shiftRepository.findByShiftPlan(shift.getShiftPlan());
            for (Shift compShift : shifts){
                if(compShift.getStartTime() == shift.getStartTime() && compShift.getEndTime() == shift.getEndTime()){
                    throw new ShiftDuplicateException("Shift starting at " + shift.getStartTime() + " already exists");
                }
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
        newShift.setShiftWorkers(shift.getShiftWorkers());
        newShift.setEndTime(newTime.plus(shift.getShiftDuration()));
        return newShift;
    }

    /**
     * For a given shift, throw exception if the user already has another shift at that time
     *
     * @param shift the shift to check if it overlaps with other shift
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

    /**
     * For a given shift, throw exception if user has absence at the same time
     *
     * @param shift the shift to check if overlaps with user absence
     * @param user whose shift we check if it overlaps 
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    public void overlapUserAbsences(Shift shift, Userx user){
        Set<Absence> absences =  user.getAbsences();
        for (Absence absence : absences){
            if(shift.getStartTime().isAfter(absence.getValidFrom()) && shift.getStartTime().isBefore(absence.getValidUntil())){
                if(shift.getStartTime().getDayOfWeek() == absence.getAbsentDay() && shift.getStartTime().toLocalTime().isBefore(absence.getAbsentFrom())
                    && shift.getEndTime().toLocalTime().isAfter(absence.getAbsentFrom())){
                    throw new ShiftOverlapException("The shift "+ shift.getId() + "overlaps with absence of user " + user.getUsername());
                }
            }
        }
    }
}
