package data.entities;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.DataAccessObject;

/**
 * Die Klasse {@code Frage} repräsentiert eine Frage im Quizsystem.
 * <p>
 * Sie erweitert {@link DataAccessObject} und implementiert {@link Serializable},
 * sodass Frage-Objekte in Streams gespeichert und geladen werden können.
 * </p>
 */
public class Frage extends DataAccessObject implements Serializable {

    /**
     * SQL-Template für das INSERT einer Frage in die Datenbank.
     * <p>
     * Platzhalter: <br>
     * %s: Frage-Text, Antworttexte <br>
     * %d: Antwortpunkte, Thema-ID <br>
     * %b: Wahl (boolean)
     * </p>
     */
    public final String SQL_INSERT = "INSERT INTO fragen (frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema_id) " +
                                     "VALUES ('%s', '%s', %d, '%s', %d, '%s', %d, '%s', %d, %b, %d)";

    /**
     * SQL-Template für das UPDATE einer bestehenden Frage in der Datenbank.
     * <p>
     * Platzhalter: <br>
     * %s: Frage-Text, Antworttexte <br>
     * %d: Antwortpunkte, Thema-ID, Frage-ID <br>
     * %b: Wahl (boolean)
     * </p>
     */
    public final String SQL_UPDATE = "UPDATE fragen SET frage = '%s', a1 = '%s', ap1 = %d, a2 = '%s', ap2 = %d, " +
                                     "a3 = '%s', ap3 = %d, a4 = '%s', ap4 = %d, wahl = %b, thema_id = %d WHERE id = %d";

    /**
     * Serial-Version UID zur Sicherstellung der Kompatibilität während der Deserialisierung.
     */
    private static final long serialVersionUID = 1L;

    // Attribute der Frage
    private int id;
    private String text;
    private String a1, a2, a3, a4;
    private int ap1, ap2, ap3, ap4;
    private boolean wahl;
    private Thema thema; 

    /**
     * Konstruktor zum Erzeugen eines {@code Frage}-Objekts mit allen Attributen.
     *
     * @param id     die eindeutige ID der Frage
     * @param text   der Text der Frage
     * @param a1     Antwortmöglichkeit 1
     * @param ap1    Punkte für Antwortmöglichkeit 1
     * @param a2     Antwortmöglichkeit 2
     * @param ap2    Punkte für Antwortmöglichkeit 2
     * @param a3     Antwortmöglichkeit 3
     * @param ap3    Punkte für Antwortmöglichkeit 3
     * @param a4     Antwortmöglichkeit 4
     * @param ap4    Punkte für Antwortmöglichkeit 4
     * @param wahl   gibt an, ob eine Wahlfrage vorliegt
     * @param thema  das {@link Thema}, zu dem die Frage gehört
     */
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

    /**
     * Standardkonstruktor.
     */
    public Frage() {
        // Default-Konstruktor
    }

