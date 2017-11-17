/**
 * ExpenseSQLiteHelper is the helper class that extends SQLiteOpenHelper class
 * to implement the best practice pattern for creating, opening, and upgrading
 * SQLite databases.
 * 
 * @author      Wei Ching Wang
 * @version     1.0
 * @since       1.0
 */


public class ExpenseSQLiteHelper extends SQLiteOpenHelper {

	// database names and version
	private static final String DATABASE_NAME = "expenses.db";
	private static final int DATABASE_VERSION = 2;
	
	public ExpenseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	

	/** 
	 * Called when no database exists in disk and the helper class needs
	 * to create a new one. It delegates the table creation to individual
	 * table class (e.g., ExpenseTable).
	 * 
	 * @param database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		ExpenseTable.onCreate(database);
	}

	/** 
	 * Called when there is a database version mismatch to upgrade the database to
	 * the current version. It delegates the table creation to individual
	 * table class (e.g., ExpenseTable).
	 * 
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		ExpenseTable.onUpgrade(database, oldVersion, newVersion);	
	}

}
