# Gastro-Reservierungs-System

Ein digitales Backend-System zur Verwaltung von Tischen, Kunden und Reservierungen für lokale Gastronomiebetriebe. Das System löst manuelle Reservierungsbücher ab, automatisiert die Verfügbarkeitsprüfung, verhindert Fehlbuchungen und bietet jederzeit einen klaren Überblick über die Auslastung.

Der aktuelle Prototyp bietet ein Menü über die Kommandozeile und dient als stabile Datenbasis für spätere grafische Benutzeroberflächen.

## Hauptziel

Man kann über ein Menü in der Konsole Daten:
  - anzeigen (Auslastung, Kundenkartei)
  - hinzufügen (Kunden registrieren, Tische anlegen, Reservierungen buchen)

Alle Daten werden dauerhaft und lokal in einer SQLite-Datenbank (`gastro.db`) gespeichert.

## Was wird verwaltet? (3 Kern-Entitäten)

- **TISCH:** Tische mit Tischnummer, Sitzplatzanzahl und Bereich (z.B. Terrasse).
- **KUNDE:** Gäste mit vollständigem Namen und Telefonnummer.
- **RESERVIERUNG:** Eine konkrete Buchung an einem Datum zu einer bestimmten Uhrzeit.

### Wie hängen die Dinge zusammen? (Beziehungen)
- Eine Reservierung ist immer genau einem Kunden zugeordnet.
- Eine Reservierung belegt immer genau einen Tisch.
- Ein Tisch kann mehrere Reservierungen haben (aber nie zur selben Zeit!).
- Ein Kunde kann mehrere Reservierungen tätigen.

Diese Verbindungen funktionieren über Fremdschlüssel (Foreign Keys). Die Reservierung speichert nicht den kompletten Namen des Gastes ab, sondern merkt sich nur die ID des verknüpften Kunden und die ID des verknüpften Tisches.

## Verwendete Technologien & Abhängigkeiten

Das Projekt nutzt Maven zur Verwaltung der Abhängigkeiten. Folgende Bibliotheken (Libraries) werden benötigt und automatisch über die `pom.xml` geladen:

- **Java Version:** JDK 14
- **ORMLite Core & JDBC:** Version `6.1` (Für das objektrelationale Mapping)
- **SQLite JDBC Driver:** Version `3.45.3.0` (Der eigentliche Datenbanktreiber)
- **SLF4J Simple:** Version `1.7.36` (Für sauberes Logging der ORMLite Datenbank-Aktionen)

## Systemarchitektur & Projektstruktur

Das Projekt ist streng nach dem DAO-Muster (Data Access Object) und MVC-Ansätzen strukturiert, um Datenzugriff, Geschäftslogik und Benutzeroberfläche sauber zu trennen.

```text
src/main/java/gastro/
├── Main.java                        # Startpunkt des Programms
├── model/                           # Die reinen Daten-Objekte (Spiegeln die DB-Tabellen wider)
│   ├── Tisch.java
│   ├── Kunde.java
│   └── Reservierung.java
├── dao/                             # Datenbankzugriff (Speichern, Suchen, Auslesen via ORMLite)
│   ├── DatabaseManager.java         # Baut die Verbindung zur lokalen SQLite-Datei auf
│   ├── TischDao.java
│   ├── KundeDao.java
│   └── ReservierungDao.java
├── service/                         # Das "Gehirn" der Anwendung (Geschäftslogik)
│   └── ReservierungService.java     # Führt u.a. die Konfliktprüfung vor dem Speichern durch
└── ui/                              # Die Benutzeroberfläche
    └── ConsoleUI.java               # Zeichnet das Text-Menü und verarbeitet Nutzereingaben
