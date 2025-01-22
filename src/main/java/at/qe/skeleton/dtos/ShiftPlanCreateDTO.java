package at.qe.skeleton.dtos;

import java.time.LocalDateTime;
import java.util.List;

// DTO for the purpose of ShiftPlan creation
public record ShiftPlanCreateDTO(
    // List of all possible attributes and why they shouldn't be in the CreateDTO

    // Long id;                         -- database will set this automatically
    // ShiftPlanState shiftPlanState;   -- will always be DRAFT upon creation
    // Date createDate;                 -- will be set automatically
    // Date updateDate;                 -- will be set automatically
    // Date date;                       -- obsolete/duplicate field

    String name,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long departmentId,
    List<ShiftDTO> shifts            // -- at least one shift
) {}
