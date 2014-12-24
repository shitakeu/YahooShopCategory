package jp.ksjApp.yahooshopcategory.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.data.GenreData;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * YAHOOジャンル検索API 用Parser
 * 
 * @author shitakeu
 * 
 */
public class YahooShopGenreParser {

	@SuppressWarnings("unused")
	private static final String TAG = YahooShopGenreParser.class.getSimpleName();

	public ArrayList<GenreData> xmlParser(InputStream is) {
		XmlPullParser parser = null;
		final ArrayList<GenreData> result = new ArrayList<GenreData>();
		GenreData currentMsg = null;
		parser = Xml.newPullParser();
		try {
			parser.setInput(is, null);

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {

				String tag = null;
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;

				case XmlPullParser.START_TAG:
					tag = parser.getName();
					if (tag.equals("Child")) {
						currentMsg = new GenreData();
					} else if (currentMsg != null) {
						if (tag.equals("Id")) {
							currentMsg.id = parser.nextText();
						} else if (tag.equals("Short")) {
							currentMsg.name = parser.nextText();
						}
					}
					break;

				case XmlPullParser.END_TAG:
					tag = parser.getName();
					if (tag.equals("Child")) {
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
