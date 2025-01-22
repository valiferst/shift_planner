package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.DepartmentDTO;
import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.DepartmentService;
import at.qe.skeleton.services.ShiftPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Mapping between Department and DepartmentDTOs.
 *
 */
@Service
public class DepartmentMapper implements DTOMapper<Department, DepartmentDTO> {

    private final DepartmentService departmentService;
    private final ShiftPlanService shiftPlanService;

    @Autowired
    public DepartmentMapper(DepartmentService departmentService, ShiftPlanService shiftPlanService) {
        this.departmentService = departmentService;
        this.shiftPlanService = shiftPlanService;
    }

    @Override
    public DepartmentDTO mapTo(Department department) {
        if (department == null) {
            return null;
        }
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getOpeningTime(),
                department.getClosingTime(),
                department.getManager() != null ? department.getManager().getId() : null,
                departmentService.getFullManagerName(department),
                department.getShiftPlans().stream().map(ShiftPlan::getId).toList()
        );
    }

    @Override
    public Department mapFrom(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }
        Department department = new Department();
        if (departmentDTO.id() != null) {
            department = departmentService.loadDepartment(departmentDTO.id())
                    .orElse(new Department());
        }
        department.setName(departmentDTO.name());
        department.setOpeningTime(departmentDTO.openingTime());
        department.setClosingTime(departmentDTO.closingTime());

        if (departmentDTO.managerId() != null) {
            Optional<Userx> managerOpt = departmentService.loadDepartment(departmentDTO.managerId())
                    .map(Department::getManager);
            managerOpt.ifPresent(department::setManager);
        }

        department.setShiftPlans(departmentDTO.shiftPlanIds().stream().map(shiftPlanService::loadShiftPlan).map(Optional::get).toList());
        return department;
    }
}
