package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftPlanCreateDTO;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.UserxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapping between ShiftPlanCreateDTO and ShiftPlan.
 */
@Service
public class ShiftPlanCreateMapper implements DTOMapper<ShiftPlan, ShiftPlanCreateDTO> {

    private final UserxService userxService;

    @Autowired
    public ShiftPlanCreateMapper(UserxService userxService) {
        this.userxService = userxService;
    }

    @Override
    public ShiftPlanCreateDTO mapTo(ShiftPlan entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ShiftPlan mapFrom(ShiftPlanCreateDTO dto) {
        ShiftPlan shiftPlan = new ShiftPlan();
        shiftPlan.setName(dto.name());
        shiftPlan.setStartDate(dto.startDate());
        shiftPlan.setEndDate(dto.endDate());

        Set<Userx> assignedUsers = new HashSet<>();
        dto.assignedUserIds().forEach(userId ->
                userxService.loadUser(userId).ifPresent(assignedUsers::add)
        );
        shiftPlan.setAssignedUsers(assignedUsers);

        return shiftPlan;
    }
}
