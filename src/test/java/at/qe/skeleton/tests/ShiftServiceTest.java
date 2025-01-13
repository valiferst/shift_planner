package at.qe.skeleton.tests;

import at.qe.skeleton.model.Shift;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.services.ShiftService;
import at.qe.skeleton.services.UserxService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.Executable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Some very basic tests for {@link ShiftService}.
 *
 */
@SpringBootTest
@WebAppConfiguration
public class ShiftServiceTest {

    @Autowired
    ShiftService shiftService;
    @Autowired
    UserxService userxService;

//    @DirtiesContext
//    @Test
//    @WithMockUser(username = "user1", authorities = {"MANAGER"})
//    public void testDeleteShift() {
//        Long deleteShiftId = 2001L;
//        Optional<Userx> managerUser = userxService.loadUser(2000L);
//        Assertions.assertFalse(managerUser.isEmpty(), "Manager user could not be loaded from test data source");
//        Optional<Shift> toBeDeletedShiftOpt = shiftService.loadShift(deleteShiftId);
//        Assertions.assertFalse(toBeDeletedShiftOpt.isEmpty(), "Shift with id \"" + deleteShiftId + "\" could not be loaded from test data source");
//        Shift toBeDeletedShift = toBeDeletedShiftOpt.get();
//
//        shiftService.deleteShift(toBeDeletedShift);
//
//        Assertions.assertEquals(2, shiftService.getAllUserShifts(managerUser.get()).size(), "No shift has been deleted after calling ShiftService.deleteShift");
//        Optional<Shift> deletedShiftOpt = shiftService.loadShift(deleteShiftId);
//        Assertions.assertTrue(deletedShiftOpt.isEmpty(), "Deleted Shift with id \"" + deleteShiftId + "\" could still be loaded from test data source via ShiftService.loadShift");
//
//        for (Shift remainingShift : shiftService.getAllUserShifts(managerUser.get())) {
//            Assertions.assertNotEquals(toBeDeletedShift.getId(), remainingShift.getId(), "Deleted Shift with id \"" + deleteShiftId + "\" could still be loaded from test data source via ShiftService.getAllUserShifts");
//        }
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = {"MANAGER"})
//    public void testCopyShift(){
//        LocalDateTime newTime = LocalDateTime.of(2025, 03, 02, 10, 00, 00);
//        LocalDateTime earlyTime = LocalDateTime.of(2000, 01, 02, 10, 00, 00);
//
//        Optional<Shift> copyShift = shiftService.loadShift(2001L);
//        Assertions.assertFalse(copyShift.isEmpty(), "Shift to copy could not be loaded from test data source");
//
//        Shift newShift = shiftService.copyShift(copyShift.get(), newTime);
//        //Is shift correct copied
//        Duration duration = copyShift.get().getShiftDuration();
//        Assertions.assertEquals(newTime, newShift.getStartTime(), "Copied shift does not have the new startTime");
//        Assertions.assertEquals(newTime.plus(duration), newShift.getEndTime(), "Copied shift does not have the correct endTime");
//        Assertions.assertEquals(copyShift.get().getShiftPlan(), newShift.getShiftPlan(), "Copied shift does not have the same shiftPlan");
//        Assertions.assertEquals(copyShift.get().getShiftWorkers(), newShift.getShiftWorkers(), "Copied shift does not have the same shiftWorkers");
//
//        // Throws the correct Exceptions
//        Assertions.assertThrows(IllegalArgumentException.class, () -> shiftService.copyShift(newShift, earlyTime), "Tried to copy a shift into the past");
//
//        Shift isNew = new Shift();
//        Assertions.assertThrows(IllegalArgumentException.class, () -> shiftService.copyShift(isNew, newTime), "Tried to copy a new Shift");
//    }



}
