package at.qe.skeleton.tests;

import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.model.UserxRole;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * Tests to ensure that each entity's implementation of equals conforms to the
 * contract. See {@linkplain http://www.jqno.nl/equalsverifier/} for more
 * information.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
public class EqualsImplementationTest {

    @Test
    public void testUserEqualsContract() {
        Userx user1 = new Userx();
        user1.setId(1L);
        Userx user2 = new Userx();
        user2.setId(2L);
        ShiftPlan shiftPlan1 = new ShiftPlan();
        shiftPlan1.setId(1L);
        ShiftPlan shiftPlan2 = new ShiftPlan();
        shiftPlan2.setId(2L);
        EqualsVerifier.forClass(Userx.class)
                .withPrefabValues(Userx.class, user1, user2)
                .withPrefabValues(ShiftPlan.class, shiftPlan1, shiftPlan2)
                .suppress(Warning.STRICT_INHERITANCE, Warning.ALL_FIELDS_SHOULD_BE_USED)
                .verify();
    }

    @Test
    public void testUserRoleEqualsContract() {
        EqualsVerifier.forClass(UserxRole.class).verify();
    }

}