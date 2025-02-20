package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.entities.Frage;
import data.entities.Kategorie;
import data.entities.Thema;

/**
 * DataManager ist eine Singleton-Klasse, die für die Verwaltung von Datenbankverbindungen
 * sowie für CRUD-Operationen (Erstellen, Lesen, Aktualisieren, Löschen) auf den Entitäten
 * {@link Frage}, {@link Kategorie} und {@link Thema} zuständig ist.
 * <p>
 * Die Klasse verwendet JDBC, um mit einer MySQL-Datenbank (quizdb) zu kommunizieren.
 * </p>
 */
public class DataManager {

    /**
     * Die Singleton-Instanz von DataManager.
     */
    private static DataManager instance;

    /**
     * Privater Konstruktor, um die direkte Instanziierung von außerhalb zu verhindern.
     */
    private DataManager() {}

    /**
     * Liefert die Singleton-Instanz von DataManager.
     *
     * @return die Instanz von DataManager.
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * Stellt eine Verbindung zur Datenbank her.
     *
     * @return ein {@link Connection}-Objekt, das mit der Datenbank verbunden ist.
     * @throws SQLException falls beim Herstellen der Verbindung ein Fehler auftritt.
     */
    Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quizdb";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Speichert ein {@link Frage}-Objekt in der Datenbank.
     * <p>
     * Dabei wird ein INSERT in die Tabelle <em>fragen</em> ausgeführt, wobei die Werte aus dem übergebenen
     * {@link Frage}-Objekt entnommen werden.
     * </p>
     *
     * @param frage das Frage-Objekt, das gespeichert werden soll.
     * @return {@code true}, wenn das Speichern erfolgreich war, ansonsten {@code false}.
     */
    public boolean saveFrage(Frage frage) {
        String sql = "INSERT INTO fragen (frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, frage.getText());
            stmt.setString(2, frage.getA1());
            stmt.setInt(3, frage.getAp1());
            stmt.setString(4, frage.getA2());
            stmt.setInt(5, frage.getAp2());
            stmt.setString(6, frage.getA3());
            stmt.setInt(7, frage.getAp3());
            stmt.setString(8, frage.getA4());
            stmt.setInt(9, frage.getAp4());
            stmt.setBoolean(10, frage.isWahl());
            stmt.setInt(11, frage.getThema().getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lädt alle {@link Frage}-Objekte aus der Datenbank inklusive des zugehörigen {@link Thema}
     * und dessen {@link Kategorie}.
     *
     * @return eine Liste mit allen {@link Frage}-Objekten.
     */
    public List<Frage> loadAllFragen() {
        List<Frage> fragenList = new ArrayList<>();
        String sql = "SELECT f.id, f.frage, f.a1, f.ap1, f.a2, f.ap2, f.a3, f.ap3, f.a4, f.ap4, f.wahl, f.thema_id, " +
                     "t.bezeichnung AS thema_bezeichnung, k.bezeichnung AS kategorie_bezeichnung " +
                     "FROM fragen f " +
                     "JOIN thema t ON f.thema_id = t.id " +
                     "LEFT JOIN kategorie k ON t.kategorie_id = k.id";

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String frage = rs.getString("frage");
                String a1 = rs.getString("a1");
                int ap1 = rs.getInt("ap1");
                String a2 = rs.getString("a2");
                int ap2 = rs.getInt("ap2");
                String a3 = rs.getString("a3");
                int ap3 = rs.getInt("ap3");
                String a4 = rs.getString("a4");
                int ap4 = rs.getInt("ap4");
                boolean wahl = rs.getBoolean("wahl");
                int themaId = rs.getInt("thema_id");
                String themaBezeichnung = rs.getString("thema_bezeichnung");
                String kategorieBezeichnung = rs.getString("kategorie_bezeichnung");

                // Hier wird für Kategorie ein Dummy-Objekt erzeugt, da die ID nicht aus der Abfrage stammt.
                Kategorie kategorie = new Kategorie(0, kategorieBezeichnung);
                Thema thema = new Thema(themaId, themaBezeichnung, kategorie);
                Frage frageObj = new Frage(id, frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
                fragenList.add(frageObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fragenList;
    }

    /**
     * Lädt ein einzelnes {@link Frage}-Objekt anhand seiner ID.
     *
     * @param frageId die ID der Frage, die geladen werden soll.
     * @return das {@link Frage}-Objekt, wenn gefunden, andernfalls {@code null}.
     */
    public Frage loadFrageById(int frageId) {
        String sql = "SELECT f.id, f.frage, f.a1, f.ap1, f.a2, f.ap2, f.a3, f.ap3, f.a4, f.ap4, f.wahl, " +
                     "f.thema_id, t.bezeichnung AS thema_name, k.id AS kategorie_id, k.bezeichnung AS kategorie_name " +
                     "FROM fragen f " +
                     "JOIN thema t ON f.thema_id = t.id " +
                     "JOIN kategorie k ON t.kategorie_id = k.id " +
                     "WHERE f.id = ?";

        Frage frage = null;
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, frageId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String frageText = rs.getString("frage");
                    String a1 = rs.getString("a1");
                    int ap1 = rs.getInt("ap1");
                    String a2 = rs.getString("a2");
                    int ap2 = rs.getInt("ap2");
                    String a3 = rs.getString("a3");
                    int ap3 = rs.getInt("ap3");
                    String a4 = rs.getString("a4");
                    int ap4 = rs.getInt("ap4");
                    boolean wahl = rs.getBoolean("wahl");
                    int themaId = rs.getInt("thema_id");
                    String themaBezeichnung = rs.getString("thema_name");
                    int kategorieId = rs.getInt("kategorie_id");
                    String kategorieBezeichnung = rs.getString("kategorie_name");

                    Kategorie kategorie = new Kategorie(kategorieId, kategorieBezeichnung);
                    Thema thema = new Thema(themaId, themaBezeichnung, kategorie);
                    frage = new Frage(id, frageText, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frage;
    }

    /**
     * Ruft alle {@link Kategorie}-Objekte aus der Datenbank ab.
     *
     * @return eine Liste von {@link Kategorie}-Objekten.
     */
    public List<Kategorie> getCategories() {
        List<Kategorie> categories = new ArrayList<>();
        String sql = "SELECT id, bezeichnung FROM kategorie";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String bezeichnung = rs.getString("bezeichnung");
                categories.add(new Kategorie(id, bezeichnung));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Ruft ein {@link Kategorie}-Objekt anhand des Kategorienamens aus der Datenbank ab.
     *
     * @param categoryName der Name der Kategorie.
     * @return das gefundene {@link Kategorie}-Objekt oder {@code null}, wenn keine Übereinstimmung gefunden wurde.
     */
    public Kategorie getCategoryByName(String categoryName) {
        Kategorie category = null;
        String sql = "SELECT id, bezeichnung FROM kategorie WHERE bezeichnung = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    category = new Kategorie(id, bezeichnung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Ruft alle {@link Kategorie}-Objekte aus der Datenbank ab.
     *
     * @return eine Liste aller {@link Kategorie}-Objekte.
     */
    public List<Kategorie> getAllCategories() {
        List<Kategorie> categories = new ArrayList<>();
        String sql = "SELECT id, bezeichnung FROM kategorie";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    categories.add(new Kategorie(id, bezeichnung));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Ruft alle {@link Thema}-Objekte ab, die zu einer bestimmten Kategorie gehören.
     *
     * @param categoryName der Name der Kategorie.
     * @return eine Liste von {@link Thema}-Objekten, die der angegebenen Kategorie zugeordnet sind.
     */
    public List<Thema> getThemaByKategorie(String categoryName) {
        List<Thema> themas = new ArrayList<>();
        String sql = "SELECT t.id, t.bezeichnung FROM thema t " +
                     "JOIN kategorie k ON t.kategorie_id = k.id " +
                     "WHERE k.bezeichnung = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    themas.add(new Thema(id, bezeichnung, null)); // Kategorie wird hier ggf. später gesetzt
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themas;
    }

    /**
     * Ruft ein {@link Thema}-Objekt anhand des Themennamens aus der Datenbank ab.
     *
     * @param themaName der Name des Themas.
     * @return das {@link Thema}-Objekt, wenn gefunden, andernfalls {@code null}.
     */
    public Thema getThemaByName(String themaName) {
        Thema thema = null;
        String sql = "SELECT id, bezeichnung, kategorie_id FROM thema WHERE bezeichnung = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, themaName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    int kategorieId = rs.getInt("kategorie_id");

                    // Hole die zugehörige Kategorie anhand der ID
                    Kategorie kategorie = getCategoryById(kategorieId);
                    thema = new Thema(id, bezeichnung, kategorie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thema;
    }

    /**
     * Ruft ein {@link Kategorie}-Objekt anhand seiner ID aus der Datenbank ab.
     *
     * @param kategorieId die ID der Kategorie.
     * @return das {@link Kategorie}-Objekt, wenn gefunden, andernfalls {@code null}.
     */
    public Kategorie getCategoryById(int kategorieId) {
        Kategorie category = null;
        String sql = "SELECT id, bezeichnung FROM kategorie WHERE id = ?";

        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kategorieId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String bezeichnung = rs.getString("bezeichnung");
                    category = new Kategorie(kategorieId, bezeichnung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
