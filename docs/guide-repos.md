# Anleitung für Repositories
## AbstractRepository (ein Interface von dem alle repos erben)
Das AbstractRepository sieht so aus:
```
/**
* Common base repository for all other repositories. Provides basic methods for
* loading, saving and removing entities.
  */

  public interface AbstractRepository<T, ID extends Serializable> extends Repository<T, ID> {
      
      void delete(T entity);
      List<T> findAll();
      Optional<T> findById(ID id);
      <S extends T> S save(S entity);
  }
```
Das heißt alle repo die von dem AbstractRepository erben bieten schon die folgenden Funktionalitäten, auch wenn sie **LEER** sind:
- löschen
- alle finden
- mit der Id finden
- speichern

z.B. mit :

```
public interface AbsenceRepository extends AbstractRepository<Absence, Long> {

}
```
kann man schon Absences speichern, löschen, alle herausgeben und mit einer Id finden.

## "Custom"-Funktionalität für Repos

### Beispiel für AuditLog

```
public interface AuditLogRepository extends AbstractRepository<AuditLog, Long> {

    List<AuditLog> findByEvent(AuditEvent event);

}
```
Es wurde eine weitere Methode **findByEvent** geschrieben.

### Beispiel für Userx

```
public interface UserxRepository extends AbstractRepository<Userx, Long> {

    Optional<Userx> findFirstByUsername(String username);

    List<Userx> findByUsernameContaining(String username);

    @Query("SELECT u FROM Userx u WHERE CONCAT(u.firstName, ' ', u.lastName) = :wholeName")
    List<Userx> findByWholeNameConcat(@Param("wholeName") String wholeName);

    @Query("SELECT u FROM Userx u WHERE :role MEMBER OF u.roles")
    List<Userx> findByRole(@Param("role") UserxRole role);

    public boolean existsByUsername(String username);

}
```

- findFirstByUsername
- findByUsernameContaining
- findByWholeNameConcat
- findByRole
- existsByUsername