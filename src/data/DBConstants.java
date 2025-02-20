/**
 * Enthält alle Tabellennamen und Spaltennamen der Datenbank.
 * <p>
 * Dieses Interface zentralisiert alle Konstanten, die zur Identifikation von Tabellen
 * und deren Spalten in der Datenbank verwendet werden. Dadurch wird vermieden, dass diese
 * Werte an mehreren Stellen hartkodiert werden müssen.
 * </p>
 * 
 * @author ValentinaTikko
 */
package data;

public interface DBConstants {

    /** Tabellenname für Personen. */
    public static final String TABLE_PERSON = "Person";
    /** Tabellenname für Geschlechter. */
    public static final String TABLE_GENDER = "Gender";
    /** Tabellenname für Adressen. */
    public static final String TABLE_ADDRESS = "Address";

    // Spaltennamen
    /** Allgemeiner Name der Identifikationsspalte. */
    public static final String ID = "ID";

    // Tabelle Gender
    /** Spaltenname für den Namen eines Geschlechts. */
    public static final String GENDER_NAME = "Name";
    /** Spaltenname für die Kurzbezeichnung eines Geschlechts. */
    public static final String GENDER_SHORT = "Short";

    // Tabelle Person
    /** Spaltenname für den Nachnamen einer Person. */
    public static final String PERSON_NAME = "Name";
    /** Spaltenname für den Vornamen einer Person. */
    public static final String PERSON_FIRSTNAME = "Firstname";
    /** Spaltenname für das Geburtsdatum einer Person. */
    public static final String PERSON_BIRTHDAY = "Birthdate";
    /** Spaltenname für den Fremdschlüssel zur Geschlechter-Tabelle. */
    public static final String PERSON_GENDER_ID = "Gender_Id";
    /** Spaltenname für den Fremdschlüssel zur Adressen-Tabelle. */
    public static final String PERSON_ADDRESS_ID = "Address_Id";

    // Tabelle Address
    /** Spaltenname für die Postleitzahl in der Adresse. */
    public static final String ADDRESS_PLZ = "PLZ";
    /** Spaltenname für die Stadt in der Adresse. */
    public static final String ADDRESS_CITY = "City";
    /** Spaltenname für die Straße in der Adresse. */
    public static final String ADDRESS_STREET = "Street";
    /** Spaltenname für die Hausnummer in der Adresse. */
    public static final String ADDRESS_HOUSE = "House";

}
