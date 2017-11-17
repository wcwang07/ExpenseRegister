/**
 * ExpenseContentProvider provides an interface for publishing the data of the ExpenseRegister application.
 * It provides the abstraction for accessing the underlying SQLite database. 
 * 
 * @author      Wei Ching Wang
 * @version     1.0
 * @since       1.0
 */


public class ExpenseContentProvider extends ContentProvider {

	// database helper
	private ExpenseSQLiteHelper dbHelper;

	// Constants for the UriMacher
	private static final int EXPENSE_ALL = 1;
	private static final int EXPENSE_SINGLE = 2;

	// Constants for CONTENT_URI
	private static final String AUTHORITY = "svuca.cs596021.expenseregister.contentprovider";
	private static final String BASE_PATH = "expense";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	// Content types
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.cs596021.expense";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.cs596021.expense";

	// TODO: Create the UriMatcher and initialize it statically
	private static final UriMatcher uriMatcher = null;
	static
	{	
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(CONTENT_URI, EXPENSE_ALL);
		uriMatcher.addURI(CONTENT_URI, EXPENSE_SINGLE);
	}



	/**
	 * Called when the Content Provider is created to initialize the data source.
	 */	
	@Override
	public boolean onCreate() {
		
		// TODO: Initialize the data source
		dbHelper = new ExpenseContentProvider(getContext());

		return true;
	}
	  
	  
	/**
	 * Called to query the data source
	 * 
	 * @param uri
	 * @param projection
	 * @param selection
	 * @param selectionArgs
	 * @param sortOrder
	 */	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		// TODO: Implement the method
		
		SQLiteQueryBuilder db = new SQLiteQueryBuilder();

		db.setTables(expenses;
		try{
			db = dbHelper.getWritableDatabase();
		}
		catch(SQLiteException ex){
			db = dbHelper.getReadableDatatbase();
		}
		switch(uriMatcher.match(uri)){
			case EXPENSE_SINGLE:
				String rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
		}


	}
	  
	  
	/**
	 * Called to return the Content Type
	 * 
	 * @param uri
	 * @throws IllegalArgumentException("Unsupported URI: " + uri)	when the given uri is not supported
	 */	
	@Override
	public String getType(Uri uri) {

		// TODO: Implement the method	
		switch (uriMatcher.match(uri)){
			case EXPENSE_ALL:
				return CONTENT_TYPE;
			case EXPENSE_SINGLE:
		}		return CONTENT_ITEM_TYPE;

	}
	  
	  
	/**
	 * Called to insert to the data source
	 * 
	 * @param uri
	 * @param values
	 * @return Uri of the inserted record
	 */	
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		// TODO: Implement the method
		SQliteDatabase db = dbHelper.getWritableDatabase();

		String nullColumnHack = null;

		// Insert the values into the table
		long id = db.insert(dbHelper.DATABASE_NAME, nullColumnHack, values);

		if(id>-1){
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
		
		getContext().getContentResolver().notifyChange(insertedId, null);

			return insertedId;
		}	
		else{
			return null;
		}

	}
	  
	  
	/**
	 * Called to update the data source
	 * 
	 * @param uri
	 * @param values
	 * @param selection
	 * @param selectionArgs
	 * @return count of updated records
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		// TODO: Implement the method
		SQliteDatabase db = dbHelper.getWritableDatabase();

		//if this is a row URI, limit the deletion to the specified row
		switch(uriMatcher.match(uri)){
			case EXPENSE_SINGLE:
				String rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" + rowID + (!TextUtils.isEmpty(selection)? "AND" ("+selection+')'+");

			default: break; 
		}
		int updateCount = db.update(dbHelper.DATABASE_NAME, values, selection, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);

		return updateCount;

	}
	  
	  
	/**
	 * Called to delete from the data source
	 * 
	 * @param uri
	 * @param selection
	 * @param selectionArgs
	 * @return count of deleted records
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
	
		// TODO: Implement the method

		SQliteDatabase db = dbHelper.getWritableDatabase();

		switch(uriMatcher.match(uri)) {
			case EXPENSE_SINGLE:
				String rowID = uri.getPathSegments().get(1);
				selection = KEY_ID + "=" +rowID + (!TextUtils.isEmpty(selection)? "AND ("+selection+ ')':"");

			default:
				break;
		}
		if(selection == null ){
			selection = "1";
		}

		int deleteCount = db.delete(dbHelper.DATABASE_NAME, selection, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);

		return deleteCount;

	}
	
}
