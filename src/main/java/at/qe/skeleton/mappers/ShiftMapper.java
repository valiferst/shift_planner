package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftDTO;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.ShiftPlanService;
import at.qe.skeleton.services.ShiftService;
import at.qe.skeleton.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapping between Shift and ShiftDTOs.
 *
 */
@Service
public class ShiftMapper implements DTOMapper<Shift, ShiftDTO>{

    private final ShiftService shiftService;
    private final UserxService userxService;
    private final ShiftPlanService shiftPlanService;

    @Autowired
    public ShiftMapper(ShiftService shiftService, UserxService userxService, ShiftPlanService shiftPlanService){
        this.shiftService = shiftService;
        this.userxService = userxService;
        this.shiftPlanService = shiftPlanService;
    }

    @Override
    public ShiftDTO mapTo(Shift shift) {
        if (shift == null) {
            return null;
        }
        return new ShiftDTO(
                shift.getId(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.getShiftPlan().getId(),
                shift.getShiftWorkers().stream().map(Userx::getId).toList(),
                shift.getShiftWorkers().stream().map(Userx::getFullNameWithUsername).toList()
        );
    }

    @Override
    public Shift mapFrom(ShiftDTO shiftDto) {
        if (null == shiftDto) {
            return null;
        }
        Shift shift;
        if (null != shiftDto.id()) {
            shift = shiftService.loadShift(shiftDto.id()).orElse(new Shift());
        } else {
            shift = new Shift();
        }
        shift.setStartTime(shiftDto.startTime());
        shift.setEndTime(shiftDto.endTime());
        shift.setShiftPlan(shiftPlanService.loadShiftPlan(shiftDto.shiftPlanId()).orElse(null));
        shift.setShiftWorkers(userxService.getUsersById(shiftDto.shiftWorkerIds()).stream().collect(Collectors.toSet()));

        return shift;
    }
}

