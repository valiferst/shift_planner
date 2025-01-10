package at.qe.skeleton.mappers;

import at.qe.skeleton.dtos.DepartmentDTO;
import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.DepartmentService;
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

    @Autowired
    public DepartmentMapper(DepartmentService departmentService) {
        this.departmentService = departmentService;
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
                department.getShiftPlans()
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

        department.setShiftPlans(departmentDTO.shiftPlans());
        return department;
    }
}
