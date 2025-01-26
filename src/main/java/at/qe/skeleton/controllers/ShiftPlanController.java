package at.qe.skeleton.controllers;

import at.qe.skeleton.dtos.ShiftPlanCreateDTO;
import at.qe.skeleton.mappers.ShiftPlanCreateMapper;
import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.dtos.ValidationErrorDTO;
import at.qe.skeleton.mappers.ShiftPlanMapper;
import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.ValidationError;
import at.qe.skeleton.services.DepartmentService;
import at.qe.skeleton.services.ShiftPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing Shift Plans.
 */
@RestController
@RequestMapping("/api/shiftplans")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;
    private final DepartmentService departmentService;
    private final ShiftPlanMapper shiftPlanMapper;
    private final ShiftPlanCreateMapper shiftPlanCreateMapper;

    // TODO: check how to handle non singleton bean autowiring
    @Autowired
    public ShiftPlanController(ShiftPlanService shiftPlanService, DepartmentService departmentService, ShiftPlanMapper shiftPlanMapper, ShiftPlanCreateMapper shiftPlanCreateMapper) {
        this.shiftPlanService = shiftPlanService;
        this.departmentService = departmentService;
        this.shiftPlanMapper = shiftPlanMapper;
        this.shiftPlanCreateMapper = shiftPlanCreateMapper;
    }

    /**
     * Retrieves all shift plans.
     *
     * @return List of all shift plans as DTOs.
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<ShiftPlanDTO>> getAllShiftPlans() {
        List<ShiftPlanDTO> shiftPlans = shiftPlanService.getAllShiftPlans().stream()
                .map(shiftPlanMapper::mapTo)
                .toList();
        return ResponseEntity.ok(shiftPlans);
    }

    /**
     * Retrieves all shift plans for a manager.
     *
     * @return List of all ShiftPlans DTOs for departments where the user is manager.
     */
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<ShiftPlanDTO>> getAllShiftPlansForManager() {
        Collection<Department> departments = departmentService.getDepartmentsByManagerId();
        List<ShiftPlanDTO> shiftPlans = departments.stream()
                .map(Department::getId)
                .flatMap(id -> shiftPlanService.getShiftPlansByDepartmentId(id).stream())
                .map(shiftPlanMapper::mapTo)
                .toList();
        return ResponseEntity.ok(shiftPlans);
    }

    /**
     * Retrieves all shift plans for a department.
     *
     * @return List of all ShiftPlans as DTOs for a department.
     */
    @GetMapping("/by_department_id")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<ShiftPlanDTO>> getShiftPlansByDepartmentId(@RequestParam Long departmentId) {
        List<ShiftPlanDTO> shiftPlans = shiftPlanService.getShiftPlansByDepartmentId(departmentId).stream()
                .map(shiftPlanMapper::mapTo)
                .toList();
        return ResponseEntity.ok(shiftPlans);
    }

    /**
     * Retrieves a single shift plan by ID.
     *
     * @param id ID of the shift plan.
     * @return The requested shift plan as a DTO.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ShiftPlanDTO> getShiftPlan(@PathVariable Long id) {
        Optional<ShiftPlan> shiftPlanOpt = shiftPlanService.loadShiftPlan(id);
        return shiftPlanOpt.map(shiftPlan -> ResponseEntity.ok(shiftPlanMapper.mapTo(shiftPlan)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new shift plan.
     *
     * @param shiftPlanCreateDTO DTO containing shift plan details.
     * @return The created shift plan as a DTO.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ShiftPlanCreateDTO> createShiftPlan(@Valid @RequestBody ShiftPlanCreateDTO shiftPlanCreateDTO) {
        ShiftPlan shiftPlan = shiftPlanCreateMapper.mapFrom(shiftPlanCreateDTO);
        ShiftPlan savedShiftPlan = shiftPlanService.saveShiftPlan(shiftPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(shiftPlanCreateMapper.mapTo(savedShiftPlan));
    }

    /**
     * Updates an existing shift plan.
     *
     * @param id ID of the shift plan to update.
     * @param shiftPlanDTO DTO containing updated shift plan details.
     * @return The updated shift plan as a DTO.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ShiftPlanDTO> updateShiftPlan(@PathVariable Long id, @Valid @RequestBody ShiftPlanDTO shiftPlanDTO) {
        Optional<ShiftPlan> existingPlan = shiftPlanService.loadShiftPlan(id);
        if (existingPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ShiftPlan shiftPlan = shiftPlanMapper.mapFrom(shiftPlanDTO);
        shiftPlan.setId(id);
        ShiftPlan updatedShiftPlan = shiftPlanService.saveShiftPlan(shiftPlan);
        return ResponseEntity.ok(shiftPlanMapper.mapTo(updatedShiftPlan));
    }

    /**
     * Publishes an existing shift plan.
     *
     * @param id ID of the shift plan to publish.
     * @return The published shift plan as a DTO.
     */
    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<List<ValidationErrorDTO>> publishShiftPlan(@PathVariable Long id) {
        Optional<ShiftPlan> existingPlan = shiftPlanService.loadShiftPlan(id);
        if (existingPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ValidationError> errors = shiftPlanService.publishShiftPlan(existingPlan.get());

        if (!errors.isEmpty()) {
            List<ValidationErrorDTO> validationErrorDTOS = errors.stream()
                    .map(ValidationError::mapToDto)
                    .toList();
            return ResponseEntity.badRequest().body(validationErrorDTOS);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    /**
     * Deletes a shift plan by ID.
     *
     * @param id ID of the shift plan to delete.
     * @return Response with status indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<Void> deleteShiftPlan(@PathVariable Long id) {
        Optional<ShiftPlan> existingPlan = shiftPlanService.loadShiftPlan(id);
        if (existingPlan.isPresent()) {
            shiftPlanService.deleteShiftPlan(existingPlan.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
