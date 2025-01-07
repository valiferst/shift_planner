package at.qe.skeleton.model;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class ShiftPlan implements Persistable<Long>, Serializable, Comparable<ShiftPlan> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the ShiftPlan

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department; // Associated department

    @OneToMany(mappedBy = "shiftPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Shift> shifts; // List of shifts in the plan

    @Enumerated(EnumType.STRING)
    private ShiftPlanState state; // Current state of the ShiftPlan

    private LocalDateTime date; // Date associated with the start or week of the ShiftPlan

    // No-args constructor (required by JPA)
    public ShiftPlan() {}

    // Flexible constructor for easier instantiation
    public ShiftPlan(Department department, LocalDateTime date) {
        this.department = department;
        this.date = date;
        this.state = ShiftPlanState.DRAFT;
    }

    // Getters and Setters
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    public ShiftPlanState getState() {
        return state;
    }

    public void setState(ShiftPlanState state) {
        this.state = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    // Utility methods
    public void addShift(Shift shift) {
        this.shifts.add(shift);
        // shift.setShiftPlan(this); // Ensure bidirectional sync

    }

    public void removeShift(Shift shift) {
        this.shifts.remove(shift);
        // shift.setShiftPlan(null); // Ensure bidirectional sync
    }

    public void publish() { // Publish the ShiftPlan
        if (this.state == ShiftPlanState.DRAFT) {
            this.state = ShiftPlanState.PUBLISHED;
        } else {
            throw new IllegalStateException("Only draft plans can be published.");
        }
    }

    // standard methods

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ShiftPlan)) {
            return false;
        }
        final ShiftPlan other = (ShiftPlan) obj;
        return Objects.equals(this.getId(), other.getId());
    }
    
    @Override
    public String toString() {
        return "at.qe.skeleton.model.ShiftPlan[ id=" + id + " ]";
    }
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public boolean isNew() {
        return (null == id);
    }
    @Override
    public int compareTo(ShiftPlan o) {
        return this.id.compareTo(o.getId());
    }

}
