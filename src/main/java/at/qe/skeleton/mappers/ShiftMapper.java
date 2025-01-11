package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftDTO;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mapping between Shift and ShiftDTOs.
 *
 */
@Service
public class ShiftMapper implements DTOMapper<Shift, ShiftDTO>{

    private final ShiftService shiftService;

    @Autowired
    public ShiftMapper(ShiftService shiftService){
        this.shiftService = shiftService;
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
                shift.getShiftPlan(),
                shift.getShiftWorkers()
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
        shift.setShiftPlan(shiftDto.toShiftPlan());
        shift.setShiftWorkers(shiftDto.shiftWorkers());

        return shift;
    }
}

