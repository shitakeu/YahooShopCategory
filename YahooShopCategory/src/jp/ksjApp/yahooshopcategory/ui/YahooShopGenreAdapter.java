package jp.ksjApp.yahooshopcategory.ui;

import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.data.GenreData;
import android.content.Context;
import android.content.Intent;

/**
 * 
 * @author mtb_cc_sin5
 * 
 */
public class YahooShopGenreAdapter extends GenreAdapter {

	public YahooShopGenreAdapter(Context c, ArrayList<GenreData> genreData,
			String searchGenreTitle) {
		super(c, genreData, searchGenreTitle);
	}

	protected void startGenreActivity(Context context, GenreData item,
			String searchGenreTitle) {
		final Intent intent = new Intent(context, YahooShopGenreActivity.class);
		intent.putExtra(Const.INTENT_GENRE_KEY_ID, item.id);
		intent.putExtra(Const.INTENT_GENRE_KEY_TITLE, searchGenreTitle + " > "
				+ item.name);
		intent.putExtra(Const.INTENT_QUERY_RANKING_KEY, false);
		context.startActivity(intent);
	}
}
