package gastro.ui;

import gastro.model.Kunde;
import gastro.model.Reservierung;
import gastro.model.Tisch;
import gastro.service.ReservierungService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class ConsoleUI {

    private final ReservierungService service;
    private final Scanner scanner;

    public ConsoleUI(ReservierungService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void starten() {
        System.out.println("Willkommen im Gastro-Reservierungssystem  ");

        boolean laufen = true;
        while (laufen) {
            hauptmenuAnzeigen();
            int auswahl = eingabeInt("Ihre Auswahl: ");

            switch (auswahl) {
                case 1:
                    tischAnlegen();
                    break;
                case 2:
                    alleTischeAnzeigen();
                    break;
                case 3:
                    kundeAnlegen();
                    break;
                case 4:
                    alleKundenAnzeigen();
                    break;
                case 5:
                    reservierungErstellen();
                    break;
                case 6:
                    alleReservierungenAnzeigen();
                    break;
                case 0:
                    System.out.println("\nAuf Wiedersehen!");
                    laufen = false;
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }


    private void hauptmenuAnzeigen() {
        System.out.println("  HAUPTMENÜ");
        System.out.println("   1 Tisch anlegen");
        System.out.println("   2 Alle Tische anzeigen");
        System.out.println("   3 Kunden anlegen");
        System.out.println("   4 Alle Kunden anzeigen");
        System.out.println("   5 Reservierung erstellen");
        System.out.println("   6 Alle Reservierungen anzeigen");
        System.out.println("   0 Beenden");
    }


    private void tischAnlegen() {
        System.out.println("\n Neuen Tisch anlegen");
        int nummer = eingabeInt("Tischnummer: ");
        int sitze = eingabeInt("Anzahl Sitzplätze: ");
        System.out.print("Bereich (z.B. Innenbereich / Terrasse): ");
        String bereich = scanner.nextLine().trim();

        if (bereich.isEmpty()) {
            System.out.println("Bereich darf nicht leer sein.");
            return;
        }

        try {
            service.tischAnlegen(nummer, sitze, bereich);
            System.out.println("Tisch Nr. " + nummer + " erfolgreich angelegt.");
        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void alleTischeAnzeigen() {
        System.out.println("\n Alle Tische");
        try {
            List<Tisch> tische = service.alleTische();
            if (tische.isEmpty()) {
                System.out.println("(Keine Tische vorhanden)");
            } else {
                tische.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }


    private void kundeAnlegen() {
        System.out.println("\n Neuen Kunden anlegen");
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Telefonnummer: ");
        String telefon = scanner.nextLine().trim();

        if (name.isEmpty() || telefon.isEmpty()) {
            System.out.println("Name und Telefonnummer dürfen nicht leer sein");
            return;
        }

        try {
            service.kundeAnlegen(name, telefon);
            System.out.println("Kunde \"" + name + "\" erfolgreich angelegt.");
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private void alleKundenAnzeigen() {
        System.out.println("\n Alle Kunden");
        try {
            List<Kunde> kunden = service.alleKunden();
            if (kunden.isEmpty()) {
                System.out.println("(Keine Kunden vorhanden)");
            } else {
                kunden.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private void reservierungErstellen() {
        System.out.println("\n Neue Reservierung erstellen");

        try {
            System.out.println("  Verfügbare Tische:");
            service.alleTische().forEach(t -> System.out.println("    " + t));
            System.out.println("  Registrierte Kunden:");
            service.alleKunden().forEach(k -> System.out.println("    " + k));
        } catch (SQLException e) {
            System.out.println("Datenbankfehler beim Laden der Übersicht.");
            return;
        }

        int tischId = eingabeInt("Tisch-ID: ");
        int kundeId = eingabeInt("Kunden-ID: ");

        System.out.print("Datum (YYYY-MM-DD, z.B. 2025-07-15): ");
        String datum = scanner.nextLine().trim();

        System.out.print("Uhrzeit (HH:MM, z.B. 19:00): ");
        String uhrzeit = scanner.nextLine().trim();

        if (!datum.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Ungültiges Datumsformat. Bitte YYYY-MM-DD verwenden.");
            return;
        }
        if (!uhrzeit.matches("\\d{2}:\\d{2}")) {
            System.out.println("Ungültiges Uhrzeitformat. Bitte HH:MM verwenden.");
            return;
        }

        try {
            service.reservierungErstellen(tischId, kundeId, datum, uhrzeit);
            System.out.println("Reservierung erfolgreich gespeichert.");
        } catch (IllegalStateException e) {
            System.out.println("Konflikt: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Eingabefehler: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private void alleReservierungenAnzeigen() {
        System.out.println("\n Alle Reservierungen");
        try {
            List<Reservierung> reservierungen = service.alleReservierungen();
            if (reservierungen.isEmpty()) {
                System.out.println("(Keine Reservierungen vorhanden)");
            } else {
                reservierungen.forEach(System.out::println);
            }
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private int eingabeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String zeile = scanner.nextLine().trim();
            try {
                return Integer.parseInt(zeile);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine ganze Zahl eingeben.");
            }
        }
    }
}