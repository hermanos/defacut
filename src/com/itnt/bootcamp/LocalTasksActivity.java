package com.itnt.bootcamp;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.itnt.bootcamp.db.TodoDataSource;
import com.itnt.bootcamp.model.Priority;
import com.itnt.bootcamp.model.TodoItem;
import com.itnt.bootcamp.persistence.CustomAdapter;
import com.itnt.bootcamp.resources.Utils;
/**
 * 
 * @author GabiRadu
 *
 */
public class LocalTasksActivity extends Activity {

	// UI Elements
	private ListView todoListView;
	private CustomAdapter listAdapter;

	// Data Source
	private TodoDataSource dataSource;
	private List<TodoItem> listofItems;

	// Activity Intent Result Types
	private final int ADD_MODE = 1;
	private final int EDIT_MODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// fetches all todos items from the DB
		dataSource = new TodoDataSource(this);
		loadDB();
		sortTodoItems();

		// creates the custom adapter
		listAdapter = new CustomAdapter(this, R.id.itemTitle, listofItems);

		// attaches the custom adapter to the list
		todoListView = (ListView) findViewById(R.id.todoList);
		todoListView.setAdapter(listAdapter);

		// sets the edit action on the list click
		todoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
				edit(pos);
			}
		});

		// open a menu on long click
		todoListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> p, View v, int pos,
					long id) {
				(new ChooseBox(pos)).show();
				return true;
			}
		});

		// adds a button for adding new items to the TODOlist
		ImageView addButton = (ImageView) findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add();
			}
		});

		// check Internet Connection.
		if (!Utils.isNetworkAvailable(LocalTasksActivity.this)) {
			Toast.makeText(LocalTasksActivity.this,
					getString(R.string.noconnectivity), Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Loads all the items from the DB.
	 */
	private void loadDB() {
		dataSource.open();
		listofItems = dataSource.getAllItems();
		dataSource.close();
	}

	/**
	 * Add todo item in local storage
	 */
	private void add() {
		Intent addIntent = new Intent(LocalTasksActivity.this,
				AddTodoItemActivity.class);
		addIntent.setAction("add");
		startActivityForResult(addIntent, ADD_MODE);
	}

	/**
	 * Starts the ThingActivity activity for editing the thing at the position
	 * tIndex in the list.
	 * 
	 * @param tIndex
	 *            the position of the thing to edit in the list
	 */
	private void edit(int tIndex) {
		Intent editIntent = new Intent(LocalTasksActivity.this,
				AddTodoItemActivity.class);

		TodoItem item = listofItems.get(tIndex);
		editIntent.setAction("edit");
		editIntent.putExtra("pos", tIndex);
		editIntent.putExtra("title", item.getTitle());
		editIntent.putExtra("description", item.getDescription());
		editIntent.putExtra("priority", item.getPriority().ordinal());

		startActivityForResult(editIntent, EDIT_MODE);
	}

	/**
	 * Removes the item at the position tIndex in the list, thus notifies the
	 * change to the list adapter, and then to the user through a toast.
	 * 
	 * @param tIndex
	 *            the position of the thing to edit in the list
	 */
	private void delete(int tIndex) {
		listofItems.remove(tIndex);

		listAdapter.notifyDataSetChanged();

		Toast.makeText(LocalTasksActivity.this,
				getString(R.string.toast_delete_success), Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * Sorts the items in the list of TODOs ('sortTodoItems').
	 */
	private void sortTodoItems() {
		Collections.sort(listofItems);
	}

	/**
	 * Adds three buttons on the options menu: 1. Add - performs an add as it
	 * was called by the "add thing button". 2. Sort - sorts the list of things
	 * by comparing their priorities. .
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// add button
		menu.add(R.string.menu_add_item).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						add();
						return true;
					}
				});

		// sort button
		menu.add(R.string.menu_sort_items_by_priority)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						sortTodoItems();
						listAdapter.notifyDataSetChanged();
						return true;
					}
				});

		return true;
	}

	/**
	 * Handles the results coming from the ThingActivity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case ADD_MODE:
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();

				TodoItem tmpThing = new TodoItem(extras.getString("title"),
						extras.getString("description"),
						Priority.values()[extras.getInt("priority")]);

				listofItems.add(0, tmpThing);

				// notifies the add to the list adapter
				listAdapter.notifyDataSetChanged();

				Toast.makeText(LocalTasksActivity.this,
						getString(R.string.toast_add_success),
						Toast.LENGTH_SHORT).show();
			}
			break;

		case EDIT_MODE:
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();

				// updates the local thing
				int tId = extras.getInt("pos");

				TodoItem item = listofItems.get(tId);
				item.setTitle(extras.getString("title"));
				item.setDescription(extras.getString("description"));
				item.setPriority(Priority.values()[extras.getInt("priority")]);

				// notifies the changes to the list adapter and then alerts
				// the user through the use of a Toast
				listAdapter.notifyDataSetChanged();
				Toast.makeText(LocalTasksActivity.this,
						getString(R.string.toast_update_success),
						Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}

	/**
	 * Updates the DB with the new list of things to do and then lets the
	 * application does the rest.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		dataSource.open();
		dataSource.updateAll(listofItems);
		dataSource.close();

		super.onSaveInstanceState(outState);
	}

	/**
	 * Represents a dialog in which the user can choose if edit or delete the
	 * current item.
	 */
	private class ChooseBox extends AlertDialog {

		public ChooseBox(final int tIndex) {
			super(LocalTasksActivity.this);

			// set the box title's
			setTitle(R.string.choose_box_title);

			// sets what to do when the POSITIVE button ("Edit") is clicked
			setButton(BUTTON_POSITIVE,
					getString(R.string.choose_box_positive_button),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							edit(tIndex);
						}
					});

			// sets what to do when the NEGATIVE button ("Delete") is clicked
			setButton(BUTTON_NEGATIVE,
					getString(R.string.choose_box_negative_button),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							delete(tIndex);
						}
					});
		}
	}
}