package at.qe.skeleton.dtos;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Reduced data transfer object for the ShiftPlan Entity in the create endpoint.
 */
public record ShiftPlanCreateDTO(
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Set<Long> assignedUserIds
) {}
