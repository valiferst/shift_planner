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
     * Saves the user. This method will also set {@link Userx#createDate} for new
     * entities or {@link Userx#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link Userx#createDate}
     * or {@link Userx#updateUser} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    public Userx saveAbsence(Absence absence) {

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

}