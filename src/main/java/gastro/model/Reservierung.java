package gastro.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "reservierungen")
public class Reservierung {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "tisch_id")
    private Tisch tisch;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "kunde_id")
    private Kunde kunde;

    @DatabaseField(canBeNull = false)
    private String datum;

    @DatabaseField(canBeNull = false)
    private String uhrzeit;

    public Reservierung() {}

    public Reservierung(Tisch tisch, Kunde kunde, String datum, String uhrzeit) {
        this.tisch = tisch;
        this.kunde = kunde;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }


    public int getId() { return id; }

    public Tisch getTisch() { return tisch; }
    public void setTisch(Tisch tisch) { this.tisch = tisch; }

    public Kunde getKunde() { return kunde; }
    public void setKunde(Kunde kunde) { this.kunde = kunde; }

    public String getDatum() { return datum; }
    public void setDatum(String datum) { this.datum = datum; }

    public String getUhrzeit() { return uhrzeit; }
    public void setUhrzeit(String uhrzeit) { this.uhrzeit = uhrzeit; }

    @Override
    public String toString() {
        return String.format("[ID: %d] Tisch Nr. %d | Kunde: %s | %s um %s",
                id,
                tisch != null ? tisch.getTischNummer() : -1,
                kunde != null ? kunde.getName() : "?",
                datum,
                uhrzeit);
    }
}
