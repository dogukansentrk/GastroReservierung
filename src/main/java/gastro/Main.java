package gastro;

import gastro.dao.DatabaseManager;
import gastro.service.ReservierungService;
import gastro.ui.ConsoleUI;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");
        try {
            DatabaseManager.getInstance();

            ReservierungService service = new ReservierungService();
            ConsoleUI ui = new ConsoleUI(service);

            ui.starten();

        } catch (SQLException e) {
            System.err.println("Datenbankverbindung fehlgeschlagen: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                DatabaseManager.getInstance().close();
            } catch (SQLException e) {
            }
        }
    }
}
