package data.entities;

import java.io.Serializable;

/**
 * Die Klasse {@code Kategorie} repräsentiert eine Kategorie in der Anwendung.
 * <p>
 * Sie implementiert {@link Serializable}, um die Serialisierung von Kategorie-Objekten zu ermöglichen.
 * </p>
 */
public class Kategorie implements Serializable {
    
    /**
     * Die eindeutige Serialisierungs-ID zur Sicherstellung der Kompatibilität.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Die eindeutige ID der Kategorie.
     */
    private int id;
    
    /**
     * Die Bezeichnung der Kategorie.
     */
    private String bezeichnung;    
    
    /**
     * Konstruktor zum Erzeugen eines {@code Kategorie}-Objekts.
     *
     * @param kategorieId die eindeutige ID der Kategorie
     * @param kategorieBezeichnung die Bezeichnung der Kategorie
     */
    public Kategorie(int kategorieId, String kategorieBezeichnung) {
        id = kategorieId;
        bezeichnung = kategorieBezeichnung;
    }

    /**
     * Gibt die serialVersionUID zurück.
     *
     * @return die serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * Liefert die ID der Kategorie.
     *
     * @return die Kategorie-ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die ID der Kategorie.
     *
     * @param id die zu setzende Kategorie-ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Liefert die Bezeichnung der Kategorie.
     *
     * @return die Kategoriebezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Setzt die Bezeichnung der Kategorie.
     *
     * @param bezeichnung die zu setzende Kategoriebezeichnung
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
