package at.qe.skeleton.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ShiftDTO(
        Long id,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long shiftPlanId,
        List<Long> shiftWorkerIds,
        List<String> shiftWorkerNames
) {
}
