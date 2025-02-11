package data;

import java.util.ArrayList;

import data.entities.Frage;


/**
 * Schnittstelle zwischen der Datenzugriffschicht der Anwendung und der
 * persistenten Datenspeicherung
 */
public interface IOManager {

	/**
	 * Speichert ein Objekt in die DB
	 * @param dao
	 */
	public void save( Frage frage, int categoryId);
	
	/**
	 * Löscht ein Objekt aus der DB
	 * @param dao
	 */
	
	public void delete(DataAccessObject dao);
	
	/**
	 * Lädt ein Objekt Person aus der DB
	 * @param id PK in der Tabelle
	 * @return
	 */
	public Frage loadFrageById(int id);
	
	public ArrayList<Frage> getAllFragen();
	
	

	void save(Frage frage);

}
