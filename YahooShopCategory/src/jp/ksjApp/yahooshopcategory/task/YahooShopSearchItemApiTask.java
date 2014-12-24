package jp.ksjApp.yahooshopcategory.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.data.ItemData;
import jp.ksjApp.yahooshopcategory.parser.YahooShopQueryRankingParser;
import jp.ksjApp.yahooshopcategory.parser.YahooShopSearchItemParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * Yahoo商品検索API
 * 
 * @author mtb_cc_sin5
 * 
 */
public class YahooShopSearchItemApiTask extends
		AsyncTask<String, Void, ArrayList<ItemData>> {

	private static final String TAG = YahooShopSearchItemApiTask.class.getSimpleName();

	private String mSearchWord = "";
	private String mSearchGenre = "0";
	private int mOffset = 0;

	public YahooShopSearchItemApiTask(String searchWord, String searchGenre, int page) {
		mSearchWord = searchWord;
		mSearchGenre = searchGenre;
		mOffset = page;
	}

	@Override
	protected ArrayList<ItemData> doInBackground(String... params) {
		return requestApi();
	}

	private ArrayList<ItemData> requestApi() {

		ArrayList<ItemData> data = null;
		final HttpClient httpClient = new DefaultHttpClient();
		try {
			final HttpGet request = new HttpGet(createApiUrl());
			final HttpResponse httpResponse = httpClient.execute(request);
			final InputStream in = httpResponse.getEntity().getContent();
			final YahooShopSearchItemParser rakutenParser = new YahooShopSearchItemParser();
			data = rakutenParser.xmlParser(in);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private String createApiUrl() {
		final StringBuffer strbuf = new StringBuffer();

		strbuf.append(Const.YAHOO_SHOP_SEARCH_API_URL);
		if (!TextUtils.isEmpty(mSearchWord)) {
			strbuf.append("&");
			strbuf.append("query=" + URLEncoder.encode(mSearchWord));
		}
		strbuf.append("&");
		strbuf.append("category_id=" + URLEncoder.encode(mSearchGenre));
		strbuf.append("&");
		final int offset = mOffset * Const.YAHOO_SHOP_SEARCH_OSSSET_MAX;
		strbuf.append("offset=" + offset);
		if (Const._DEBUG_) {
			Log.d(TAG, strbuf.toString());
		}

		return strbuf.toString();
	}

}
