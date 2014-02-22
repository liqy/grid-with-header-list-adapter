package com.plattysoft.gridviewwithheaderexample;

import com.plattysoft.ui.GridItemClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements GridItemClickListener, OnScrollListener {

	private ListAsGridExampleAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.listView);
		View header = View.inflate(getBaseContext(), R.layout.sample_header, null);
		listView.addHeaderView(header);
		View footer = View.inflate(getBaseContext(), R.layout.sample_footer, null);
		listView.addFooterView(footer);

		mAdapter = new ListAsGridExampleAdapter(this);
		mAdapter.setNumColumns(3);
		mAdapter.setBackgroundResource(R.drawable.row);
		mAdapter.setOnGridClickListener(this);
		listView.setAdapter (mAdapter);
		listView.setOnScrollListener(this);
		
		for (int i = 0; i < 50; i++) {
			mAdapter.addItem(i);
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onGridItemClicked(View v, int position, long itemId) {
		Toast.makeText(this, "Item clicked: "+mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount >= totalItemCount) {
			for (int i = 0; i < 50; i++) {
				mAdapter.addItem(i);
			}
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
