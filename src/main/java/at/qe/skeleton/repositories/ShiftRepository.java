package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Shift} entities.
 *
 */

public interface ShiftRepository extends AbstractRepository<Shift, Long>{

    Optional<Shift> findFirstByStartTime(LocalDateTime startTime);

    @Query("SELECT u FROM Shift u WHERE :time BETWEEN u.startTime AND u.endTime")
    Optional<Shift> findByStartTimeContaining(@Param("time") LocalDateTime startTime);

    @Query("SELECT u FROM Shift u WHERE :worker MEMBER OF u.shiftWorkers")
    List<Shift> findByShiftWorker(@Param("worker") Userx shiftWorker);

    List<Shift> findByShiftPlan(ShiftPlan shiftPlan);

    boolean existsByStartTime(LocalDateTime startTime);
}
