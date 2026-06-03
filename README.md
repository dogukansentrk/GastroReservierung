# Gastro-Reservierungssystem

Ein digitales Backend-System zur Verwaltung von Tischen, Kunden und Reservierungen
für Gastronomiebetriebe – realisiert als Java-Konsolenanwendung (CLI) mit
ORMLite (ORM) und SQLite (Datenbank).

---

## Projektstruktur

```
GastroReservierung/
├── pom.xml                          ← Maven-Konfiguration & Dependencies
└── src/main/java/gastro/
    ├── Main.java                    ← Einstiegspunkt
    ├── model/
    │   ├── Tisch.java               ← Entität: Tisch
    │   ├── Kunde.java               ← Entität: Kunde
    │   └── Reservierung.java        ← Entität: Reservierung (FK auf Tisch & Kunde)
    ├── dao/
    │   ├── DatabaseManager.java     ← Singleton: DB-Verbindung & Tabellenerstellung
    │   ├── TischDao.java            ← Datenzugriff für Tische
    │   ├── KundeDao.java            ← Datenzugriff für Kunden
    │   └── ReservierungDao.java     ← Datenzugriff + Konfliktprüfung
    ├── service/
    │   └── ReservierungService.java ← Geschäftslogik (verbindet DAOs)
    └── ui/
        └── ConsoleUI.java          ← CLI-Menü, Benutzereingabe, Fehlerbehandlung
```

---

## Voraussetzungen

- Java JDK 11 oder höher
- Maven 3.6 oder höher
- Internetverbindung beim ersten Build (Maven lädt Dependencies)

---

## Build & Start

### 1. Projekt bauen
```bash
mvn package
```
→ Erstellt `target/gastro-reservierung-1.0.0-jar-with-dependencies.jar`

### 2. Anwendung starten
```bash
java -jar target/gastro-reservierung-1.0.0-jar-with-dependencies.jar
```

Die SQLite-Datenbank `gastro.db` wird automatisch im aktuellen Verzeichnis erstellt.

---

## Funktionsübersicht

| Menüpunkt | Funktion |
|---|---|
| 1 – Tisch anlegen | Tischnummer, Sitzplätze, Bereich eingeben |
| 2 – Alle Tische | Liste aller angelegten Tische |
| 3 – Kunden anlegen | Name und Telefonnummer eingeben |
| 4 – Alle Kunden | Liste aller registrierten Kunden |
| 5 – Reservierung | Tisch + Kunde + Datum + Uhrzeit; mit Konfliktprüfung |
| 6 – Alle Reservierungen | Vollständige Übersicht |
| 0 – Beenden | Programm sauber beenden |

---

## Technische Entscheidungen

### Warum ORMLite?
ORMLite (Object Relational Mapping Lite) erlaubt es, Java-Objekte direkt
auf Datenbanktabellen zu mappen. Tabellen werden automatisch aus den
`@DatabaseTable`- und `@DatabaseField`-Annotationen generiert. SQL-Queries
werden durch DAOs (Data Access Objects) vollständig abstrahiert.

### Konfliktprüfung (Doppelbuchungs-Schutz)
Bevor eine Reservierung gespeichert wird, prüft `ReservierungDao.istTischBelegt()`
via `QueryBuilder`, ob für denselben Tisch, dasselbe Datum und dieselbe Uhrzeit
bereits ein Eintrag existiert. Bei einem Treffer wird die Buchung mit einer
`IllegalStateException` abgelehnt – ohne Absturz, mit klarer Fehlermeldung.

### Robustheit
Alle Integer-Eingaben laufen durch `eingabeInt()` in der `ConsoleUI`.
Diese Methode fängt `NumberFormatException` ab und fragt erneut,
statt das Programm abstürzen zu lassen.
