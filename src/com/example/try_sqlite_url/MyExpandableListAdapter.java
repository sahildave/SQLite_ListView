package com.example.try_sqlite_url;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<String> mListDataHeader;
	private HashMap<String, List<String>> mListDataChild;
	public static HashMap<Integer, boolean[]> mChildCheckStates;

	private ChildViewHolder childViewHolder;
	private GroupViewHolder groupViewHolder;

	private String groupText;
	private String childText;
	private int lastExpandedGroupPosition = -1;

	public static ExpandableListView expandableList;

	public MyExpandableListAdapter(Context context,
			List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this.mContext = context;
		this.mListDataHeader = listDataHeader;
		this.mListDataChild = listChildData;
		mChildCheckStates = new HashMap<Integer, boolean[]>();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		groupText = getGroup(groupPosition).toString();

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dialog_list_group, null);

			// Initialize the GroupViewHolder defined at the bottom of this
			// document
			groupViewHolder = new GroupViewHolder();
			groupViewHolder.mGroupText = (TextView) convertView
					.findViewById(R.id.list_group);
			convertView.setTag(groupViewHolder);
		} else {

			groupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		groupViewHolder.mGroupText.setText(groupText);
		groupViewHolder.mGroupText.setTypeface(null, Typeface.BOLD);

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final int mGroupPosition = groupPosition;
		final int mChildPosition = childPosition;

		childText = getChild(groupPosition, childPosition).toString();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater
					.inflate(R.layout.dialog_list_rowdetail, null);

			childViewHolder = new ChildViewHolder();

			childViewHolder.mChildText = (TextView) convertView
					.findViewById(R.id.childTextView);

			childViewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);

			convertView.setTag(R.layout.dialog_list_rowdetail, childViewHolder);
		} else {

			childViewHolder = (ChildViewHolder) convertView
					.getTag(R.layout.dialog_list_rowdetail);
		}

		childViewHolder.mChildText.setText(childText);

		childViewHolder.mCheckBox.setOnCheckedChangeListener(null);

		if (mChildCheckStates.containsKey(mGroupPosition)) {
			boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
			childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);

		} else {
			boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
			mChildCheckStates.put(mGroupPosition, getChecked);
			childViewHolder.mCheckBox.setChecked(false);
		}

		childViewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						if (isChecked) {

							boolean getChecked[] = mChildCheckStates
									.get(mGroupPosition);
							getChecked[mChildPosition] = isChecked;
							mChildCheckStates.put(mGroupPosition, getChecked);

						} else {

							boolean getChecked[] = mChildCheckStates
									.get(mGroupPosition);
							getChecked[mChildPosition] = isChecked;
							mChildCheckStates.put(mGroupPosition, getChecked);
						}
					}
				});

		return convertView;
	}

	public static void getExpList(ExpandableListView explist) {
		expandableList = explist;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.mListDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.mListDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {

		if (groupPosition != lastExpandedGroupPosition) {
			expandableList.collapseGroup(lastExpandedGroupPosition);
		}
		expandableList.smoothScrollToPosition(groupPosition);
		lastExpandedGroupPosition = groupPosition;
		super.onGroupExpanded(groupPosition);

	};

	public final class GroupViewHolder {

		TextView mGroupText;
	}

	public final class ChildViewHolder {

		TextView mChildText;
		CheckBox mCheckBox;
	}
}