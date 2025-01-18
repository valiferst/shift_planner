package at.qe.skeleton.controllers;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.repositories.ShiftPlanRepository;
import at.qe.skeleton.services.ShiftPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shiftplans")
public class ShiftPlanController {

    private final ShiftPlanService shiftPlanService;
    private final ShiftPlanRepository shiftPlanRepository;  // Injected repository

    @Autowired
    public ShiftPlanController(ShiftPlanService shiftPlanService, ShiftPlanRepository shiftPlanRepository) {
        this.shiftPlanService = shiftPlanService;
        this.shiftPlanRepository = shiftPlanRepository;  // Injected repository
    }

    @PutMapping("/{id}/{attributeName}")
    public ResponseEntity<String> updateShiftPlanAttribute(
            @PathVariable Long id,
            @PathVariable String attributeName,
            @RequestBody Object value) {

        ShiftPlan shiftPlan = shiftPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ShiftPlan not found"));

        shiftPlanService.updateShiftPlan(shiftPlan, attributeName, value);

        return ResponseEntity.ok("ShiftPlan attribute updated successfully.");
    }
}
