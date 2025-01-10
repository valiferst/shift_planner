package at.qe.skeleton.services;

import at.qe.skeleton.exceptions.UsernameDuplicateException;
import at.qe.skeleton.model.Absence;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import at.qe.skeleton.repositories.AbsenceRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public Absence saveAbsence(Absence absence) {
        if(absence.isNew()){
            absence.setCreateUser(getAuthenticatedUser())
        } else {
            absence.setUpdateUser(getAuthenticatedUser())
        }
        return absenceRepository.save(absence)

    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    public void deleteAbsence(Absence absence) {
        // :TODO: write some audit log stating who and when this user was permanently deleted.
        Optional<Absence> absenceOpt = absenceRepository.findById(absence.getId());
        absenceOpt.ifPresent(absencex -> absenceRepository.delete(absencex));
    }

    /**
     * Returns a collection of all absences.
     *
     * @return the absence collection
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }


    /**
     * Loads a single absence identified by its id.
     *
     * @param id the id to search for
     * @return the absence with the id
     */
    public Optional<Absence> loadAbsence(Long id) {
        return absenceRepository.findById(id);
    }

}