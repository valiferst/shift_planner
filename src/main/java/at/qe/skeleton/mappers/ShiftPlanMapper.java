package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.model.ShiftPlan;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Mapping between ShiftPlan and ShiftPlanDTOs.
 */
@Service
public class ShiftPlanMapper implements DTOMapper<ShiftPlan, ShiftPlanDTO> {

    @Override
    public ShiftPlanDTO mapTo(ShiftPlan shiftPlan) {
        if (shiftPlan == null) {
            return null;
        }
        return new ShiftPlanDTO(
                shiftPlan.getId(),
                shiftPlan.getCreateUser() != null ? shiftPlan.getCreateUser().getId() : null,
                shiftPlan.getCreateDate(),
                shiftPlan.getUpdateUser() != null ? shiftPlan.getUpdateUser().getId() : null,
                shiftPlan.getUpdateDate(),
                shiftPlan.getName(),
                shiftPlan.getStartDate(),
                shiftPlan.getEndDate(),
                shiftPlan.getAssignedUsers().stream().map(Userx::getId).collect(Collectors.toSet())
        );
    }

    @Override
    public ShiftPlan mapFrom(ShiftPlanDTO dto) {
        throw new UnsupportedOperationException("Mapping from ShiftPlanDTO to ShiftPlan is not supported.");
    }
}
