package com.itnt.bootcamp.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itnt.bootcamp.model.Priority;
import com.itnt.bootcamp.model.TodoItem;

public class TodoDataSource {

	private SQLiteDatabase db;
	private SQLiteOpenHelper dbHelper;
	private String[] allColumns = { DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE,
			DBHelper.COLUMN_DESCRIPTION, DBHelper.COLUMN_PRIORITY };

	public TodoDataSource(Context context) {
		dbHelper = new DBHelper(context);
	}

	public void open() throws SQLException {
		if (db == null || !db.isOpen())
			// Create and/or open a database that will be used for reading and
			// writing
			db = dbHelper.getWritableDatabase();
	}

	public void close() {
		// Close any open database object.
		dbHelper.close();
	}

	public TodoItem createItem(TodoItem t) {
		ContentValues values = new ContentValues();

		values.put(DBHelper.COLUMN_TITLE, t.getTitle());
		values.put(DBHelper.COLUMN_DESCRIPTION, t.getDescription());
		values.put(DBHelper.COLUMN_PRIORITY, t.getPriority().ordinal());

		// method for inserting a row into the database
		long insertId = db.insert(DBHelper.TABLE_NAME, null, values);

		// method for inserting a row into the database
		Cursor cursor = db.query(DBHelper.TABLE_NAME, allColumns,
				DBHelper.COLUMN_ID + " = " + insertId, null, null, null, null);

		// Move the cursor to the first row.
		cursor.moveToFirst();

		// construct a <p>TodoItem</p> from cursor's info
		TodoItem newItem = readCursorDetails(cursor);

		// Closes the Cursor, releasing all of its resources and making it
		// completely invalid
		cursor.close();

		return newItem;
	}

	private TodoItem readCursorDetails(Cursor cursor) {
		TodoItem newItem = new TodoItem();
		newItem.setId(cursor.getLong(0));
		newItem.setTitle(cursor.getString(1));
		newItem.setDescription(cursor.getString(2));
		newItem.setPriority(Priority.values()[cursor.getInt(3)]);

		return newItem;
	}

	public void deleteItem(TodoItem item) {
		db.delete(DBHelper.TABLE_NAME,
				DBHelper.COLUMN_ID + " = " + item.getId(), null);
	}

	public List<TodoItem> getAllItems() {
		List<TodoItem> items = new ArrayList<TodoItem>();

		Cursor cursor = db.query(DBHelper.TABLE_NAME, allColumns, null, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			items.add(readCursorDetails(cursor));
			cursor.moveToNext();
		}
		cursor.close();

		return items;
	}

	public void updateAll(List<TodoItem> items) {
		deleteAll();
		addAll(items);
	}

	public void addAll(List<TodoItem> items) {
		for (TodoItem t : items) {
			createItem(t);
		}
	}

	private void deleteAll() {
		db.delete(DBHelper.TABLE_NAME, null, null);
	}
}
