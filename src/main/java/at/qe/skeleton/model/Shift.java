// Dummy class for Shift
package at.qe.skeleton.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ShiftPlan shiftPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShiftPlan getShiftPlan() {
        return shiftPlan;
    }

    public void setShiftPlan(ShiftPlan shiftPlan) {
        this.shiftPlan = shiftPlan;
    }
}
