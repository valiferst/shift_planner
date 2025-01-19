package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlanState;

import java.time.LocalDateTime;

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
        ShiftPlanState state
) {}
