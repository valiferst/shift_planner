package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlanState;
import at.qe.skeleton.model.UserxRole;

import java.time.LocalDateTime;
import java.util.Set;

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
        Set<Long> assignedUserIds // Changed from Set<Userx> to Set<Long>
) {}
