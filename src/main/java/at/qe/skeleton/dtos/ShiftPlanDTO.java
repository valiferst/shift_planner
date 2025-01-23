package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlanState;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data transfer object for the ShiftPlan Entity.
 */
public record ShiftPlanDTO (
        Long id,
        LocalDateTime createDate,
        LocalDateTime updateDate,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ShiftPlanState state,
        String departmentName,
        Long departmentId,
        List<ShiftDTO> shifts,
        List<String> errorMessages
) {}
