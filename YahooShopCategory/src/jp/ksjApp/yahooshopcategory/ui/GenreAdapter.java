package jp.ksjApp.yahooshopcategory.ui;

import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.R;
import jp.ksjApp.yahooshopcategory.data.GenreData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author mtb_cc_sin5
 * 
 */
public abstract class GenreAdapter extends BaseAdapter {
	
	private static final String TAG = GenreAdapter.class.getSimpleName();
	
	private Context mContext;
	private ArrayList<GenreData> mGenreData;
	private LayoutInflater mInflater;
	private String mSearchGenreTitle;
	
	public GenreAdapter(Context c, ArrayList<GenreData> genreData, String searchGenreTitle) {
		mContext = c;
		mGenreData = genreData;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSearchGenreTitle = searchGenreTitle;
	}

	public int getCount() {
		return mGenreData.size();
	}

	public Object getItem(int position) {
		return mGenreData.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final TextView tv;
		if (convertView == null || convertView.getId() != position) {
			convertView = mInflater.inflate(R.layout.grid_item_text, parent,
					false);
			tv = (TextView) convertView.findViewById(R.id.txt_genre);
			convertView.setTag(tv);
		} else {
			tv = (TextView) convertView.getTag();
		}

		final GenreData item = (GenreData) mGenreData.get(position);
		if (item == null) {
			return convertView;
		}
		tv.setText(item.name);

		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e(TAG, "item,id : " + item.id);
				Log.e(TAG, "item,name : " + item.name);
				startGenreActivity(mContext, item, mSearchGenreTitle);
			}
		});

		return convertView;

	}
	
	protected abstract void startGenreActivity(Context context, GenreData item, String searchGenreTitle);
}
