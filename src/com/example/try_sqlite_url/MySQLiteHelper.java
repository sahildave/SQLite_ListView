package com.example.try_sqlite_url;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private String TAG = "SQLITE_URL";

	private static final String DATABASE_NAME = "feeds_three.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_TOI = "toi";
	public static final String TABLE_HINDU = "hindu";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_LINK = "link";

	//@formatter:off
	private static final String CREATE_TABLE_TOI = 
			"CREATE TABLE " + TABLE_TOI + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TITLE + " TEXT NOT NULL, "
			+ COLUMN_LINK + " TEXT NOT NULL);";
	
	private static final String CREATE_TABLE_HINDU = 
			"CREATE TABLE " + TABLE_HINDU + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TITLE + " TEXT NOT NULL, "
			+ COLUMN_LINK + " TEXT NOT NULL);";
	
	
	//@formatter:on

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TOI);
		db.execSQL(CREATE_TABLE_HINDU);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HINDU);

		onCreate(db);

	}

}
