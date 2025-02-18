package data.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.DataAccessObject;

public class Frage extends DataAccessObject implements Serializable{

    public final String SQL_INSERT = "INSERT INTO fragen (frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema_id) " +
                                     "VALUES ('%s', '%s', %d, '%s', %d, '%s', %d, '%s', %d, %b, %d)";
    
    public final String SQL_UPDATE = "UPDATE fragen SET frage = '%s', a1 = '%s', ap1 = %d, a2 = '%s', ap2 = %d, " +
                                     "a3 = '%s', ap3 = %d, a4 = '%s', ap4 = %d, wahl = %b, thema_id = %d WHERE id = %d";
    private static final long serialVersionUID = 1L; // Ensure compatibility

    private int id;
    private String text;
    private String a1, a2, a3, a4;
    private int ap1, ap2, ap3, ap4;
    private boolean wahl;
    private Thema thema; 
    
    
    public Frage(int id, String text, String a1, int ap1, String a2, int ap2, String a3, int ap3, String a4, int ap4, boolean wahl, Thema thema) {
        this.id = id;
        this.text = text;
        this.a1 = a1;
        this.ap1 = ap1;
        this.a2 = a2;
        this.ap2 = ap2;
        this.a3 = a3;
        this.ap3 = ap3;
        this.a4 = a4;
        this.ap4 = ap4;
        this.wahl = wahl;
        this.thema = thema;
    }

    public String getSqlString() {
        if (id > 0) {
            return SQL_UPDATE.formatted(text, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema, id);
        } else {
            return SQL_INSERT.formatted(text, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
        }
    }

    public Frage() {
        // Default constructor
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public int getAp1() {
        return ap1;
    }

    public void setAp1(int ap1) {
        this.ap1 = ap1;
    }

    public int getAp2() {
        return ap2;
    }

    public void setAp2(int ap2) {
        this.ap2 = ap2;
    }

    public int getAp3() {
        return ap3;
    }

    public void setAp3(int ap3) {
        this.ap3 = ap3;
    }

    public int getAp4() {
        return ap4;
    }

    public void setAp4(int ap4) {
        this.ap4 = ap4;
    }

    public boolean isWahl() {
        return wahl;
    }

    public void setWahl(boolean wahl) {
        this.wahl = wahl;
    } 


    public Thema getThema() {
 		return thema;
	}

	public void setThema(Thema thema) {
		this.thema = thema;
	}

	public String getTableName() {
        return "fragen";
    }

	@Override
	protected void setPreparedStatementParameters(PreparedStatement stmt) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
