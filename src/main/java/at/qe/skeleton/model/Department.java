// Dummy class for Department
package at.qe.skeleton.model;

import jakarta.persistence.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Department implements Persistable<Long>, Serializable, Comparable<Department> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;


    @OneToMany(mappedBy = "department")
    private List<ShiftPlan> shiftPlans;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Userx manager;

    // Constructors
    public Department(String name, LocalDateTime openingTime, LocalDateTime closingTime) {
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public Department() {}

    // Getters and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public List<ShiftPlan> getShiftPlans() {
        return shiftPlans;
    }

    public void setShiftPlans(List<ShiftPlan> shiftPlans) {
        this.shiftPlans = shiftPlans;
    }

    public Userx getManager() {
        return manager;
    }

    public void setManager(Userx manager) {
        this.manager = manager;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public int compareTo(Department other) {
        return this.id.compareTo(other.getId());
    }

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
        if (!(obj instanceof Department other)) {
            return false;
        }
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "at.qe.skeleton.model.Department[ id=" + id + " ]";
    }
}
