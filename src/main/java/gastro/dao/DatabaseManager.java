package gastro.dao;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.SqliteDatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import gastro.model.Kunde;
import gastro.model.Reservierung;
import gastro.model.Tisch;

import java.sql.SQLException;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:gastro.db";
    private static DatabaseManager instance;
    private ConnectionSource connectionSource;

    private DatabaseManager() throws SQLException {
        // HIER IST DIE WICHTIGE ÄNDERUNG:
        connectionSource = new JdbcConnectionSource(DATABASE_URL, new SqliteDatabaseType());

        createTablesIfNotExist();
        System.out.println("[DB] Verbindung zur Datenbank hergestellt: gastro.db");
    }

    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    private void createTablesIfNotExist() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Tisch.class);
        TableUtils.createTableIfNotExists(connectionSource, Kunde.class);
        TableUtils.createTableIfNotExists(connectionSource, Reservierung.class);
    }

    public void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (Exception e) {
                System.err.println("[DB] Fehler beim Schließen der Verbindung: " + e.getMessage());
            }
        }
    }
}