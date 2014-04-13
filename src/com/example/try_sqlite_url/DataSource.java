package com.example.try_sqlite_url;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSource {
	private String TAG = "SQLITE_URL";
	private SQLiteDatabase database;
	private MySQLiteHelper dbhelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_LINK };

	String TOIurl = "http://timesofindia.feedsportal.com/c/33039/f/533921/index.rss";
	String HinduUrl = "http://www.hindu.com/rss/07hdline.xml";

	private String useTable;

	public DataSource(Context context) {
		Log.d(TAG, "in DataSource Context");

		dbhelper = new MySQLiteHelper(context);

	}

	public void open() throws SQLException {

		Log.d(TAG, "in DataSource Open");

		database = dbhelper.getWritableDatabase();

		new Tables(database);

		// Checking Table
		Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY, null,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Log.d(TAG, "_id   " + cursor.getInt(0));
			Log.d(TAG, "name   " + cursor.getString(1));
			cursor.moveToNext();
		}
		cursor.close();
	}

	public void close() {
		dbhelper.close();
	}

	public ArrayList<Headlines> newspaper(String url) {
		ArrayList<Headlines> listToSend = null;
		if (url == TOIurl) {
			Log.d(TAG, "newspaperTOI url " + url);

			useTable = "toi";
			listToSend = createNews(url);
		} else if (url == HinduUrl) {
			Log.d(TAG, "newspaperHindu url " + url);

			useTable = "hindu";
			listToSend = createNews(url);
		}

		return listToSend;
	}

	public ArrayList<Headlines> createNews(String url) {
		ArrayList<Headlines> arraylist = new ArrayList<Headlines>();
		Log.d(TAG, "CreateNews url " + url);
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			InputStream streamUrl = loadXmlFromNetwork(url);
			xpp.setInput(streamUrl, null);
			Log.d(TAG, "inDobackground " + streamUrl.toString());
			beginDocument(xpp, "item");

			do {
				if (xpp.getEventType() == XmlPullParser.START_TAG
						&& xpp.getName().equals("title")) {
					ContentValues values = new ContentValues();
					xpp.next();
					values.put(MySQLiteHelper.COLUMN_TITLE, xpp.getText());
					xpp.next();
					do {
						if (xpp.getEventType() == XmlPullParser.START_TAG
								&& xpp.getName().equals("link")) {
							xpp.next();
							values.put(MySQLiteHelper.COLUMN_LINK,
									xpp.getText());

							database.insert(useTable, null, values);
						}
						xpp.next();
					} while (xpp.getEventType() != XmlPullParser.TEXT);
				}
				xpp.next();
			} while (xpp.getEventType() != XmlPullParser.END_DOCUMENT);

			Cursor cursor = database.query(useTable, allColumns, null, null,
					null, null, null);

			while (cursor.moveToNext()) {
				Headlines newHeadline = createHeadline(cursor);
				arraylist.add(newHeadline);
			}

		} catch (Throwable t) {
			Log.d(TAG, t.toString());
		}
		return arraylist;
	}

	private Headlines createHeadline(Cursor cursor) {
		Headlines headlines = new Headlines();
		headlines.setTitle(cursor.getString(1));
		headlines.setLink(cursor.getString(2));
		return headlines;
	}

	private void beginDocument(XmlPullParser xpp, String string)
			throws XmlPullParserException, IOException {

		while ((xpp.next() != XmlPullParser.END_DOCUMENT)) {
			if ((xpp.getEventType() == XmlPullParser.START_TAG)
					&& (xpp.getName().equals("item"))) {
				return;
			}
		}
	}

	private InputStream loadXmlFromNetwork(String urlString)
			throws XmlPullParserException, IOException {
		InputStream stream = null;
		try {

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			stream = conn.getInputStream();

		} finally {
		}

		return stream;

	}
}
