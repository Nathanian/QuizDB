package data.entities;

public class Kategorie {
	private int id;
	private String bezeichnung;	
	
	
	public Kategorie(int kategorieId, String kategorieBezeichnung) {
		id = kategorieId;
		bezeichnung = kategorieBezeichnung;
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
