package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.AbsenceDTO;
import at.qe.skeleton.model.Absence;
import at.qe.skeleton.services.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Mapping between Absence and AbsenceDTOs.
 *
 */
@Service
public class AbsenceMapper implements DTOMapper<Absence, AbsenceDTO>{

    private final AbsenceService absenceService;

    @Autowired
    public AbsenceMapper(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @Override
    public AbsenceDTO mapTo(Absence absence) {
        if (absence == null) {
            return null;
        }
        AbsenceDTO dto = new AbsenceDTO(
                absence.getId(),
                absence.getUser(),
                absence.getValidFrom(),
                absence.getValidUntil(),
                absence.getAbsentFrom(),
                absence.getAbsentUntil(),
                absence.getAbsentDay()
        );

        return dto;
    }

    @Override
    public Absence mapFrom(AbsenceDTO absenceDto) {
        if (null == absenceDto) {
            return null;
        }
        Absence absence;
        if (null != absenceDto.id()) {
            absence = AbsenceService.loadAbsence(absenceDto.id()).orElse(new Absence());
        } else {
            absence = new Absence();
        }
        absence.setFirstName(absenceDto.firstName());
        user.setUser(absenceDto.user());
        user.setValidFrom(absenceDto.validFrom());
        user.setValidUntil(absenceDto.validUntil());
        user.setAbsentFrom(absenceDto.absentFrom());
        user.setAbsentUntil(absenceDto.absentUntil());
        user.setAbsentDay(absenceDto.absentDay());

        return absence;
    }

}