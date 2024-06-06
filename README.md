# Projektbeschreibung Fahrten-Logger

> CLI zum Verwalten von Autofahrten, Tankvorgängen, etc.

![image](https://github.com/ntrenz/fahrten-logger/assets/100510761/7e21e94a-cc60-4c7d-aa2f-b90db343f4d6)

| <u>Projekt:</u> | fahrten-logger |
| - | - |
| <u>Von:</u> | Robin Pfaff () |
| | Niklas Trenz () |
| <u>Modul:</u> | Advanced Software Engineering, SS2024, DHBW Karlsruhe |
| <u>GitHub-Link:</u> | [GitHub fahrten-logger](https://github.com/ntrenz/fahrten-logger) |

## Projektbeschreibung

Dieses Projekt ist im Rahmen der Vorlesung Advanced Software Engineering entstanden. Es dient als Leistungsnachweis.

Der "fahrten-logger" ist eine terminal basierte Anwendung zum Erfassen von Fahrten von A nach B, Tankvorgängen und der Analyse dieser Fahrten nach bestimmten Kriterien. Durch die Eingabe von Befehlen werden die Eingabedaten verarbeitet und in CSV-Dateien gespeichert (diese können dann mit professionellen Tools wie Excel analysiert werden). Eine grundlegende Analyse der Daten wird ebenfalls in Form von Befehlen bereitgestellt.

### Befehle

Struktur eines Befehls: `<befehl> <sub-befehl:?> <parameter[]:?>`

| Befehl | Sub-Befehl | Beschreibung |
| - | - | - |
| `trip` | `new` | Legt einen neuen trip an. Parameter: Stadt Start, Stadt Ziel, Datum?, Distanz? |
| | `read` | Liest $n$ trips aus, sofern vorhanden. Parameter: |
| | `update` | Akutalisiert den spezifizierten Trip. Parameter: |
| | `delete` | Löscht $n$ trips, wenn vorhanden. Parameter:  |
| `refuel` | `new` ||
| | `read` | |
| | `update` | |
| | `delete` | |
| `analyze` |||
| `help` | N/A | Zeigt den allgemeinen Hilfe-Text an. |
| | `trip` ||
| | `refuel` | |
| | `analyze` | |
| | `exit` | |
| `exit` | N/A | Speichert die Daten und beendet das Programm. |

## Architektur

### Project Structure

1. main: controlling functionality and init
2. plugins: plugins for data access and other libraries
3. adapters: adapters to talk to the application
4. application: application code
5. domain: domain specific code

### Configurations

Configurations are currently used in IntelliJ.

- Run All Tests: run all in project configured tests
- Build: Build the whole project
- **Run App**: run main of the project
