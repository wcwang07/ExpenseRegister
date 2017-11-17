/**
 * ExpenseTable is the helper class that represents the table "expense"
 * in the ExpenseRegister application.
 * <p>
 * This class is used by ExpenseSQLiteHelper class to create and upgrade
 * the "expense" table whenever necessary.
 * 
 * @author      Wei Ching Wang
 * @version     1.0
 * @since       1.0
 */


public class ExpenseTable {

	  // Database table and its columns
	  public static final String TABLE_EXPENSE = "expense";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_DESCRIPTION = "description";
	  public static final String COLUMN_AMOUNT = "amount";	  
	  public static final String COLUMN_MEMO = "memo";

	  // Table creation SQL statement
	  private static final String TABLE_CREATE = "create table " 
	      + TABLE_EXPENSE
	      + "(" 
	      + COLUMN_ID + " integer primary key autoincrement, " 
	      + COLUMN_DESCRIPTION + " text not null," 
	      + COLUMN_AMOUNT + " double not null, " 
	      + COLUMN_MEMO + " text " 
	      + ");";
	  
	  
	  /**
	   * Called when no database exists in disk and the table needs to be created
	   * 
	   * @param database
	   */
	  public static void onCreate(SQLiteDatabase database) {

		  // TODO: Implement the method
	  	  database.db.execSQL(TABLE_CREATE);

	  }

	  
	  /**
	   * Called when there is a database version mismatch to upgrade the table to the latest version
	   * 
	   * @param database
	   * @param oldVersion
	   * @param newVersion
	   */
	  public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		  Log.w(ExpenseTable.class.getName(), "Upgrading database from version "
				  + oldVersion + " to " + newVersion
				  + ", which will destroy all old data");

		  // TODO: Implement the method
		  database.execSQL("DROP TABLE IF EXISTS TABLE_EXPENSE");
		  onCreate(database);

	  }
	
}
