package com.plattysoft.gridviewwithheaderexample;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plattysoft.ui.ListAsGridBaseAdapter;

/**
 * Created by shalafi on 6/6/13.
 */
public class ListAsGridExampleAdapter extends ListAsGridBaseAdapter {

	ArrayList<Integer> mArray = new ArrayList<Integer>();
	
	private LayoutInflater mInflater;

	public ListAsGridExampleAdapter(Context context) {
		super(context);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public Integer getItem(int position) {
		return mArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return mArray.size();
	}
	
	public void addItem(Integer i) {
		mArray.add(i);
	}

	@Override
	protected View getItemView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = mInflater.inflate(R.layout.simple_list_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.text);        
		tv.setText(String.valueOf(getItem(position)));
		return view;
	}

}
