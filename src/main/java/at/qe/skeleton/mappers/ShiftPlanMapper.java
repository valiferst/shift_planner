package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.services.ShiftPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Mapping between ShiftPlan and ShiftPlanDTOs.
 */
@Service
public class ShiftPlanMapper implements DTOMapper<ShiftPlan, ShiftPlanDTO> {

    private final ShiftPlanService shiftPlanService;
    private final ShiftMapper shiftMapper;
    private final ShiftPlanRepository shiftPlanRepository;

    @Autowired
    public ShiftPlanMapper(ShiftPlanService shiftPlanService, ShiftMapper shiftMapper, ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanService = shiftPlanService;
        this.shiftMapper = shiftMapper;
        this.shiftPlanRepository = shiftPlanRepository;
    }

    @Override
    public ShiftPlanDTO mapTo(ShiftPlan shiftPlan) {
        if (shiftPlan == null) {
            return null;
        }
        return new ShiftPlanDTO(
                shiftPlan.getId(),
                shiftPlan.getCreateDate(),
                shiftPlan.getUpdateDate(),
                shiftPlan.getName(),
                shiftPlan.getStartDate(),
                shiftPlan.getEndDate(),
                shiftPlan.getState(),
                shiftPlan.getDepartment().getName(),
                shiftPlan.getDepartment().getId(),
                shiftPlan.getShifts().stream().map(shiftMapper::mapTo).toList()
        );
    }

    // TODO handle the newly introduced fields in the DTO (departmentName and departmentId, shiftDTO-list), when receiving it from the frontend (new shiftplan-creation)
    @Override
    public ShiftPlan mapFrom(ShiftPlanDTO shiftPlanDto) {
        if (null == shiftPlanDto) {
            return null;
        }
        ShiftPlan shiftPlan;
        if (null != shiftPlanDto.id()) {
            shiftPlan = shiftPlanService.loadShiftPlan(shiftPlanDto.id()).orElse(new ShiftPlan());
        } else {
            shiftPlan = new ShiftPlan();
        }
        shiftPlan.setCreateDate(shiftPlanDto.createDate());
        shiftPlan.setUpdateDate(shiftPlanDto.updateDate());
        shiftPlan.setName(shiftPlanDto.name());
        shiftPlan.setStartDate(shiftPlanDto.startDate());
        shiftPlan.setEndDate(shiftPlanDto.endDate());
        shiftPlan.setState(shiftPlanDto.state());


        return shiftPlan;
    }
}
