package data.entities;

import java.io.Serializable;

/**
 * Die Klasse {@code Thema} repräsentiert ein Thema im System.
 * <p>
 * Ein Thema besitzt eine eindeutige ID, eine Bezeichnung und optional eine zugeordnete
 * {@link Kategorie}. Diese Klasse implementiert {@link Serializable} zur Unterstützung
 * der Objektserialisierung.
 * </p>
 */
public class Thema implements Serializable {
    
    /**
     * Die eindeutige Serialisierungs-ID zur Sicherstellung der Kompatibilität.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Die eindeutige ID des Themas.
     */
    private int id;

    /**
     * Die Bezeichnung des Themas.
     */
    private String bezeichnung;

    /**
     * Die zugeordnete Kategorie des Themas.
     */
    private Kategorie kategorie; // ✅ Ensure Kategorie is here

    /**
     * Konstruktor zum Erzeugen eines {@code Thema}-Objekts ohne zugeordnete Kategorie.
     *
     * @param id die eindeutige ID des Themas
     * @param bezeichnung die Bezeichnung des Themas
     */
    public Thema(int id, String bezeichnung) {
        this.id = id;
        this.bezeichnung = bezeichnung;
    }

    /**
     * Konstruktor zum Erzeugen eines {@code Thema}-Objekts mit einer zugeordneten Kategorie.
     *
     * @param id die eindeutige ID des Themas
     * @param bezeichnung die Bezeichnung des Themas
     * @param kategorie die {@link Kategorie}, der das Thema zugeordnet ist
     */
    public Thema(int id, String bezeichnung, Kategorie kategorie) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.kategorie = kategorie;
    }

    /**
     * Liefert die ID des Themas.
     *
     * @return die eindeutige ID des Themas
     */
    public int getId() {
        return id;
    }

    /**
     * Liefert die Bezeichnung des Themas.
     *
     * @return die Bezeichnung des Themas
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Liefert die zugeordnete {@link Kategorie} des Themas.
     *
     * @return die Kategorie, der das Thema zugeordnet ist, oder {@code null}, falls keine zugeordnet ist
     */
    public Kategorie getKategorie() {
        return kategorie;
    }

    /**
     * Setzt die {@link Kategorie} für dieses Thema.
     *
     * @param kategorie die Kategorie, die diesem Thema zugeordnet werden soll
     */
    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }
}
