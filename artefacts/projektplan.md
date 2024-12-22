# Projektplan

- fachliches Klassendiagramm als Startpunkt

### Sinnvolle Reihenfolge für den "Kern" und damit Grundgerüst der App
1. **Entitätsklassen erstellen** (Template von Userx und AuditLog Musterlösung)
2. **Repositories für Entitätsklassen erstellen** (können erstmal leer sein und nur von AbstractRepo erben)
3. **Services für Entitätsklassen erstellen** (können erstmal erstellt werden und evtl. wenn nicht gebraucht wieder entfert)
4. **Unit-Tests für Services erstellen** (können später noch ausgebaut werden)
5. **DTOs erstellen** (möglicherweise werden nicht alle attribute im Front-End benötigt)
6. **DTO-Mapper erstellen** (über die Richtung des Mappings nachdenken, was muss vom Frontend zum Backend und andersherum)

### Validierungslogik der Schichtpläne

- Sequenzdiagramm als Startpunkt


## Frontend
- Über die verschiedene Ansichten nachdenken
- Für jede Rolle eine Ansicht erstellen (Funktionaliät der Projektbeschreibung entnehmen)

### Entwicklungsschritte (Programmieren) anhand der REACT.js Baumstruktur (top-down, von Wurzel zu Blättern)
1. `App.tsx` und `routes.js` modifizieren
2. Im Ordner `./views` neue views erstellen
3. Im Ordner `./components` falls nötig neue components erstellen
4. Im Order `./factories` und `./utilities` falls nötig neue crud und factory erstellen

## USE-Cases (`swa_projekt_aufgabe.pdf` nochmal scannen um alle zu finden)
- Erstellung eines Schichtplans (Manager:in)
- Abwesenheiten eintragen (Mitarbeiter:in)
- Veröffentlichung eines Schichtplans (Manager:in)
- Wiederverwendung eines Schichtplans (Manager:in)
- Erstellen eines Departments (Admin)
- Ändern eines Departments (Admin)
- Löschen eines Departments (Admin)
- Erstellen eines Users (Admin, **bereits im skeleton vorhanden**)
- Ändern eines Users (Admin, **bereits im skeleton vorhanden**)
- Löschen eines Users (Admin, **bereits im skeleton vorhanden**)