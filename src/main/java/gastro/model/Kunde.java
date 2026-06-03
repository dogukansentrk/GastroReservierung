package gastro.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "kunden")
public class Kunde {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String telefonnummer;

    public Kunde() {}

    public Kunde(String name, String telefonnummer) {
        this.name = name;
        this.telefonnummer = telefonnummer;
    }


    public int getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTelefonnummer() { return telefonnummer; }
    public void setTelefonnummer(String telefonnummer) { this.telefonnummer = telefonnummer; }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s | Tel: %s", id, name, telefonnummer);
    }
}
