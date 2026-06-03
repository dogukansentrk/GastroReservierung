package gastro.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "tische")
public class Tisch {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(unique = true, canBeNull = false)
    private int tischNummer;

    @DatabaseField(canBeNull = false)
    private int anzahlSitzplaetze;

    @DatabaseField(canBeNull = false)
    private String bereich;

    public Tisch() {}

    public Tisch(int tischNummer, int anzahlSitzplaetze, String bereich) {
        this.tischNummer = tischNummer;
        this.anzahlSitzplaetze = anzahlSitzplaetze;
        this.bereich = bereich;
    }


    public int getId() { return id; }

    public int getTischNummer() { return tischNummer; }
    public void setTischNummer(int tischNummer) { this.tischNummer = tischNummer; }

    public int getAnzahlSitzplaetze() { return anzahlSitzplaetze; }
    public void setAnzahlSitzplaetze(int anzahlSitzplaetze) { this.anzahlSitzplaetze = anzahlSitzplaetze; }

    public String getBereich() { return bereich; }
    public void setBereich(String bereich) { this.bereich = bereich; }

    @Override
    public String toString() {
        return String.format("[ID: %d] Tisch Nr. %d | %d Plätze | Bereich: %s",
                id, tischNummer, anzahlSitzplaetze, bereich);
    }
}
