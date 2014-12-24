package jp.ksjApp.yahooshopcategory.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import jp.ksjApp.yahooshopcategory.data.GenreData;
import jp.ksjApp.yahooshopcategory.parser.YahooShopGenreParser;

import android.os.AsyncTask;

public abstract	class AbstractTaskApi extends AsyncTask<String, Void, ArrayList<GenreData>> {

	public AbstractTaskApi() {
	}	
	
	@Override
	protected ArrayList<GenreData> doInBackground(String... params) {
		return requestApi(params[0]);
	}
	
	protected ArrayList<GenreData> requestApi(String genreId){
		ArrayList<GenreData> data = null;
		final HttpClient httpClient = new DefaultHttpClient();
		try {
			final HttpGet request = new HttpGet(createUrl(genreId));
			final HttpResponse httpResponse = httpClient.execute(request);
			final InputStream in = httpResponse.getEntity().getContent();
			final YahooShopGenreParser rakutenParser = new YahooShopGenreParser();
			data = rakutenParser.xmlParser(in);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	protected abstract  String createUrl(String genreId);
}