    /**
     * Gibt den SQL-String für den Speichervorgang dieser Frage zurück.
     * <p>
     * Falls die ID größer als 0 ist, wird davon ausgegangen, dass es sich um ein Update handelt,
     * andernfalls um ein Insert.
     * </p>
     *
     * @return den formatierten SQL-String
     */
    public String getSqlString() {
        if (id > 0) {
            return SQL_UPDATE.formatted(text, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema, id);
        } else {
            return SQL_INSERT.formatted(text, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
        }
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
     * Liefert die ID der Frage.
     *
     * @return die Frage-ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die ID der Frage.
     *
     * @param id die zu setzende ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Liefert den Fragetext.
     *
     * @return der Fragetext
     */
    public String getText() {
        return text;
    }

    /**
     * Setzt den Fragetext.
     *
     * @param text der zu setzende Fragetext
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Liefert die Antwortmöglichkeit 1.
     *
     * @return Antwortmöglichkeit 1
     */
    public String getA1() {
        return a1;
    }

    /**
     * Setzt die Antwortmöglichkeit 1.
     *
     * @param a1 die zu setzende Antwortmöglichkeit 1
     */
    public void setA1(String a1) {
        this.a1 = a1;
    }

    /**
     * Liefert die Antwortmöglichkeit 2.
     *
     * @return Antwortmöglichkeit 2
     */
    public String getA2() {
        return a2;
    }

    /**
     * Setzt die Antwortmöglichkeit 2.
     *
     * @param a2 die zu setzende Antwortmöglichkeit 2
     */
    public void setA2(String a2) {
        this.a2 = a2;
    }

    /**
     * Liefert die Antwortmöglichkeit 3.
     *
     * @return Antwortmöglichkeit 3
     */
    public String getA3() {
        return a3;
    }

    /**
     * Setzt die Antwortmöglichkeit 3.
     *
     * @param a3 die zu setzende Antwortmöglichkeit 3
     */
    public void setA3(String a3) {
        this.a3 = a3;
    }

    /**
     * Liefert die Antwortmöglichkeit 4.
     *
     * @return Antwortmöglichkeit 4
     */
    public String getA4() {
        return a4;
    }

    /**
     * Setzt die Antwortmöglichkeit 4.
     *
     * @param a4 die zu setzende Antwortmöglichkeit 4
     */
    public void setA4(String a4) {
        this.a4 = a4;
    }

    /**
     * Liefert die Punkte für Antwortmöglichkeit 1.
     *
     * @return Punkte für A1
     */
    public int getAp1() {
        return ap1;
    }

    /**
     * Setzt die Punkte für Antwortmöglichkeit 1.
     *
     * @param ap1 die zu setzenden Punkte für A1
     */
    public void setAp1(int ap1) {
        this.ap1 = ap1;
    }

    /**
     * Liefert die Punkte für Antwortmöglichkeit 2.
     *
     * @return Punkte für A2
     */
    public int getAp2() {
        return ap2;
    }

    /**
     * Setzt die Punkte für Antwortmöglichkeit 2.
     *
     * @param ap2 die zu setzenden Punkte für A2
     */
    public void setAp2(int ap2) {
        this.ap2 = ap2;
    }

    /**
     * Liefert die Punkte für Antwortmöglichkeit 3.
     *
     * @return Punkte für A3
     */
    public int getAp3() {
        return ap3;
    }

    /**
     * Setzt die Punkte für Antwortmöglichkeit 3.
     *
     * @param ap3 die zu setzenden Punkte für A3
     */
    public void setAp3(int ap3) {
        this.ap3 = ap3;
    }

    /**
     * Liefert die Punkte für Antwortmöglichkeit 4.
     *
     * @return Punkte für A4
     */
    public int getAp4() {
        return ap4;
    }

    /**
     * Setzt die Punkte für Antwortmöglichkeit 4.
     *
     * @param ap4 die zu setzenden Punkte für A4
     */
    public void setAp4(int ap4) {
        this.ap4 = ap4;
    }

    /**
     * Liefert, ob es sich um eine Wahlfrage handelt.
     *
     * @return {@code true} falls es eine Wahlfrage ist, sonst {@code false}
     */
    public boolean isWahl() {
        return wahl;
    }

    /**
     * Setzt, ob es sich um eine Wahlfrage handelt.
     *
     * @param wahl {@code true} für Wahlfrage, sonst {@code false}
     */
    public void setWahl(boolean wahl) {
        this.wahl = wahl;
    }

    /**
     * Liefert das {@link Thema}, zu dem diese Frage gehört.
     *
     * @return das zugehörige {@link Thema}
     */
    public Thema getThema() {
        return thema;
    }

    /**
     * Setzt das {@link Thema}, zu dem diese Frage gehört.
     *
     * @param thema das zu setzende {@link Thema}
     */
    public void setThema(Thema thema) {
        this.thema = thema;
    }

    /**
     * Liefert den Namen der Datenbanktabelle, in der Fragen gespeichert sind.
     *
     * @return der Tabellenname "fragen"
     */
    public String getTableName() {
        return "fragen";
    }

    /**
     * Setzt die Parameter des übergebenen {@link PreparedStatement} für diese Frage.
     * <p>
     * Diese Methode muss die Platzhalter im SQL-String mit den Attributwerten füllen.
     * Derzeit ist sie nicht implementiert.
     * </p>
     *
     * @param stmt das {@link PreparedStatement}, dessen Parameter gesetzt werden sollen
     * @throws SQLException falls beim Setzen der Parameter ein Fehler auftritt
     */
    @Override
    protected void setPreparedStatementParameters(PreparedStatement stmt) throws SQLException {
        // TODO: Implementierung der Parametersetzung für das PreparedStatement
    }
}
