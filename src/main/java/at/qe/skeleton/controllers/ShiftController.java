package at.qe.skeleton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
