# Projektplan

- fachliches Klassendiagramm als Startpunkt

### Sinnvolle Reihenfolge für den "Kern" und damit Grundgerüst der App
1. **Entitätsklassen erstellen** (Template von Userx und AuditLog Musterlösung)
2. **Unit-Tests für Entitätsklassen erstellen** (können später noch ausgebaut werden)
3. **Repositories für Entitätsklassen erstellen** (können erstmal leer sein und nur von AbstractRepo erben)
4. **Services für Entitätsklassen erstellen** (können erstmal erstellt werden und evtl. wenn nicht gebraucht wieder entfert)
5. **DTOs erstellen** (möglicherweise werden nicht alle attribute im Front-End benötigt)
6. **DTO-Mapper erstellen** (über die Richtung des Mappings nachdenken, was muss vom Frontend zum Backend und andersherum)

### Validierungslogik der Schichtpläne


## Frontend
- Über die verschiedene Ansichten nachdenken
- Für jede Rolle eine Ansicht erstellen (Funktionaliät der Projektbeschreibung entnehmen)

## USE-Cases
- Erstellung eines Schichtplans
- Abwesenheiten eintragen
- Veröffentlichung eines Schichtplans
- Wiederverwendung eines Schichtplans