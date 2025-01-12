package at.qe.skeleton.tests;

import at.qe.skeleton.services.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest()
@WebAppConfiguration
public class AbsenceServiceTest {

    @Autowired
    AbsenceService absenceService;




}
