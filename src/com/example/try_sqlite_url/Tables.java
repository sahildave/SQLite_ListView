package com.example.try_sqlite_url;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class Tables {
	private String TAG = "SQLITE_URL";
	private SQLiteDatabase database;

	public Tables(SQLiteDatabase db) {
		Log.d("TABLE", "in Tables constructor databas");
		database = db;
		createTableNewspaper();
		createTableCategory();
		createTableNewsCat();
	}

	public Tables(int[] ncup_array) {
		Log.d("TABLE", "in Tables constructor array");

		database.beginTransaction();
		try {
			createNCUPtable(ncup_array);
		} finally {
			database.endTransaction();
		}
	}

	private void createTableNewspaper() {

		Log.d("TABLE", "in createTableNewspaper");

		String newspaper_array[][] = { { "NP_TOI", "en" },
				{ "NP_HINDU", "en" }, { "NP_FP", "en" }, { "NP_HT", "en" } };

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_NEWSPAPER + " ("
				+ MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_NAME + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_LANGUAGE + ")"
				+ " VALUES(?, ?, ?)";

		Log.d("TABLE", "db.isOpen TN " + database.isOpen());

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

		Log.d("TABLE", "in createTableCategory");

		String category_array[] = { "CAT_NAT", "CAT_INTER", "CAT_POLI",
				"CAT_SPORTS", "CAT_ENTER" };

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_CATEGORY + " ("
				+ MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_CATEGORY_NAME + ")" + " VALUES(?, ?)";

		Log.d("TABLE", "db.isOpen TC " + database.isOpen());

		SQLiteStatement statement = database.compileStatement(sql);

		for (int i = 0; i < 5; i++) {

			statement.clearBindings();
			statement.bindLong(1, i + 1);
			statement.bindString(2, category_array[i]);
			statement.execute();
		}
	}

	private void createTableNewsCat() {

		Log.d("TABLE", "in createTableNewsCat");

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

		Log.d("TABLE", "db.isOpen TNC " + database.isOpen());

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

	private void createNCUPtable(int[] ncup) {

		Log.d("TABLE", "in createNCUPtable");

		String sql = "INSERT INTO " + MySQLiteHelper.TABLE_NCUP + " ("
				+ MySQLiteHelper.COLUMN_ID + " ,"
				+ MySQLiteHelper.COLUMN_NEWSPAPER_ID + " ,"
				+ MySQLiteHelper.COLUMN_CATEGORY_ID + ")" + " VALUES(?, ?, ?)";

		Log.d("TABLE", "db.isOpen TNCUP " + database.isOpen());

		SQLiteStatement statement = database.compileStatement(sql);
		int count = 1;
		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 5; j++) {

				if (ncup[(i * 5) + j] == 1) {
					statement.clearBindings();
					statement.bindLong(1, count);
					statement.bindLong(2, i + 1);
					statement.bindLong(3, j + 1);
					statement.execute();
				}
				count++;
			}
		}
	}
}
