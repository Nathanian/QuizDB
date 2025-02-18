package data.entities;

import java.io.Serializable;

public class Kategorie implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
	private String bezeichnung;	
	
	
	public Kategorie(int kategorieId, String kategorieBezeichnung) {
		id = kategorieId;
		bezeichnung = kategorieBezeichnung;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
}
