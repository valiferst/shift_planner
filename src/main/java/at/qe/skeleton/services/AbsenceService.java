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


}