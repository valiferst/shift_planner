package at.qe.skeleton.controllers;

import at.qe.skeleton.dtos.ShiftDTO;
import at.qe.skeleton.dtos.UserxDTO;
import at.qe.skeleton.mappers.ShiftMapper;
import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    /**
     * Deletes shift of given id.
     *
     * @param id the id of the shift to delete
     * @return {@link ResponseEntity} with status {@code 204 (No Content)} on successful delete, or with status {@code 404 (Not Found)} if no user with this id exists
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        Optional<Shift> existingShift = shiftService.loadShift(id);
        if (existingShift.isPresent()) {
            shiftService.deleteShift(existingShift.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shift not found");
        }
    }
}
