package at.qe.skeleton.controllers;

import at.qe.skeleton.dtos.ShiftPlanDTO;
import at.qe.skeleton.mappers.ShiftPlanMapper;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.services.ShiftPlanService;
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
@RequestMapping("/api/shiftplans")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;
    private final ShiftPlanMapper shiftPlanMapper;

    @Autowired
    public ShiftPlanController(ShiftPlanService shiftPlanService, ShiftPlanMapper shiftPlanMapper) {
        this.shiftPlanService = shiftPlanService;
        this.shiftPlanMapper = shiftPlanMapper;
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
     * @param shiftPlanDTO DTO containing shift plan details.
     * @return The created shift plan as a DTO.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ShiftPlanDTO> createShiftPlan(@Valid @RequestBody ShiftPlanDTO shiftPlanDTO) {
        ShiftPlan shiftPlan = shiftPlanMapper.mapFrom(shiftPlanDTO);
        ShiftPlan savedShiftPlan = shiftPlanService.saveShiftPlan(shiftPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(shiftPlanMapper.mapTo(savedShiftPlan));
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
     * @param shiftPlanDTO DTO containing to be published shift plan details.
     * @return The published shift plan as a DTO.
     */
    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ShiftPlanDTO> publishShiftPlan(@PathVariable Long id, @RequestBody ShiftPlanDTO shiftPlanDTO) {
        Optional<ShiftPlan> existingPlan = shiftPlanService.loadShiftPlan(id);
        if (existingPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ShiftPlan shiftPlan = shiftPlanMapper.mapFrom(shiftPlanDTO);
        ShiftPlan publishedShiftPlan = shiftPlanService.publishShiftPlan(shiftPlan);
        return ResponseEntity.ok(shiftPlanMapper.mapTo(publishedShiftPlan));
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
