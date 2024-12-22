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
