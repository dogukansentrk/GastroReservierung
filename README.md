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

HAUPTZIEL
---------

Man kann über ein Menü in der Konsole Daten:
  - anzeigen (Auslastung, Kundenkartei)
  - hinzufügen (Kunden registrieren, Tische anlegen, Reservierungen buchen)
Alle Daten werden dauerhaft und lokal in einer SQLite-Datenbank gespeichert.


WAS WIRD VERWALTET? (3 Kern-Entitäten)
--------------------------------------
  TISCH         - Tische mit Tischnummer, Sitzplatzanzahl und Bereich (z.B. Terrasse)
  KUNDE         - Gäste mit vollständigem Namen und Telefonnummer
  RESERVIERUNG  - Eine konkrete Buchung an einem Datum zu einer bestimmten Uhrzeit


WIE HÄNGEN DIE DINGE ZUSAMMEN? (Beziehungen)
---------------------------------------------
  - Eine RESERVIERUNG  ist immer genau einem KUNDEN zugeordnet.
  - Eine RESERVIERUNG  belegt immer genau einen TISCH.
  - Ein  TISCH         kann mehrere Reservierungen haben (aber nie zur selben Zeit!).
  - Ein  KUNDE         kann mehrere Reservierungen tätigen.

Diese Verbindungen funktionieren über Fremdschlüssel (Foreign Keys). 
Beispiel: Die Reservierung speichert nicht den kompletten Namen des Gastes ab, 
sondern merkt sich nur die ID des verknüpften Kunden und die ID des verknüpften Tisches.


WOZU SIND DIE KLASSEN DA? (Projektstruktur)
-------------------------------------------
Das Projekt ist nach dem DAO-Muster (Data Access Object) 
strukturiert, damit alles sauber getrennt ist.

  * Main.java
    Der Startpunkt des Programms. Stellt die Verbindung zur Datenbank her und 
    startet das Konsolenmenü.

  * model/ (Die Datenstrukturen)
    - Tisch.java, Kunde.java, Reservierung.java: 
      Das sind die reinen Daten-Objekte. Sie spiegeln exakt die Spalten der 
      Datenbank-Tabellen wider. Ein Objekt entspricht einer Zeile in der Tabelle.

  * dao/ (Datenbankzugriff)
    - DatabaseManager.java: Baut die Verbindung zur lokalen SQLite-Datei (gastro.db) auf.
    - TischDao.java, KundeDao.java, ReservierungDao.java: 
      Hier passieren die eigentlichen Befehle (Speichern, Suchen, Auslesen). 
      Dank ORMLite müssen wir hier fast kein SQL schreiben.

  * service/ (Die Geschäftslogik)
    - ReservierungService.java: Das "Gehirn" der Anwendung. Hier findet die 
      Konfliktprüfung statt. Bevor eine Reservierung an das DAO weitergegeben wird, 
      prüft der Service, ob der gewünschte Tisch zur gewünschten Zeit überhaupt frei ist.

  * ui/ (Die Benutzeroberfläche)
    - ConsoleUI.java: Zeichnet das Text-Menü, nimmt die Tastatureingaben des 
      Nutzers entgegen und gibt Erfolgs- oder Fehlermeldungen auf dem Bildschirm aus.


WELCHE TECHNIK WIRD VERWENDET?
------------------------------
  - Sprache:     Java (JDK 8+)
  - Datenbank:   SQLite (lokale Datei, kein externer Server nötig)
  - ORM:         ORMLite (verbindet Java-Klassen automatisch mit DB-Tabellen)
  - Oberfläche:  Konsole (Text-Menü, Basis für spätere grafische UIs)


SO STARTET MAN ES
-----------------
  1. Projekt in der IDE (z.B. IntelliJ) öffnen.
  2. Sicherstellen, dass Maven die Abhängigkeiten (pom.xml) geladen hat.
  3. Die Klasse gastro.Main starten.
  4. Die Datenbankdatei (gastro.db) sowie die nötigen Tabellen werden 
     beim allerersten Start automatisch erstellt.
