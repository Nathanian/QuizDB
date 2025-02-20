package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Abstrakte Basisklasse für Data Access Objects (DAO), die allgemeine Funktionalität
 * zum Speichern von Objekten in der Datenbank bereitstellt.
 * <p>
 * Klassen, die diese abstrakte Klasse erweitern (z.B. {@code Frage}), müssen
 * die abstrakten Methoden implementieren, um die entsprechenden SQL-Strings, 
 * Tabellennamen und die Parameter für Prepared Statements zu definieren.
 * </p>
 */
public abstract class DataAccessObject implements DBConstants {

    /**
     * Die eindeutige ID des Objekts.
     */
    protected int id;

    /**
     * Speichert das aktuelle Objekt in der Datenbank.
     * <p>
     * Diese Methode ermittelt den SQL-String über {@link #getSqlString()}, 
     * holt eine Verbindung über den {@code DataManager}, setzt die Parameter 
     * im PreparedStatement über {@link #setPreparedStatementParameters(PreparedStatement)} 
     * und führt das Update aus. Bei Erfolg wird eine Bestätigung in der Konsole ausgegeben.
     * </p>
     *
     * @throws RuntimeException wenn ein {@link SQLException} während des Speichervorgangs auftritt.
     */
    public void save() {
        String sql = getSqlString(); // SQL-String, der vom konkreten Objekt bereitgestellt wird

        try (Connection conn = DataManager.getInstance().getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Setzt die Parameter für das PreparedStatement dynamisch
            setPreparedStatementParameters(stmt); 

            // Führt das SQL-Update aus
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Object erfolgreich gespeichert!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving object", e);
        }
    }

    /**
     * Liefert den SQL-String, der zum Speichern des Objekts verwendet wird.
     * <p>
     * Diese Methode muss von den Unterklassen implementiert werden, um den passenden
     * SQL INSERT- oder UPDATE-String zurückzugeben.
     * </p>
     *
     * @return der SQL-Query-String.
     */
    public abstract String getSqlString();

    /**
     * Liefert den Namen der Datenbanktabelle, in der das Objekt gespeichert wird.
     *
     * @return der Tabellenname.
     */
    public abstract String getTableName();

    /**
     * Setzt die Parameter des übergebenen {@link PreparedStatement} für den Speichervorgang.
     * <p>
     * Diese Methode muss von den Unterklassen implementiert werden, um die Werte für
     * die Platzhalter im SQL-String, der von {@link #getSqlString()} zurückgegeben wird, zu setzen.
     * </p>
     *
     * @param stmt das PreparedStatement, dessen Parameter gesetzt werden sollen.
     * @throws SQLException wenn ein Fehler beim Setzen der Parameter auftritt.
     */
    protected abstract void setPreparedStatementParameters(PreparedStatement stmt) throws SQLException;

    /**
     * Gibt die eindeutige ID des Objekts zurück.
     *
     * @return die Objekt-ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die eindeutige ID des Objekts.
     *
     * @param id die zu setzende ID.
     */
    public void setId(int id) {
        this.id = id;
    }
}
