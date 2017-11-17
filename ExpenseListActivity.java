/**
 * ExpenseListActivity is the main Activity class of the ExpenseRegister application.
 * It displays the list of all expense records and the total of all expenses. This class
 * extends the ListActivity class and implements the LoaderCallbacks<Cursor> interface.
 * 
 * @author      Wei Ching Wang
 * @version     1.0
 * @since       1.0
 */


public class ExpenseListActivity extends ListActivity implements LoaderCallbacks<Cursor>  {

	// ID for the Cursor Loader
	private static final int LOADER_ID = 0;
	
	// SimpleCursorAdapter for the list view
	private SimpleCursorAdapter adapter;

	
	/**
	 * Called at the start of the Activity life cycle
	 * 
	 * @param savedInstanceState
	 */	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // TODO: inflate the UI
        setContentView(R.layout.row_expense); 
        
        // use Cursor Loader to populate the list view through the SimpleCursorAdapter
        String[] from = { ExpenseTable.COLUMN_DESCRIPTION, ExpenseTable.COLUMN_AMOUNT };
        int[] to = { R.id.textViewDescription, R.id.textViewAmount };
        getLoaderManager().initLoader(LOADER_ID, null, this);
        adapter = new SimpleCursorAdapter(ExpenseListActivity.this, R.layout.row_expense, null, from, to, 0);
        
        // TODO: bind the adapter to the view
        setListAdapter(adapter);

    }


	/**
	 * Called when the loader is initialized, it creates and return new Cursor Loader object.
	 * 
	 * @param id
	 * @param bundle
	 */		
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

		// TODO: return a new Cursor Loader that will return all expense records 
		// sorted alphabetically according to the "description" column
		return new CursorLoader(ExpenseListActivity.this, ExpenseContentProvider.CONTENT_URI,
        from, null, null, ExpenseTable.COLUMN_DESCRIPTION+" ASC");

	}


	/**
	 * Called when the Loader Manager has completed the asynchronous query.
	 * 
	 * @param loader
	 * @param cursor
	 */	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);		
		updateTotalView(cursor);
	}


	/**
	 * Called when the Loader Manager resets the Cursor Loader.
	 * 
	 * @param loader
	 */	
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);	
		updateTotalView(null);
	}


	/**
	 * Called when the "Add" button is clicked. It takes the user to 
	 * the window to enter details for the new expense record.
	 * 
	 * @param view
	 */	
	public void onAddExpenseClick(View view) {
		
		// TODO: Handle the "Add" button click
		Intent intent = new Intent(view.getContext(), ExpenseDetailsActivity.class);
        startActivity(intent);

	}    
	

	/**
	 * Called when the a expense item is clicked. It takes the user to 
	 * the window to edit the details for the clicked expense record.
	 * 
	 * @param listView
	 * @param view
	 * @param position
	 * @param id
	 */	
	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {

		// The Uri object to be passed to the details window
		// Use "ExpenseContentProvider.CONTENT_ITEM_TYPE" as the key to pass this Uri object
	    Uri uri = Uri.parse(ExpenseContentProvider.CONTENT_URI + "/" + id);
		
		// TODO: Handle the click of the list item 
		super.onListItemClick(l, v, position, id); 
		Intent intent = new Intent(this, ExpenseDetailsActivity.class);
		startActivity(intent);

	}
	
	
	/**
	 * Updates the TextView to display the total expense
	 * If cursor is null or empty, display total expense as 0
	 * 
	 * @param cursor
	 */
	private void updateTotalView(Cursor cursor) {
		
		// TODO: Add up all expense amounts and update the TextView (id = textViewTotalValue)

		int sum=0;
		if (cursor.moveToFirst()) {
			sum+=Integer.parseInt(cursor.getString(cursor.getColumnIndex("ExpenseTable.COLUMN_AMOUNT")));
		}
		TextView item = (TextView) findViewById(R.id.textViewTotalValue);
		item.setText(sum);

	}
	
}
