package com.itnt.bootcamp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "itnttodos.db";
	public static final int DB_VERSION = 1;

	public static final String TABLE_NAME = "todositems";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_PRIORITY = "priority";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID
				+ " integer primary key autoincrement," + COLUMN_TITLE
				+ " text not null," + COLUMN_DESCRIPTION + " text not null,"
				+ COLUMN_PRIORITY + " integer not null" + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
