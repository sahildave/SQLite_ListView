package com.example.try_sqlite_url;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Tables {
	private String TAG = "TABLE";
	private SQLiteDatabase database;

	public static Tables tableInstance;

	public static Tables getInstance() {
		if (tableInstance == null) {
			tableInstance = new Tables();
			Log.d("TABLE", "getting new Table Instance");
		}
		return tableInstance;
	}

	private Tables() {
		Log.d(TAG, "in Tables constructor.");
	}

	public void getDatabase(SQLiteDatabase db) {
		Log.d(TAG, "In getDatabase");
		database = db;
	}

	public void createStartupTables(SQLiteDatabase db) {
		Log.d(TAG, "in createStartupTables");
		database = db;
		createTableNewspaper();
		createTableCategory();
		createTableNewsCat();
	}

	private void createTableNewspaper() {

		Log.d(TAG, "in createTableNewspaper");

		String newspaper_array[][] = { { "NP_TOI", "en" },
				{ "NP_HINDU", "en" }, { "NP_FP", "en" }, { "NP_HT", "en" } };

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_NEWSPAPER + " ("
				+ MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_NAME + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_LANGUAGE + ")"
				+ " VALUES(?, ?, ?)";

		SQLiteStatement statement = database.compileStatement(sql);

		for (int i = 0; i < 4; i++) {

			statement.clearBindings();
			statement.bindLong(1, i + 1);
			statement.bindString(2, newspaper_array[i][0]);
			statement.bindString(3, newspaper_array[i][1]);
			statement.execute();
		}
	}

	private void createTableCategory() {

		Log.d(TAG, "in createTableCategory");

		String category_array[] = { "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER" };

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_CATEGORY + " ("
				+ MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_CATEGORY_NAME + ")" + " VALUES(?, ?)";

		SQLiteStatement statement = database.compileStatement(sql);

		for (int i = 0; i < 5; i++) {

			statement.clearBindings();
			statement.bindLong(1, i + 1);
			statement.bindString(2, category_array[i]);
			statement.execute();
		}
	}

	private void createTableNewsCat() {

		Log.d(TAG, "in createTableNewsCat");

		String link_array[] = { "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER", "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER", "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER", "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER" };

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_NEWSPAPER_CATEGORY
				+ " (" + MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_ID + " ,"
				+ MySQLiteHelper.COLUMN_CATEGORY_ID + " ,"
				+ MySQLiteHelper.COLUMN_URL + ")" + " VALUES(?, ?, ?, ?)";

		SQLiteStatement statement = database.compileStatement(sql);
		int count = 1;

		for (int i = 1; i < 5; i++) {

			for (int j = 1; j < 6; j++) {

				statement.clearBindings();
				statement.bindLong(1, count);
				statement.bindLong(2, i);
				statement.bindLong(3, j);
				statement.bindString(4, link_array[count - 1]);
				statement.execute();
				count++;
			}
		}
	}

	public void createUserPrefTables(int[] ncup_array) {
		Log.d(TAG, "in createUserPrefTables");

		database.beginTransaction();

		try {
			createNCUPtable(ncup_array);
			database.setTransactionSuccessful();
		} finally {
			database.endTransaction();
			database.close();
			MainActivity.source.close();
		}
	}

	private void createNCUPtable(int[] ncup) {

		Log.d(TAG, "in createNCUPtable");

		database.delete(MySQLiteHelper.TABLE_NCUP, null, null);

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_NCUP + " ("
				+ MySQLiteHelper.COLUMN_ID + " ," + MySQLiteHelper.COLUMN_NC_ID
				+ " ," + MySQLiteHelper.COLUMN_NEWSPAPER_ID + " ,"
				+ MySQLiteHelper.COLUMN_CATEGORY_ID + ")"
				+ " VALUES(?, ?, ?, ?)";

		SQLiteStatement statement = database.compileStatement(sql);
		int count = 1;
		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 5; j++) {

				if (ncup[(i * 5) + j] == 1) {
					statement.clearBindings();
					statement.bindLong(2, count);
					statement.bindLong(3, i + 1);
					statement.bindLong(4, j + 1);
					statement.execute();
				}
				count++;
			}
		}
		checkTable();
	}

	public void checkTable() {
		Log.d("TABLE", "Printing TABLE");
		Log.d("TABLE", "==============");
		// Checking Table
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NCUP, null, null,
				null, null, null, null);
		cursor.moveToFirst();
		Log.d("TABLE", "id    np_id    news    cat");
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(0);
			int np_id = cursor.getInt(1);
			int news = cursor.getInt(2);
			int cat = cursor.getInt(3);

			Log.d("TABLE", id + "    " + np_id + "    " + news + "    " + cat);
			cursor.moveToNext();
		}
		cursor.close();

	}
}
