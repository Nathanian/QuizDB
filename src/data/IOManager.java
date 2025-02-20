package data;

import java.util.ArrayList;
import data.entities.Frage;
import data.DataAccessObject; // Sicherstellen, dass DataAccessObject importiert ist

/**
 * Schnittstelle zwischen der Datenzugriffsschicht der Anwendung und der persistenten Datenspeicherung.
 * <p>
 * Diese Schnittstelle definiert grundlegende Operationen zum Speichern, Löschen und Laden von Objekten,
 * insbesondere von {@link Frage}-Objekten.
 * </p>
 */
public interface IOManager {

    /**
     * Speichert eine {@link Frage} in der Datenbank und ordnet sie einer bestimmten Kategorie zu.
     * 
     * @param frage das Frage-Objekt, das gespeichert werden soll.
     * @param categoryId die ID der Kategorie, der die Frage zugeordnet werden soll.
     */
    public void save(Frage frage, int categoryId);
    
    /**
     * Löscht ein Objekt aus der Datenbank.
     * 
     * @param dao das {@link DataAccessObject}, das aus der Datenbank entfernt werden soll.
     */
    public void delete(DataAccessObject dao);
    
    /**
     * Lädt eine {@link Frage} aus der Datenbank anhand ihrer eindeutigen ID.
     * 
     * @param id die Primärschlüssel-ID der Frage in der Datenbank.
     * @return das gefundene {@link Frage}-Objekt oder {@code null}, wenn keine Frage mit der angegebenen ID existiert.
     */
    public Frage loadFrageById(int id);
    
    /**
     * Liefert alle {@link Frage}-Objekte, die in der Datenbank gespeichert sind.
     * 
     * @return eine {@link ArrayList} aller gespeicherten {@link Frage}-Objekte.
     */
    public ArrayList<Frage> getAllFragen();
    
    /**
     * Speichert eine {@link Frage} in der Datenbank.
     * <p>
     * Diese Methode ist eine Überladung von {@link #save(Frage, int)} und speichert die Frage ohne
     * explizite Angabe einer Kategorie-ID.
     * </p>
     * 
     * @param frage das Frage-Objekt, das gespeichert werden soll.
     */
    void save(Frage frage);
}
