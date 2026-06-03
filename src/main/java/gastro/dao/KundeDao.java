package gastro.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import gastro.model.Kunde;

import java.sql.SQLException;
import java.util.List;

public class KundeDao {

    private final Dao<Kunde, Integer> dao;

    public KundeDao() throws SQLException {
        dao = DaoManager.createDao(DatabaseManager.getInstance().getConnectionSource(), Kunde.class);
    }

    public void erstellen(Kunde kunde) throws SQLException {
        dao.create(kunde);
    }

    public List<Kunde> alleAbrufen() throws SQLException {
        return dao.queryForAll();
    }

    public Kunde nachIdSuchen(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
