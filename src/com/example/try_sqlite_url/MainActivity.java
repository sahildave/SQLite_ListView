package com.example.try_sqlite_url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	private String TAG = "SQLITE_URL";

	public String url;
	String TOIurl = "http://timesofindia.feedsportal.com/c/33039/f/533921/index.rss";
	String HinduUrl = "http://www.hindu.com/rss/07hdline.xml";

	MyExpandableListAdapter myListAdapter;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	final Context context_dialog = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayAdapter<Headlines> adapter = new ArrayAdapter<Headlines>(this,
				android.R.layout.simple_list_item_1, new ArrayList<Headlines>());
		setListAdapter(adapter);

		new DialogMaker(context_dialog);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		DialogMaker.dialog.getWindow().setLayout((width * 9) / 10,
				(height * 9) / 10);

	}

	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.toi:
			url = TOIurl;
			Log.d(TAG, "onClick TOI url " + url);
			callAsync();
			break;
		case R.id.hindu:
			url = HinduUrl;
			Log.d(TAG, "onClick Hindu url " + url);
			callAsync();
			break;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		url = TOIurl;
		Log.d(TAG, "onResume url " + url);
		callAsync();
	}

	private void callAsync() {
		MyAsyncTask async = new MyAsyncTask(this);
		async.execute();
	}

	private class MyAsyncTask extends
			AsyncTask<Void, Void, ArrayList<Headlines>> {

		ArrayList<Headlines> values;
		Context ctx = null;

		public MyAsyncTask(Context context) {
			ctx = context;
		}

		@Override
		protected ArrayList<Headlines> doInBackground(Void... params) {

			DataSource source = new DataSource(ctx);
			source.open();
			Log.d(TAG, "inBackground url " + url);
			values = source.newspaper(url);
			source.close();

			return values;

		}

		@Override
		protected void onPostExecute(ArrayList<Headlines> result) {
			super.onPostExecute(result);
			// Log.d(TAG, "onPostExecute "+result.toString());
			ArrayAdapter<Headlines> asyncAdapter = (ArrayAdapter<Headlines>) getListAdapter();
			asyncAdapter.clear();
			asyncAdapter.addAll(result);
			asyncAdapter.notifyDataSetChanged();
		}

	}
}
