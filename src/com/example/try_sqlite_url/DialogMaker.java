package com.example.try_sqlite_url;

import java.util.ArrayList;
import java.util.Arrays;
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
				int[] ncup = new int[20];

				for (int i = 0; i < 4; i++) {
					if (!userPrefs.containsKey(i)) {
						for (int j = 0; j < 5; j++) {
							ncup[(i * 5) + j] = 0;
						}
					} else {
						int indexCount = 0;
						for (boolean value : userPrefs.get(i)) {
							if (value) {
								ncup[(i * 5) + indexCount] = 1;
							} else {
								ncup[(i * 5) + indexCount] = 0;
							}
							indexCount++;
						}
					}
				}
				Log.d("TABLE", Arrays.toString(ncup));

				Tables instance = Tables.getInstance();
				Log.d("TABLE", "got instance");
				instance.createUserPrefTables(ncup);
				dialog.dismiss();
			}
		});

	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Times of India");
		listDataHeader.add("Hindustan Times");
		listDataHeader.add("FirstPost");
		listDataHeader.add("The Hindu");

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

		List<String> TH = new ArrayList<String>();
		TH.add("National");
		TH.add("International");
		TH.add("Politics");
		TH.add("Sports");
		TH.add("Entertainment");

		listDataChild.put(listDataHeader.get(0), TOI); // Header, Child data
		listDataChild.put(listDataHeader.get(1), HT);
		listDataChild.put(listDataHeader.get(2), FP);
		listDataChild.put(listDataHeader.get(3), TH);
	}

}
