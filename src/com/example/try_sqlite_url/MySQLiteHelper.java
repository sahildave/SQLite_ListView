package com.example.try_sqlite_url;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static MySQLiteHelper sInstance;
	public Tables tablesInstance;
	public SQLiteDatabase database;

	private String TAG = "SQLITE_URL";

	private static final String DATABASE_NAME = "feeds_2i2.db";
	private static final int DATABASE_VERSION = 3;

	public static final String TABLE_TOI = "toi";
	public static final String TABLE_HINDU = "hindu";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_LINK = "link";

	public static final String COLUMN_NEWSPAPER_NAME = "newspaper_name_column";
	public static final String COLUMN_NEWSPAPER_LANGUAGE = "language_column";
	public static final String COLUMN_CATEGORY_NAME = "category_name_column";
	public static final String COLUMN_NEWSPAPER_ID = "newspaper_id_column";
	public static final String COLUMN_CATEGORY_ID = "category_id_column";
	public static final String COLUMN_URL = "url_column";

	public static final String TABLE_NEWSPAPER = "newspaper_table";
	public static final String TABLE_CATEGORY = "category_table";
	public static final String TABLE_NEWSPAPER_CATEGORY = "newspaper_category_table";

	// new
	public static final String COLUMN_NC_ID = "newspaper_cat_id_column";
	public static final String TABLE_NCUP = "ncup_table";

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
	
	private static final String CREATE_TABLE_NEWSPAPER = 
			"CREATE TABLE " + TABLE_NEWSPAPER + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NEWSPAPER_NAME + " TEXT NOT NULL, "
			+ COLUMN_NEWSPAPER_LANGUAGE + " TEXT NOT NULL);";
	
	private static final String CREATE_TABLE_CATEGORY = 
			"CREATE TABLE " + TABLE_CATEGORY + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";
	
	private static final String CREATE_TABLE_NEWSPAPER_CATEGORY = 
			"CREATE TABLE " + TABLE_NEWSPAPER_CATEGORY + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NEWSPAPER_ID + " TEXT NOT NULL, "
			+ COLUMN_CATEGORY_ID + " TEXT NOT NULL, "
			+ COLUMN_URL + " TEXT NOT NULL);";
	
	//new
	private static final String CREATE_TABLE_NCUP = 
			"CREATE TABLE " + TABLE_NCUP + "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_NC_ID + " TEXT NOT NULL, "
			+ COLUMN_NEWSPAPER_ID + " TEXT NOT NULL, "
			+ COLUMN_CATEGORY_ID + " TEXT NOT NULL);";
	
	//@formatter:on

	public static MySQLiteHelper getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new MySQLiteHelper(context);
		}
		return sInstance;
	}

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_TOI);
		db.execSQL(CREATE_TABLE_HINDU);
		db.execSQL(CREATE_TABLE_NEWSPAPER);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_NEWSPAPER_CATEGORY);
		db.execSQL(CREATE_TABLE_NCUP);

		Tables tablesInstance = Tables.getInstance();
		tablesInstance.createStartupTables(db);

		Log.d("TABLE", "in onCreate MySQLhelper");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.d("TABLE", "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOI);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HINDU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSPAPER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSPAPER_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NCUP);

		onCreate(db);

	}

}
