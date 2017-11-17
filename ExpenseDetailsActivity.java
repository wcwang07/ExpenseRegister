/**
 * ExpenseDetailsActivity represents the Details window of the ExpenseRegister application.
 * It displays the details of a new or an existing expense record. 
 * <p>
 * The UI contains an ExpenseDetailsFragment and three buttons: Save, Delete, List.
 * <ul>
 * 	<li>Save: save the details to the database
 *  <li>Delete: delete the displayed record from the database
 *  <li>List: cancel the editing of the record and return to the list of all expense records
 * </ul>
 * <p>
 * Note: The "Delete" button is hidden when this window is invoked from the "Add" button click
 * in the Expense List window.
 * 
 * @author      Wei Ching Wang
 * @version     1.0
 * @since       1.0
 */


public class ExpenseDetailsActivity extends Activity {
	
	// The Uri object passed in when this window is invoked
	private Uri uri;
	
	// The expense details Fragment contained in this window
	private ExpenseDetailsFragment detailsFragment;

	
	/**
	 * Called at the start of the Activity life cycle
	 * 
	 * @param savedInstanceState
	 */		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // TODO: inflate the UI
        setContentView(R.layout.expense_row);

        
        // TODO: initialize detailsFragment
        detailsFragment = new ExpenseDetailsFragment();


        Bundle extras = getIntent().getExtras();
        // This window is invoked from list item click
        if (extras != null) {
        	
            // TODO: initialize "uri" using <code>Bundle.getParcelable()</code> method        	
        	uri = extras.getParcelable();
        	
        	// TODO: load the details of the clicked expense item identified by the uri
        	detailsFragment.setArgument(uri);
        }
		// This window is invoked from "Add" button click        
    	else { 		

    		// TODO: Hide the "Delete" button using <code>setVisibility(View.GONE)</code> of the View class
    		setVisibility(View.GONE);
    	}
    }

    
	/**
	 * Called when the "Save" button is clicked. It inserts a new expense record or
	 * updates the selected existing record. After the data is saved, it takes the user
	 * back to the Expense List window.
	 * 
	 * @param view
	 */	    
    public void onSaveExpenseClick(View view) {    	

    	// Abort when description or amount is not specified
		if (detailsFragment.getDescriptionText().length() == 0 || detailsFragment.getAmountText().length() == 0) {
			return;
		}



		// A new expense record
		if (uri == null) {
			
			// TODO: Insert a new expense record and sets the uri to the newly inserted record
			ContentValues newValues = new ContentValues();
			newValues.put(ExpenseContentProvider.EXPENSE_SINGLE,  );
			newValues.put(ExpenseContentProvider.EXPENSE_ALL,  );



			uri = getContentResolver().insert(ExpenseContentProvider.CONTENT_URI, newValues); 
		} 
		// An existing expense record
		else {
			ContentResolver cr = context.getContentResolver();
			Uri idUri = ContentUris.withAppendedId(ExpenseContentProvider.CONTENT_URI, );
			ContentValues mContentValues = new ContentValues ();

			// TODO: Update the existing expense record
			getContentResolver().update(uri, mContentValues, selection, selectionArgs);
		}

		// Go to the Expense List window
		onListExpenseClick(view);
    }


	/**
	 * Called when the "Delete" button is clicked. It deleted the selected existing record. 
	 * After the data is deleted, it takes the user back to the Expense List window.
	 * 
	 * @param view
	 */    
    public void onDeleteExpenseClick(View view) {    	
    	
		
			// TODO: delete the existing expense record
			try {
            int id = ((ViewGroup) (view.getParent()).getParent()).getId();

            switch (id) {
                case R.id.EXPENSE_SINGLE: {
                    int position = ExpenseDetailsActivity.getPositionForView(view);
                    URLInfo toDelete = (URLInfo) ExpenseDetailsActivity.getItemAtPosition(position);
                    PersistenceManager.deleteRecordByUrl(PersistenceManager.ContentType.HISTORY, toDelete);
                    break;
                }
                case R.id.EXPENSE_ALL: {
            int position = ExpenseDetailsActivity.getPositionForView(view);
            URLInfo toDelete = (URLInfo)ExpenseDetailsActivity.getItemAtPosition(position);
            PersistenceManager.deleteRecordByUrl(PersistenceManager.ContentType.BOOKMARKS, toDelete);
                    break;
                }
                default: {
                    return;
                }
            }
            display(false);
        } catch (final Exception e) {
            Utility.showAlertDialog(BookmarksActivity.class.getSimpleName() + ".onDeleteClick(): Failed. " + e.toString(), this);
        }

		

		// Go to the Expense List window
		onListExpenseClick(view);
    }  
   

	/**
	 * Called when the "List" button is clicked. It takes the user back to the Expense List window.
	 * 
	 * @param view
	 */        
    public void onListExpenseClick(View view) {
    	
    	// TODO: Implement the method
    	Intent intent = new Intent(this, ExpenseDetailsActivity.class);
		startActivity(intent);

    }
    
}
