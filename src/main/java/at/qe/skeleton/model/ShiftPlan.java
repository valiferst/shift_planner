package at.qe.skeleton.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ShiftPlan {
    @Id
    private Long id;

    @ManyToOne
    private Department department;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isNew() {
        return (null == id);
    }

    public void setCreateUser(Userx authenticatedUser) {
    }

    public void setUpdateUser(Userx authenticatedUser) {
    }
}
