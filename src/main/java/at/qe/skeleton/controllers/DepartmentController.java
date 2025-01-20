package at.qe.skeleton.controllers;

import at.qe.skeleton.dtos.DepartmentDTO;
import at.qe.skeleton.mappers.DepartmentMapper;
import at.qe.skeleton.model.Department;
import at.qe.skeleton.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Shift Plans.
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }


    /**
     * Retrieves all departments.
     *
     * @return List of all departments as DTOs.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments().stream()
                .map(departmentMapper::mapTo)
                .toList();
        return ResponseEntity.ok(departments);
    }


    @GetMapping("/my")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByManagerId() {
        List<DepartmentDTO> departments = departmentService.getDepartmentsByManagerId().stream()
                .map(departmentMapper::mapTo)
                .toList();
        return ResponseEntity.ok(departments);
    }

    /**
     * Retrieves a single department by ID.
     *
     * @param id ID of the department.
     * @return The requested department as a DTO.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        Optional<Department> departmentOpt = departmentService.loadDepartment(id);
        // TODO: add check if user is allowed to view department
        return departmentOpt.map(department -> ResponseEntity.ok(departmentMapper.mapTo(department)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new department.
     *
     * @param departmentDTO DTO containing department details.
     * @return The created department as a DTO.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        Department department = departmentMapper.mapFrom(departmentDTO);
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentMapper.mapTo(savedDepartment));
    }

    /**
     * Updates an existing department.
     *
     * @param id ID of the department to update.
     * @param departmentDTO DTO containing updated department details.
     * @return The updated department as a DTO.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        Optional<Department> existingPlan = departmentService.loadDepartment(id);
        if (existingPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Department department = departmentMapper.mapFrom(departmentDTO);
        Department updatedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(departmentMapper.mapTo(updatedDepartment));
    }

    /**
     * Deletes a department by ID.
     *
     * @param id ID of the department to delete.
     * @return Response with status indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> existingPlan = departmentService.loadDepartment(id);
        if (existingPlan.isPresent()) {
            departmentService.deleteDepartment(existingPlan.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
