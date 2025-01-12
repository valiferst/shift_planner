package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlan;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Data transfer object for the Department Entity.
 */
public record DepartmentDTO (
        Long id,
        String name,
        LocalDateTime openingTime,
        LocalDateTime closingTime,
        Long managerId,
        List<ShiftPlan> shiftPlans
) {}