package gastro.service;

import gastro.dao.KundeDao;
import gastro.dao.ReservierungDao;
import gastro.dao.TischDao;
import gastro.model.Kunde;
import gastro.model.Reservierung;
import gastro.model.Tisch;

import java.sql.SQLException;
import java.util.List;


public class ReservierungService {

    private final TischDao tischDao;
    private final KundeDao kundeDao;
    private final ReservierungDao reservierungDao;

    public ReservierungService() throws SQLException {
        this.tischDao = new TischDao();
        this.kundeDao = new KundeDao();
        this.reservierungDao = new ReservierungDao();
    }

    public void tischAnlegen(int tischNummer, int sitzplaetze, String bereich) throws SQLException {
        Tisch tisch = new Tisch(tischNummer, sitzplaetze, bereich);
        tischDao.erstellen(tisch);
    }

    public List<Tisch> alleTische() throws SQLException {
        return tischDao.alleAbrufen();
    }

    public Tisch tischNachId(int id) throws SQLException {
        return tischDao.nachIdSuchen(id);
    }

    public void kundeAnlegen(String name, String telefon) throws SQLException {
        Kunde kunde = new Kunde(name, telefon);
        kundeDao.erstellen(kunde);
    }

    public List<Kunde> alleKunden() throws SQLException {
        return kundeDao.alleAbrufen();
    }

    public Kunde kundeNachId(int id) throws SQLException {
        return kundeDao.nachIdSuchen(id);
    }

    public void reservierungErstellen(int tischId, int kundeId, String datum, String uhrzeit)
            throws SQLException {

        Tisch tisch = tischDao.nachIdSuchen(tischId);
        if (tisch == null) {
            throw new IllegalArgumentException("Tisch mit ID " + tischId + " nicht gefunden.");
        }

        Kunde kunde = kundeDao.nachIdSuchen(kundeId);
        if (kunde == null) {
            throw new IllegalArgumentException("Kunde mit ID " + kundeId + " nicht gefunden.");
        }

        if (reservierungDao.istTischBelegt(tisch, datum, uhrzeit)) {
            throw new IllegalStateException(
                    "Tisch Nr. " + tisch.getTischNummer() +
                    " ist am " + datum + " um " + uhrzeit + " bereits belegt. Buchung abgelehnt."
            );
        }

        Reservierung reservierung = new Reservierung(tisch, kunde, datum, uhrzeit);
        reservierungDao.erstellen(reservierung);
    }

    public List<Reservierung> alleReservierungen() throws SQLException {
        return reservierungDao.alleAbrufen();
    }
}
