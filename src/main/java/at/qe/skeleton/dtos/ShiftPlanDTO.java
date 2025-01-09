package at.qe.skeleton.dtos;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object for the ShiftPlan Entity.
 */
public record ShiftPlanDTO (
        Long id,
        Long createdBy,
        LocalDateTime createDate,
        Long updatedBy,
        LocalDateTime updateDate,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Set<Long> assignedUserIds // Changed from Set<Userx> to Set<Long>
) {}
