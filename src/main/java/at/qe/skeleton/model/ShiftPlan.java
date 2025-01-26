package at.qe.skeleton.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Entity
public class ShiftPlan implements Persistable<Long>, Serializable, Comparable<ShiftPlan> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the ShiftPlan

    @ManyToOne
    private Department department; // Associated department

    @OneToMany(mappedBy = "shiftPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shift> shifts; // List of shifts in the plan

    @Enumerated(EnumType.STRING)
    private ShiftPlanState state; // Current state of the ShiftPlan

    private LocalDateTime date; // Date associated with the start or week of the ShiftPlan

    @CreationTimestamp
    private LocalDateTime createDate; // Date when the plan was created

    @UpdateTimestamp
    private LocalDateTime updateDate; // Date when the plan was last updated

    private String name; // Name of the ShiftPlan

    private LocalDateTime startDate; // Start date of the ShiftPlan

    private LocalDateTime endDate; // End date of the ShiftPlan

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
        if (this.shifts == null) {
            this.shifts = new ArrayList<>();
        } else {
            this.shifts.clear();
        }
        if (shifts != null) {
            this.shifts.addAll(shifts);
        }
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // Standard methods
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
    public boolean isNew() {
        return (null == id);
    }

    @Override
    public int compareTo(ShiftPlan o) {
        return this.id.compareTo(o.getId());
    }
}
