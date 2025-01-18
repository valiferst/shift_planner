package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.ShiftPlanService;
import at.qe.skeleton.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapping between ShiftPlan and ShiftPlanDTOs.
 */
@Service
public class ShiftPlanMapper implements DTOMapper<ShiftPlan, ShiftPlanDTO> {

    private final ShiftPlanService shiftPlanService;
    private final UserxService userxService;

    @Autowired
    public ShiftPlanMapper(ShiftPlanService shiftPlanService, UserxService userxService) {
        this.shiftPlanService = shiftPlanService;
        this.userxService = userxService;
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
                shiftPlan.getAssignedUsers().stream()
                        .map(Userx::getId)  // Map Userx to Long (ID)
                        .collect(Collectors.toSet())
        );
    }

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
        shiftPlan.setAssignedUsers(
                shiftPlanDto.assignedUserIds().stream()
                        .map(userxService::loadUser)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet())
        );


        return shiftPlan;
    }
}
