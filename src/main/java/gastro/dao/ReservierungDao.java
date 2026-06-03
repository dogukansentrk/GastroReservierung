package gastro.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import gastro.model.Reservierung;
import gastro.model.Tisch;

import java.sql.SQLException;
import java.util.List;

public class ReservierungDao {

    private final Dao<Reservierung, Integer> dao;

    public ReservierungDao() throws SQLException {
        dao = DaoManager.createDao(DatabaseManager.getInstance().getConnectionSource(), Reservierung.class);
    }

    public boolean istTischBelegt(Tisch tisch, String datum, String uhrzeit) throws SQLException {
        QueryBuilder<Reservierung, Integer> qb = dao.queryBuilder();
        qb.where()
                .eq("tisch_id", tisch.getId())
                .and()
                .eq("datum", datum)
                .and()
                .eq("uhrzeit", uhrzeit);
        return qb.countOf() > 0;
    }

    public void erstellen(Reservierung reservierung) throws SQLException {
        dao.create(reservierung);
    }

    public List<Reservierung> alleAbrufen() throws SQLException {
        return dao.queryForAll();
    }

    public Reservierung nachIdSuchen(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
