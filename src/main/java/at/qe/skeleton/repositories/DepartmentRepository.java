package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Department;

import java.util.Collection;

/**
 * Repository for managing {@link Department} entities.
 */

public interface DepartmentRepository extends AbstractRepository<Department, Long> {
    public boolean existsByName(String name);

    boolean existsByManagerId(Long userId);

    Collection<Department> getDepartmentsByManagerId(Long userId);
}
