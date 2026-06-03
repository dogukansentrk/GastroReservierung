package gastro.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import gastro.model.Tisch;

import java.sql.SQLException;
import java.util.List;

public class TischDao {

    private final Dao<Tisch, Integer> dao;

    public TischDao() throws SQLException {
        dao = DaoManager.createDao(DatabaseManager.getInstance().getConnectionSource(), Tisch.class);
    }

    public void erstellen(Tisch tisch) throws SQLException {
        dao.create(tisch);
    }

    public List<Tisch> alleAbrufen() throws SQLException {
        return dao.queryForAll();
    }

    public Tisch nachIdSuchen(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
