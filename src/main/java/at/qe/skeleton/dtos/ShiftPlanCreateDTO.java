package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlanState;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Reduced data transfer object for the ShiftPlan Entity in the create endpoint.
 */
/**
 * Data transfer object for the ShiftPlan Entity.
 */
public record ShiftPlanCreateDTO (
        Long id,
        LocalDateTime createDate,
        LocalDateTime updateDate,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ShiftPlanState state,
        Set<Long> assignedUserIds // Changed from Set<Userx> to Set<Long>
) {}
