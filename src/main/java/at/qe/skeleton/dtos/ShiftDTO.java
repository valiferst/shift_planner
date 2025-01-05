package at.qe.skeleton.dtos;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;

import java.time.LocalDateTime;
import java.util.Set;

public record ShiftDTO(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long toShiftPlan,
        Set<Long> shiftWorkers
) {
}
