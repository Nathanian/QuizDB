/**
 * @author ValentinaTikko
 */
package data;

/**
 * Contains all table names and table column names
 */
public interface DBConstants {

	public static final String TABLE_PERSON = "Person";
	public static final String TABLE_GENDER = "Gender";
	public static final String TABLE_ADDRESS = "Address";

	// Table column names
	public static final String ID = "ID";

	// Table Gender
	public static final String GENDER_NAME = "Name";
	public static final String GENDER_SHORT = "Short";

	// Table Person
	public static final String PERSON_NAME = "Name";
	public static final String PERSON_FIRSTNAME = "Firstname";
	public static final String PERSON_BIRTHDAY = "Birthdate";
	public static final String PERSON_GENDER_ID = "Gender_Id";
	public static final String PERSON_ADDRESS_ID = "Address_Id";

	// Table Address
	public static final String ADDRESS_PLZ = "PLZ";
	public static final String ADDRESS_CITY = "City";
	public static final String ADDRESS_STREET = "Street";
	public static final String ADDRESS_HOUSE = "House";

}
