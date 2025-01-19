package at.qe.skeleton.services;

import at.qe.skeleton.model.Absence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import at.qe.skeleton.repositories.AbsenceRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Service for accessing and manipulating Absences.
 */
@Service
@Scope("application")
public class AbsenceService {

    private final AbsenceRepository absenceRepository;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    /**
     * Saves the absence.
     *
     * @param absence the absence to save
     * @return the updated absence
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Absence saveAbsence(Absence absence) {
        validateAbsence(absence);
        return absenceRepository.save(absence);
    }

    /**
     * Deletes the absence.
     *
     * @param absence the absence to delete
     */
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public void deleteAbsence(Absence absence) {
        absenceRepository.findById(absence.getId()).ifPresent(absenceRepository::delete);
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
    @PreAuthorize("hasAuthority('EMPLOYEE')")
    public Optional<Absence> loadAbsence(Long id) {
        return absenceRepository.findById(id);
    }

    /**
     * Validates the absence entity.
     *
     * @param absence the absence to validate
     */
    private void validateAbsence(Absence absence) {
        if (absence.getValidUntil() != null && absence.getValidFrom() != null &&
                absence.getValidUntil().isBefore(absence.getValidFrom())) {
            throw new IllegalArgumentException("The end date of the absence cannot be before the start date.");
        }

        if (absence.getAbsentUntil() != null && absence.getAbsentFrom() != null &&
                absence.getAbsentUntil().isBefore(absence.getAbsentFrom())) {
            throw new IllegalArgumentException("The end time of the absence cannot be before the start time.");
        }

        if (absence.isNew() && absenceRepository.findAll().stream()
                .anyMatch(existing -> existing.equals(absence))) {
            throw new IllegalArgumentException("A similar absence already exists.");
        }
    }
}
