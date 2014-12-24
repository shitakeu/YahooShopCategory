package jp.ksjApp.yahooshopcategory.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.data.QueryRankingData;
import jp.ksjApp.yahooshopcategory.data.ShopData;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/**
 * ヤフーショッピング検索急上昇ランキングAPI 用Parser
 * 
 * @author shitakeu
 * 
 */
public class YahooShopQueryRankingParser {

	@SuppressWarnings("unused")
	private static final String TAG = YahooShopQueryRankingParser.class.getSimpleName();
	
	public ArrayList<QueryRankingData> xmlParser(InputStream is) { 
		XmlPullParser parser = null;
		final ArrayList<QueryRankingData> result = new ArrayList<QueryRankingData>();
		QueryRankingData currentMsg = null;

		try {
			parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(is, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					tag = parser.getName();
					if (tag.equals("QueryRankingData")) {
						currentMsg = new QueryRankingData();
					} else if (currentMsg != null) {
						if (tag.equals("Query")) {
							currentMsg.name = parser.nextText();
						} else if (tag.equals("Url")) {
							currentMsg.url = parser.nextText();
						}
					}
					break;

				case XmlPullParser.END_TAG:
					tag = parser.getName();
					if (tag.equals("QueryRankingData")) {

						result.add(currentMsg);
						currentMsg = null;
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			Log.e(TAG, "XmlPullParserException : " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(TAG, "IOException : " + e.getMessage());
			return null;
		}
		return result;
	}

}
