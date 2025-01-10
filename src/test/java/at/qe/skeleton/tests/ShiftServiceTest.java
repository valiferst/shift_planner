package at.qe.skeleton.tests;

import at.qe.skeleton.services.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Some very basic tests for {@link ShiftService}.
 *
 */
@SpringBootTest
@WebAppConfiguration
public class ShiftServiceTest {

    @Autowired
    ShiftService shiftService;



}
