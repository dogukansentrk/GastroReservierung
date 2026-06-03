# Gastro-Reservierungs-System
 
## Projektübersicht
 
Ein digitales Backend-System zur Verwaltung von Tischen, Kunden und Reservierungen für lokale Gastronomiebetriebe. Das System löst manuelle Reservierungsbücher ab, automatisiert die Verfügbarkeitsprüfung, verhindert Fehlbuchungen und bietet jederzeit einen klaren Überblick über die Auslastung.
 
Der aktuelle Prototyp bietet ein Menü über die Kommandozeile und dient als stabile Datenbasis für spätere grafische Benutzeroberflächen.
 
## Kernfunktionen
 
- **Tischverwaltung:** Anlegen und Abrufen von Tischen (inklusive Tischnummer, Sitzplatzanzahl und Bereich wie z.B. "Innenbereich" oder "Terrasse").
- **Kundenverwaltung:** Registrierung und Abruf von Kundendaten (Name und Telefonnummer).
- **Reservierungslogik:**
  - Erstellung von Reservierungen für bestimmte Daten und Uhrzeiten.
  - Verknüpfung von Kunden mit spezifischen Tischen.
- **Konfliktprüfung:** Automatische Blockierung von Buchungen, falls der Tisch zur gewünschten Zeit bereits belegt ist (verhindert Doppelbuchungen).
- **Übersichten:** Abrufen von Listen aller getätigten Reservierungen, registrierten Kunden und Tische.
## Geplante Erweiterungen
 
- Stornierungsfunktion für bestehende Reservierungen.
- Automatische Tischzuweisung basierend auf der Personenanzahl der Gäste.
- Definition von Zeitfenstern (z.B. Standard-Blockierung eines Tisches für 2 Stunden).
## Technologien
 
- **Programmiersprache:** Java (JDK 8+)
- **Datenbank:** SQLite (lokale relationale Speicherung)
- **Persistenz-Framework:** ORMLite (objektrelationale Anbindung via DAO-Muster)
- **Benutzerschnittstelle:** Textbasierte Konsolenanwendung (CLI)
## Datenmodell
 
Das System basiert auf drei zentralen Entitäten:
 
### Tisch
 
| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | Integer | Primary Key, Auto-Increment |
| `tischNummer` | Integer | Eindeutige Tischnummer (Unique) |
| `anzahlSitzplaetze` | Integer | Kapazität des Tisches |
| `bereich` | String | z.B. "Innenbereich", "Terrasse" |
 
### Kunde
 
| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | Integer | Primary Key, Auto-Increment |
| `name` | String | Vollständiger Name |
| `telefonnummer` | String | Kontaktmöglichkeit |
 
### Reservierung
 
| Feld | Typ | Beschreibung |
|---|---|---|
| `id` | Integer | Primary Key, Auto-Increment |
| `tisch_id` | Integer | Foreign Key auf Tisch |
| `kunde_id` | Integer | Foreign Key auf Kunde |
| `datum` | String | Format: YYYY-MM-DD |
| `uhrzeit` | String | Format: HH:MM, z.B. "19:00" |
 
## Projektstruktur
 
```
src/main/java/gastro/
├── Main.java
├── model/
│   ├── Tisch.java
│   ├── Kunde.java
│   └── Reservierung.java
├── dao/
│   ├── DatabaseManager.java
│   ├── TischDao.java
│   ├── KundeDao.java
│   └── ReservierungDao.java
├── service/
│   └── ReservierungService.java
└── ui/
    └── ConsoleUI.java
```
