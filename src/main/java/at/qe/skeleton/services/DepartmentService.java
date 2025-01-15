package at.qe.skeleton.services;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.ShiftPlan;
import at.qe.skeleton.model.ShiftPlanState;
import at.qe.skeleton.model.Userx;
import at.qe.skeleton.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Departments.
 */
@Service
@Scope("application")
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Saves a new or existing department.
     *
     * @param department The department to save.
     * @return The saved department.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Department saveDepartment(Department department) {
        if (department.isNew() && departmentRepository.existsByName(department.getName())) {
            throw new IllegalArgumentException("Department with the name " + department.getName() + " already exists.");
        }
        return departmentRepository.save(department);
    }

    /**
     * Retrieves all departments.
     *
     * @return A list of all departments.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Loads a single department by its ID.
     *
     * @param id The ID of the department.
     * @return The department, if found.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<Department> loadDepartment(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * Deletes a department.
     *
     * @param department The department to delete.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(Department department) {
        if (department.getShiftPlans() != null && !department.getShiftPlans().isEmpty()) {
            throw new IllegalStateException("Can not delete an department with associated shift plans.");
        }
        departmentRepository.delete(department);
    }

    /**
     * Assigns a manager to a department.
     * @param departmentId The ID of the department.
     * @param manager The manager to assign.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void assignManager(Long departmentId, Userx manager) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));
        department.setManager(manager);
        departmentRepository.save(department);
    }

    /**
     * Gets all shift plans for a department.
     * @param departmentId The ID of the department.
     * @return A list of shift plans for the department.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<ShiftPlan> getShiftPlansByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));
        return department.getShiftPlans();
    }

    /**
     * Validates the opening and closing times for a department.
     * @param openingTime The opening time.
     * @param closingTime The closing time.
     */
    public void validateOpeningHours(LocalDateTime openingTime, LocalDateTime closingTime) {
        if (openingTime == null || closingTime == null) {
            throw new IllegalArgumentException("Opening and closing times must not be null.");
        }
        if (openingTime.isAfter(closingTime)) {
            throw new IllegalArgumentException("Opening time cannot be after closing time.");
        }
    }

    /**
     * Retrieves the single PUBLISHED shift plan for a department.
     * If there is no PUBLISHED shift plan, returns null.
     * If multiple PUBLISHED shift plans exist, an exception is thrown.
     *
     * @param departmentId The ID of the department.
     * @return The PUBLISHED shift plan, or null if none exists.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public ShiftPlan getPublishedShiftPlan(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        List<ShiftPlan> publishedShiftPlans = department.getShiftPlans().stream()
                .filter(shiftPlan -> shiftPlan.getState() == ShiftPlanState.PUBLISHED)
                .toList();

        if (publishedShiftPlans.size() > 1) {
            throw new IllegalStateException("Multiple PUBLISHED shift plans found for the department.");
        }

        return publishedShiftPlans.isEmpty() ? null : publishedShiftPlans.get(0);
    }


    /**
     * Returns the full name of the manager of a department (First Name, Last Name, username),
     * so it can be used in the frontend to set the manager of a department.
     * @param department The opening time.
     */
    public String getFullManagerName(Department department) {
        return department.getManager().getFirstName() + " " + department.getManager().getLastName() + " ("  +
                department.getManager().getUsername() + ")";
    }

    public ShiftPlan getPublishedPlan(Long departmentId) {
        return null;
    }
}