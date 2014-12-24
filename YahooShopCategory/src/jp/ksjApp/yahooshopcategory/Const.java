package jp.ksjApp.yahooshopcategory;

public class Const {

	public static final boolean _DEBUG_ = true;

	public static final int API_TIMEOUT = 30000;

	/**
	 * API種別
	 */
	public static final int TASK_ID_YAHOO_SHOP_GENRE = 0;
	
	public static final int TASK_ID_YAHOO_SHOP_SEARCH = 1;
	
	/**
	 * YahooAPI関連
	 */
	public static final String YAHOO_APP_ID = "dj0zaiZpPTFVVms2TVk5a3pNQSZzPWNvbnN1bWVyc2VjcmV0Jng9NzU-";
	public static final String YAHOO_AFFILIATEI_ID = "onlgl89gRKBbvvpOFlOZ";

	// Yahooショッピングジャンル検索API
	// http://developer.yahoo.co.jp/webapi/shopping/shopping/v1/categorysearch.html
	public static final String YAHOO_SHOP_GENRE_API_URL = "http://shopping.yahooapis.jp/ShoppingWebService/V1/categorySearch?affiliate_type=yid&appid="
			+ YAHOO_APP_ID + "&affiliate_id=" + YAHOO_AFFILIATEI_ID;

	// Yahoo商品検索API
	// http://developer.yahoo.co.jp/webapi/shopping/shopping/v1/itemsearch.html
	public static final String YAHOO_SHOP_SEARCH_API_URL = "http://shopping.yahooapis.jp/ShoppingWebService/V1/itemSearch?affiliate_type=yid&hits=50&appid="
			+ YAHOO_APP_ID + "&affiliate_id=" + YAHOO_AFFILIATEI_ID;

	// YahooキーワードランキングAPI
	// http://developer.yahoo.co.jp/webapi/shopping/shopping/v1/itemsearch.html
	public static final String YAHOO_SHOP_QUERY_RANKING_API_URL = "http://shopping.yahooapis.jp/ShoppingWebService/V1/queryRanking?affiliate_type=yid&appid="
			+ YAHOO_APP_ID + "&affiliate_id=" + YAHOO_AFFILIATEI_ID;

	// 件数
	public static final int YAHOO_SHOP_SEARCH_OSSSET_MAX = 50;

	// Intent Key
	public static final String INTENT_KEY_SEARCH_WORD = "search_word";
	public static final String INTENT_KEY_SEARCH_GENRE_ID = "search_genre_id";
	public static final String INTENT_KEY_SEARCH_GENRE_TITLE = "search_genre_title";

	public static final String INTENT_GENRE_KEY_ID = "intent_genre_id";
	public static final String INTENT_GENRE_KEY_TITLE = "intent_genre_title";
	public static final String INTENT_QUERY_RANKING_KEY = "intent_query_ranking";
}
