package data.entities;

public class Thema {
    private int id;
    private String bezeichnung;
    private Kategorie kategorie; // ✅ Ensure Kategorie is here

    public Thema(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    public Thema(int id, String bezeichnung, Kategorie kategorie) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.kategorie = kategorie;
    }

    public int getId() { return id; }

    public String getBezeichnung() { return bezeichnung; }

    public Kategorie getKategorie() { 
                return kategorie; 
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }
}
