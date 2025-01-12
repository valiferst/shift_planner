package at.qe.skeleton.services;
import at.qe.skeleton.model.Absence;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import at.qe.skeleton.repositories.AbsenceRepository;
import java.util.Optional;

/**
 * Service for accessing and manipulating Absences.
 *
 */
@Component
@Scope("application")
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository)
     {
        this.absenceRepository = absenceRepository;
    }

    /**
     * Saves the absence
     *
     * @param absence the absence to save
     * @return the updated absence
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Absence saveAbsence(Absence absence) {
        if(absence.isNew()){
            // TODO implement a check for a duplicate absence maybe and throw a custom exception
            // TODO implement checks that the end of the absence is not before the beginning
            // TODO think about other consistency checks
            throw new IllegalArgumentException("Absence already exists");}
        return absenceRepository.save(absence);
    }

    /**
     * Deletes the absence.
     *
     * @param absence the absence to delete
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public void deleteAbsence(Absence absence) {
        Optional<Absence> absenceOpt = absenceRepository.findById(absence.getId());
        absenceOpt.ifPresent(absenceRepository::delete);
    }

    /**
     * Returns a collection of all absences.
     *
     * @return the absence collection
     */
    @PreAuthorize("hasAuthority('ADMIN')") //TODO discuss this permission level
    public Collection<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }


    /**
     * Loads a single absence identified by its id.
     *
     * @param id the id to search for
     * @return the absence with the id
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Optional<Absence> loadAbsence(Long id) {
        return absenceRepository.findById(id);
    }
}