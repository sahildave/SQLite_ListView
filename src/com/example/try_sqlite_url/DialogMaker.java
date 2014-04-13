package com.example.try_sqlite_url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;

public class DialogMaker {
	private String TAG = "SQLITE_URL";
	Context ctx;
	MyExpandableListAdapter myListAdapter;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	public static Dialog dialog;

	public DialogMaker(Context context_dialog) {
		Log.d(TAG, "inDialogMaker Constructor");

		ctx = context_dialog;

		dialog = new Dialog(ctx);
		dialog.setContentView(R.layout.dialog_layout);
		dialog.setTitle("Please Select Your Feeds...");

		final ExpandableListView expListView = (ExpandableListView) dialog
				.findViewById(R.id.dialog_expandable);

		prepareListData();
		myListAdapter = new MyExpandableListAdapter(context_dialog,
				listDataHeader, listDataChild);

		expListView.setAdapter(myListAdapter);
		MyExpandableListAdapter.getExpList(expListView);
		dialog.show();

		Button doneButton = (Button) dialog.findViewById(R.id.button_in_dialog);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap<Integer, boolean[]> userPrefs = MyExpandableListAdapter.mChildCheckStates;

				for (Integer integer : userPrefs.keySet()) {
					Log.d(TAG, "printing Hashmap");
					String key = integer.toString();

					for (boolean value : userPrefs.get(integer)) {
						String print = String.valueOf(value);
						System.out.println(key + " " + print);
						Log.d(TAG, key + "  " + print);
					}

				}
				dialog.dismiss();
			}
		});

	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Times of India");
		listDataHeader.add("Hindustand Times");
		listDataHeader.add("FirstPost");

		// Adding child data
		List<String> TOI = new ArrayList<String>();
		TOI.add("National");
		TOI.add("International");
		TOI.add("Politics");
		TOI.add("Sports");
		TOI.add("Entertainment");

		List<String> HT = new ArrayList<String>();
		HT.add("National");
		HT.add("International");
		HT.add("Politics");
		HT.add("Sports");
		HT.add("Entertainment");

		List<String> FP = new ArrayList<String>();
		FP.add("National");
		FP.add("International");
		FP.add("Politics");
		FP.add("Sports");
		FP.add("Entertainment");

		listDataChild.put(listDataHeader.get(0), TOI); // Header, Child data
		listDataChild.put(listDataHeader.get(1), HT);
		listDataChild.put(listDataHeader.get(2), FP);
	}

}
