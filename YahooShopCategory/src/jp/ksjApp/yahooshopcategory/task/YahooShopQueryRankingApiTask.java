package jp.ksjApp.yahooshopcategory.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.data.ItemData;
import jp.ksjApp.yahooshopcategory.data.QueryRankingData;
import jp.ksjApp.yahooshopcategory.parser.YahooShopQueryRankingParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

/**
 * YahooショッピングランキングAPI
 * 
 * @author mtb_cc_sin5
 * 
 */
public class YahooShopQueryRankingApiTask extends
		AsyncTask<String, Void, ArrayList<QueryRankingData>> {

	private static final String TAG = YahooShopQueryRankingApiTask.class.getSimpleName();

	public YahooShopQueryRankingApiTask() {
	}

	@Override
	protected ArrayList<QueryRankingData> doInBackground(String... params) {
		return requestApi();
	}

	private ArrayList<QueryRankingData> requestApi() {

		ArrayList<QueryRankingData> data = null;
		final HttpClient httpClient = new DefaultHttpClient();
		try {
			final HttpGet request = new HttpGet(createApiUrl());
			final HttpResponse httpResponse = httpClient.execute(request);
			final InputStream in = httpResponse.getEntity().getContent();
			final YahooShopQueryRankingParser parser = new YahooShopQueryRankingParser();
			data = parser.xmlParser(in);
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
		strbuf.append(Const.YAHOO_SHOP_QUERY_RANKING_API_URL);
		if(Const._DEBUG_){
			Log.d(TAG, strbuf.toString());
		}
		return strbuf.toString();
	}

}
