package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.ShiftPlanCreateDTO;
import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.ShiftPlanState;
import at.qe.skeleton.services.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class ShiftPlanCreateMapper implements DTOMapper<ShiftPlan, ShiftPlanCreateDTO> {

    private final DepartmentService departmentService;
    private final ShiftMapper shiftMapper;

    public ShiftPlanCreateMapper(DepartmentService departmentService, ShiftMapper shiftMapper) {
        this.departmentService = departmentService;
        this.shiftMapper = shiftMapper;
    }

    @Override
    public ShiftPlanCreateDTO mapTo(ShiftPlan shiftPlan) {
        if (shiftPlan == null) {
            return null;
        }
        return new ShiftPlanCreateDTO(
                shiftPlan.getName(),
                shiftPlan.getStartDate(),
                shiftPlan.getEndDate(),
                shiftPlan.getDepartment().getId()
        );

    }

    @Override
    public ShiftPlan mapFrom(ShiftPlanCreateDTO dto) {
        ShiftPlan shiftPlan = new ShiftPlan();

        // Long id;                         -- database will set this automatically
        // ShiftPlanState shiftPlanState;   -- will be set in ShiftPlanService::save
        // Date createDate;                 -- will be set in ShiftPlanService::save
        // Date updateDate;                 -- will be set in ShiftPlanService::save
        // Date date;                       -- obsolete/duplicate field


        shiftPlan.setState(ShiftPlanState.DRAFT);
        shiftPlan.setName(dto.name());
        shiftPlan.setStartDate(dto.startDate());
        shiftPlan.setEndDate(dto.endDate());
        shiftPlan.setDepartment(departmentService.loadDepartment(dto.departmentId()).orElseThrow(() -> new IllegalArgumentException("Department not found")));
        // shiftPlan.setShifts(dto.shifts().stream().map(shiftMapper::mapFrom).toList());
        return shiftPlan;
    }
}
