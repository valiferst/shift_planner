package at.qe.skeleton.model;

import java.util.Date;
import java.util.List;

public class ShiftPlan {

    private Long id; // Unique identifier for the ShiftPlan
    private Department department; // Associated department
    private List<Shift> shifts; // List of shifts in the plan
    private ShiftPlanState state; // Current state of the ShiftPlan
    private Date date; // Date associated with the start or week of the ShiftPlan

    // Constructor
    public ShiftPlan(Long id, Department department, List<Shift> shifts, ShiftPlanState state, Date date) {
        this.id = id;
        this.department = department;
        this.shifts = shifts;
        this.state = state;
        this.date = date;
    }

    // Getters and Setters
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Utility methods
    public void addShift(Shift shift) {
        this.shifts.add(shift);
    }

    public void removeShift(Shift shift) {
        this.shifts.remove(shift);
    }
}
