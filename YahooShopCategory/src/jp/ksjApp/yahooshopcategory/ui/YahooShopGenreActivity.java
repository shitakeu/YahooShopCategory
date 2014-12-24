package jp.ksjApp.yahooshopcategory.ui;

import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.data.GenreData;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;

public class YahooShopGenreActivity extends GenreActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void startRankingItemGridActivity() {
		final Intent intent = new Intent(YahooShopGenreActivity.this,
				YahooShopItemGridActivity.class);
		intent.putExtra(Const.INTENT_KEY_SEARCH_GENRE_ID, mSearchGenreId);
		intent.putExtra(Const.INTENT_KEY_SEARCH_GENRE_TITLE, mSearchGenreTitle);
		startActivity(intent);
	}

	protected void startSearchItemGridActivity(String searchWord) {
		final Intent intent = new Intent(YahooShopGenreActivity.this,
				YahooShopItemGridActivity.class);
		intent.putExtra(Const.INTENT_KEY_SEARCH_WORD, searchWord);
		intent.putExtra(Const.INTENT_KEY_SEARCH_GENRE_ID, mSearchGenreId);
		intent.putExtra(Const.INTENT_KEY_SEARCH_GENRE_TITLE, mSearchGenreTitle);
		intent.putExtra(Const.INTENT_QUERY_RANKING_KEY, false);
		startActivity(intent);
	}

	@Override
	protected GenreAdapter getAdapter(Context c,
			ArrayList<GenreData> genreData,
			String searchGenreTitle) {
	final YahooShopGenreAdapter adapter = new YahooShopGenreAdapter(c, genreData, searchGenreTitle);
	return adapter;
	};
}
