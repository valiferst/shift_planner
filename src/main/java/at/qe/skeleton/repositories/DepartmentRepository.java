package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Department;
import at.qe.skeleton.model.Userx;

import java.util.List;

/**
 * Repository for managing {@link Department} entities.
 */

public interface DepartmentRepository extends AbstractRepository<Department, Long> {
    public boolean existsByName(String name);

    boolean existsByManagerId(Long userId);

    List<Department> getDepartmentByManager(Userx manager);
}
