package jp.ksjApp.yahooshopcategory.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.data.GenreData;
import jp.ksjApp.yahooshopcategory.parser.YahooShopGenreParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class YahooShopGenreApiTask extends AbstractTaskApi {

	@SuppressWarnings("unused")
	private static final String TAG = YahooShopGenreApiTask.class.getSimpleName();

	@Override
	protected String createUrl(String genreId) {
		final StringBuffer strbuf = new StringBuffer();
		strbuf.append(Const.YAHOO_SHOP_GENRE_API_URL);
		strbuf.append("&");
		strbuf.append("category_id=" + genreId);
		if(Const._DEBUG_){
			Log.d(TAG, "url : " + strbuf.toString());
		}
		return strbuf.toString();
	}
}
