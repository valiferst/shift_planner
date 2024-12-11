# Entities (Classes)

## User and Role-Enum

### User

1. has zero to many `Departments`
2. has one `Role`

_Citations:_

1. _Wöchentliche Schichtpläne für die zugewiesenen Dienststellen erstellen und bearbeiten._

### Role

1. `ADMIN`
2. `MANAGER`
3. `EMPLOYEE`


## Department

1. has zero to many employees : `User` (_Mitarbeiter:innen_)
2. has a start-time (**type to be determined**)
3. has an end-time (**type to be determined**)
4. has one manager : `User` (_Manager:in_)

_Citations:_

1. _Eine Dienststelle beschäftigt Mitarbeiter:innen_ 
2. _... und hat vorgegebene Dienstzeiten_
3. _... und hat vorgegebene Dienstzeiten_
4. _Manager:innen Dienststellen zuweisen..._

## ShiftPlan and Status-Enum

### ShiftPlan

1. has one `Department`
2. has zero to many `Shift`
3. has one `Status`
4. has a date (**type of date yet to be determined; weeknumber, date of startday (mon/sun)**)

_Citations:_

1. _Ein Schichtplan ist einer Dienststelle zugeordnet ..._
2. _Ein Schichtplan kann beliebig viele Schichten beinhalten._
3. _Ein Schichtplan kann im Status „in Entwurf“, „veröffentlicht“ oder „deaktiviert“ sein._
4. _für jede Woche wird ein eigener Schichtplan erstellt_

### Status

1. `DRAFT`
2. `PUBLISHED`
3. `CANCELLED`

## Shift

1. has one start-time (**type to be determined**)
2. has one end-time (**type to be determined**)
3. has zero to many `User`

_Citations:_

1. _Jede Schicht hat einen Start- und Endzeitpunkt ..._
2. _Jede Schicht hat einen Start- und Endzeitpunkt ..._
3. _... und kann beliebig viele Mitarbeiter umfassen._

# Unconsidered (ambiguous) parts of the project-description
1. _Größere Organisationen können zum Beispiel aus mehreren Dienststellen bestehen,
um die Übersichtlichkeit zu gewährleisten._

# plantUML code snippet

```
@startuml
' Define classes
class User {}

class Department {
  - startTime : TBD
  - endTime : TBD
}

class ShiftPlan {
  - date : TBD
}

class Shift {
  - startTime : TBD
  - endTime : TBD
}

enum Role {
  ADMIN
  MANAGER
  EMPLOYEE
}

enum Status {
  DRAFT
  PUBLISHED
  CANCELLED
}

' Define relationships
User "0..*" -- "0..1" Department : assigned
User "1" -- "1" Role : has
Department "1" -- "1" User : manager
Department "0..*" -- "0..*" User : employees
ShiftPlan "1" -- "1" Department : assigned to
ShiftPlan "0..*" -- "1" Shift : contains
ShiftPlan "1" -- "1" Status : status
Shift "0..*" -- "1" User : assigned to

@enduml

```

