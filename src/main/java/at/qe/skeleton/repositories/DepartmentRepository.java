package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Department;

/**
 * Repository for managing {@link Department} entities.
 */

public interface DepartmentRepository extends AbstractRepository<Department, Long> {
    public boolean existsByName(String name);
}
