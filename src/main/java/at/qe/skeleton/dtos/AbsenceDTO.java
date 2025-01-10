package at.qe.skeleton.dtos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import at.qe.skeleton.model.Userx;

public record AbsenceDTO(
        Long id,
        Userx user,
        LocalDateTime validFrom,
        LocalDateTime validUntil,
        LocalTime absentFrom,
        LocalTime absentUntil,
        DayOfWeek absentDay,
) {}