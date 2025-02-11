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

public class DataManager {

    private static DataManager instance;

    private DataManager() {}

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    Connection getConnection() throws SQLException {
         String url = "jdbc:mysql://localhost:3306/quizdb";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }
    public boolean saveFrage(Frage frage) {
        // SQL query to insert a new Frage
        String sql = "INSERT INTO fragen (frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Establish the connection and prepare the statement
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
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
            stmt.setInt(11, frage.getThema().getId());  // Using Thema ID from the Frage object

            // Execute the insert statement
            int rowsAffected = stmt.executeUpdate();

            // If rowsAffected > 0, the insertion was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // If an error occurs, return false
        }
    }
    
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Load all Fragen with their associated Thema
    public List<Frage> loadAllFragen() {
        List<Frage> fragenList = new ArrayList<>();
        String sql = "SELECT f.id, f.frage, f.a1, f.ap1, f.a2, f.ap2, f.a3, f.ap3, f.a4, f.ap4, f.wahl, f.thema_id, t.bezeichnung AS thema_bezeichnung, k.bezeichnung AS kategorie_bezeichnung " +
                     "FROM fragen f " +
                     "JOIN thema t ON f.thema_id = t.id " +
                     "LEFT JOIN kategorie k ON t.kategorie_id = k.id";
        
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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

                Kategorie kategorie = new Kategorie(0, kategorieBezeichnung); // Dummy ID 0
                Thema thema = new Thema(themaId, themaBezeichnung, kategorie);
                Frage frageObj = new Frage(id, frage, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
                fragenList.add(frageObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fragenList;
    }



    // Load a Frage by its ID
    public Frage loadFrageById(int frageId) {
        String sql = "SELECT f.id, f.frage, f.a1, f.ap1, f.a2, f.ap2, f.a3, f.ap3, f.a4, f.ap4, f.wahl, " +
                     "f.thema_id, t.bezeichnung AS thema_name, k.id AS kategorie_id, k.bezeichnung AS kategorie_name " +
                     "FROM fragen f " +
                     "JOIN thema t ON f.thema_id = t.id " +
                     "JOIN kategorie k ON t.kategorie_id = k.id " +
                     "WHERE f.id = ?";

        Frage frage = null;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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

                 // Create Kategorie first
                    Kategorie kategorie = new Kategorie(kategorieId, kategorieBezeichnung);
                    // Create Thema with the correct Kategorie
                    Thema thema = new Thema(themaId, themaBezeichnung, kategorie);
                                     // Now create the Frage object
                    frage = new Frage(id, frageText, a1, ap1, a2, ap2, a3, ap3, a4, ap4, wahl, thema);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return frage;
    }

///////////////////////////////////////////////////////////////////////////////////////////////
    public List<Kategorie> getCategories() {
        List<Kategorie> categories = new ArrayList<>();
        // SQL to fetch categories from the database
        String sql = "SELECT id, bezeichnung FROM kategorie";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
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
    public Kategorie getCategoryByName(String categoryName) {
        Kategorie category = null;
        // SQL query to select the category based on the name
        String sql = "SELECT id, bezeichnung FROM kategorie WHERE bezeichnung = ?";
        
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the parameter for category name
            stmt.setString(1, categoryName);
            
            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the category details from the ResultSet
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    
                    // Create a new Kategorie object and return it
                    category = new Kategorie(id, bezeichnung);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Return the found category or null if not found
        return category;
    }
    public List<Kategorie> getAllCategories() {
        List<Kategorie> categories = new ArrayList<>();
        String sql = "SELECT id, bezeichnung FROM kategorie";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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

    public List<Thema> getThemaByKategorie(String categoryName) {
        List<Thema> themas = new ArrayList<>();
        String sql = "SELECT t.id, t.bezeichnung FROM thema t " +
                     "JOIN kategorie k ON t.kategorie_id = k.id " +
                     "WHERE k.bezeichnung = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    themas.add(new Thema(id, bezeichnung, null)); // Assuming null for category since it's set later
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return themas;
    }
    public Thema getThemaByName(String themaName) {
        Thema thema = null;
        String sql = "SELECT id, bezeichnung, kategorie_id FROM thema WHERE bezeichnung = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, themaName);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String bezeichnung = rs.getString("bezeichnung");
                    int kategorieId = rs.getInt("kategorie_id");

                    // Create a new Thema object and associate it with the correct Kategorie
                    Kategorie kategorie = getCategoryById(kategorieId); // Fetch Kategorie by ID
                    thema = new Thema(id, bezeichnung, kategorie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thema;
    }

    public Kategorie getCategoryById(int kategorieId) {
        Kategorie category = null;
        String sql = "SELECT id, bezeichnung FROM kategorie WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
