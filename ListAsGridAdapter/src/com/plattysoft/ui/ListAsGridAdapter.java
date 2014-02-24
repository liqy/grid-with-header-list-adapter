package com.plattysoft.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.LinearLayout;

/**
 * Created by shalafi on 6/6/13.
 */
public class ListAsGridAdapter implements ListAdapter {

	private class ListItemClickListener implements OnClickListener {

		private int mPosition;

		public ListItemClickListener(int currentPos) {
			mPosition = currentPos;
		}

		@Override
		public void onClick(View v) {
			onGridItemClicked(v, mPosition);
		}
	}

	private int mNumColumns;
	private Context mContext;
	private GridItemClickListener mGridItemClickListener;
	private int mBackgroundResource = -1;
	private BaseAdapter mItemAdapter;

	public ListAsGridAdapter(Context context, BaseAdapter adapter) {
		mContext = context;
		mNumColumns = 1;
		mItemAdapter = adapter;
	}

	public void setBackgroundResource(int drawableResId) {
		mBackgroundResource = drawableResId;
	}

	public final void setOnGridClickListener(GridItemClickListener listener) {
		mGridItemClickListener = listener;
	}

	private final void onGridItemClicked(View v, int position) {
		if (mGridItemClickListener != null) {
			mGridItemClickListener.onGridItemClicked(v, position,
					getItemId(position));
		}
	}

	public final int getNumColumns() {
		return mNumColumns;
	}

	public final void setNumColumns(int numColumns) {
		mNumColumns = numColumns;
		notifyDataSetChanged();
	}

	@Override
	public final int getCount() {
		return (int) Math.ceil(getItemCount() * 1f / getNumColumns());
	}

	public int getItemCount() {
		return mItemAdapter.getCount();
	}

	protected View getItemView(int position, View convertView, ViewGroup parent) {
		return mItemAdapter.getView(position, convertView, parent);
	}

	@Override
	public final View getView(int position, View view, ViewGroup viewGroup) {
		LinearLayout layout;
		int columnWidth = 0;
		if (viewGroup != null) {
			columnWidth = viewGroup.getWidth() / mNumColumns;
		} else if (view != null) {
			columnWidth = view.getWidth() / mNumColumns;
			;
		}
		// Make it be rows of the number of columns
		if (view == null) {
			// This is items view
			layout = createItemRow(position, viewGroup, columnWidth);
		} else {
			layout = (LinearLayout) view;
			updateItemRow(position, viewGroup, layout, columnWidth);
		}
		return layout;
	}

	private LinearLayout createItemRow(int position, ViewGroup viewGroup,
			int columnWidth) {
		LinearLayout layout;
		layout = new LinearLayout(mContext);
		if (mBackgroundResource > 0) {
			layout.setBackgroundResource(mBackgroundResource);
		}
		int adjustedColumnWidth = columnWidth
				- (layout.getPaddingLeft() + layout.getPaddingRight())
				/ mNumColumns;
		layout.setOrientation(LinearLayout.HORIZONTAL);
		// Now add the sub views to it
		for (int i = 0; i < mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			// Get the new View
			final ViewGroup insideView = new LinearLayout(mContext);
			if (currentPos < getItemCount()) {
				View itemView = getItemView(currentPos, null, viewGroup);
				itemView.setVisibility(View.VISIBLE);
				insideView.setOnClickListener(new ListItemClickListener(
						currentPos));
				insideView.addView(itemView);
			}
			layout.addView(insideView);
			// Set the width of this column
			LayoutParams params = insideView.getLayoutParams();
			params.width = adjustedColumnWidth;
			insideView.setLayoutParams(params);
		}
		return layout;
	}

	private void updateItemRow(int position, ViewGroup viewGroup,
			LinearLayout layout, int columnWidth) {
		int realColumnWidth = columnWidth
				- (layout.getPaddingLeft() + layout.getPaddingRight())
				/ mNumColumns;
		for (int i = 0; i < mNumColumns; i++) {
			int currentPos = position * mNumColumns + i;
			ViewGroup insideView = (ViewGroup) layout.getChildAt(i);
			// If there are less views than objects. add a view here
			if (insideView == null) {
				insideView = new LinearLayout(mContext);
				layout.addView(insideView);
			}
			// Set the width of this column
			LayoutParams params = insideView.getLayoutParams();
			params.width = realColumnWidth;
			insideView.setLayoutParams(params);

			if (currentPos < getItemCount()) {
				insideView.setVisibility(View.VISIBLE);
				View itemView = insideView.getChildAt(0);
				// Populate the view
				View theView = getItemView(currentPos, itemView, viewGroup);
				insideView.setOnClickListener(new ListItemClickListener(currentPos));
				if (itemView == null) {
					insideView.addView(theView);
				}
			} else {
				insideView.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mItemAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mItemAdapter.unregisterDataSetObserver(observer);
	}

	public void notifyDataSetChanged() {
		mItemAdapter.notifyDataSetChanged();
	}

	public void notifyDataSetInvalidated() {
		mItemAdapter.notifyDataSetInvalidated();
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return mItemAdapter.isEmpty();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

}