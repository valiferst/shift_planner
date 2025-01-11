package at.qe.skeleton.controllers;

import at.qe.skeleton.dtos.ShiftDTO;
import at.qe.skeleton.mappers.ShiftMapper;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Shift endpoints exposed by the server.
 *
 */
@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final ShiftMapper shiftMapper;
    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftMapper shiftMapper, ShiftService shiftService) {
        this.shiftMapper = shiftMapper;
        this.shiftService = shiftService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getCurrentShift(@PathVariable Long id) {
        Optional<Shift> currentShift = shiftService.loadShift(id);
        if (currentShift.isPresent()) {
            ShiftDTO shiftDto = shiftMapper.mapTo(currentShift.get());
            return ResponseEntity.ok(shiftDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
