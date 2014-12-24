package jp.ksjApp.yahooshopcategory.ui;

import java.util.ArrayList;

import jp.ksjApp.yahooshopcategory.Const;
import jp.ksjApp.yahooshopcategory.R;
import jp.ksjApp.yahooshopcategory.Util;
import jp.ksjApp.yahooshopcategory.data.GenreData;
import jp.ksjApp.yahooshopcategory.data.QueryRankingData;
import jp.ksjApp.yahooshopcategory.task.YahooShopGenreApiTask;
import jp.ksjApp.yahooshopcategory.task.YahooShopQueryRankingApiTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class GenreActivity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = GenreActivity.class.getSimpleName();

	// 検索するジャンル
	protected String mSearchGenreId = "1";

	protected String mSearchGenreTitle = "すべて";
	
	protected BaseAdapter mAdapter;

	private GridView mGridView;

	private LinearLayout mQueryRankingLayout;

	private TextView mQueryRanking1;
	private TextView mQueryRanking2;
	private TextView mQueryRanking3;
	
	private boolean mQueryRankingFlag = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		setupView();
		requstGenre();
		requstQueryRanking();
	}

	private void init(){
		final String genre = getIntent().getStringExtra(Const.INTENT_GENRE_KEY_ID);
		if (genre != null) {
			mSearchGenreId = genre;
		}

		final String genreTitle = getIntent().getStringExtra(
				Const.INTENT_GENRE_KEY_TITLE);
		if (genreTitle != null) {
			mSearchGenreTitle = genreTitle;
		}
		setTitle(mSearchGenreTitle);
		mQueryRankingFlag = getIntent().getBooleanExtra(Const.INTENT_QUERY_RANKING_KEY,
				true);

		if (!Util.isNetworkAvailable(getApplicationContext())) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle("エラー");
			// アラートダイアログのメッセージを設定します
			alertDialogBuilder
					.setMessage("ネットワークに接続できません。¥n接続してから再度アプリを起動してください。");
			// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			alertDialogBuilder.create().show();
		}
	}
	
	private void setupView() {
		final EditText editText = (EditText) findViewById(R.id.edit_word);
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (event != null
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					if (event.getAction() == KeyEvent.ACTION_UP) {
						final String searchWord = editText.getText().toString();
						startSearchItemGridActivity(searchWord);
					}
					return true;
				}
				return false;
			}
		});


		mQueryRankingLayout = (LinearLayout) findViewById(R.id.layout_query_ranking);
		if (mQueryRankingFlag) {
			mQueryRankingLayout.setVisibility(View.VISIBLE);
			mQueryRanking1 = (TextView) mQueryRankingLayout
					.findViewById(R.id.txt_query_ranking1);
			mQueryRanking1.setOnClickListener(this);
			mQueryRanking2 = (TextView) mQueryRankingLayout
					.findViewById(R.id.txt_query_ranking2);
			mQueryRanking2.setOnClickListener(this);
			mQueryRanking3 = (TextView) mQueryRankingLayout
					.findViewById(R.id.txt_query_ranking3);
			mQueryRanking3.setOnClickListener(this);
		}else{
			mQueryRankingLayout.setVisibility(View.GONE);
		}

		findViewById(R.id.btn_ranking).setOnClickListener(this);
		findViewById(R.id.btn_search).setOnClickListener(this);
	}

	/**
	 * キーワードランキングAPI
	 */
	private void requstQueryRanking() {

		final YahooShopQueryRankingApiTask task = new YahooShopQueryRankingApiTask() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			public void onPostExecute(final ArrayList<QueryRankingData> result) {
				if (result == null || result.size() <= 0 || !mQueryRankingFlag) {
					mQueryRankingLayout.setVisibility(View.GONE);
				} else {
					mQueryRankingLayout.setVisibility(View.VISIBLE);
					final int size = result.size();
					for (int i = 0; i < size; i++) {
						if (i == 0) {
							mQueryRanking1.setText(result.get(i).name);
						} else if (i == 1) {
							mQueryRanking2.setText(result.get(i).name);
						} else if (i == 2) {
							mQueryRanking3.setText(result.get(i).name);
						} else {
							break;
						}
					}
				}
			}
		};
		task.execute();
	}

	/**
	 * ジャンル一覧を取得する
	 */
	private void requstGenre() {
		if (mGridView == null) {
			mGridView = (GridView) findViewById(R.id.grd_category);
		}

		final YahooShopGenreApiTask task = new YahooShopGenreApiTask() {
			private ProgressDialog mDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mDialog = new ProgressDialog(GenreActivity.this);
				mDialog.setTitle("通信中");
				mDialog.setMessage("商品情報取得中...");
				mDialog.show();
			}

			public void onPostExecute(final ArrayList<GenreData> result) {
				if (result == null) {
					final String[] list = { "通信失敗" };
					final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							GenreActivity.this,
							android.R.layout.simple_list_item_1, list);
					mGridView.setAdapter(adapter);
				} else if (result.size() <= 0) {
					// 現在のジャンル以下の子ジャンルはない
					startRankingItemGridActivity();
					finish();
				} else {
					mGridView.setAdapter(getAdapter(GenreActivity.this, result, mSearchGenreTitle));
					mGridView.setEnabled(true);
					mGridView
							.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
								@Override
								public void onItemSelected(
										AdapterView<?> parent, View view,
										int position, long id) {
									// 選択されたアイテムを取得します
									if (position != 0) {
										mSearchGenreId = result.get(position).id;
									}
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
								}
							});
				}
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}
		};
		task.execute(mSearchGenreId);
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		if (id == R.id.btn_search) {
			if (!Util.isNetworkAvailable(getApplicationContext())) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
				alertDialogBuilder.setTitle("エラー");
				// アラートダイアログのメッセージを設定します
				alertDialogBuilder
						.setMessage("ネットワークに接続できません。¥n接続してから再度検索してください。");
				// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
				alertDialogBuilder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				alertDialogBuilder.create().show();
				return;
			}

			final EditText editText = (EditText) findViewById(R.id.edit_word);
			final String searchWord = editText.getText().toString();
			startSearchItemGridActivity(searchWord);
		} else if (id == R.id.btn_ranking) {
			startRankingItemGridActivity();
		} else if (id == R.id.txt_query_ranking1) {
			final String searchWord = mQueryRanking1.getText().toString();
			startSearchItemGridActivity(searchWord);
		} else if (id == R.id.txt_query_ranking2) {
			final String searchWord = mQueryRanking2.getText().toString();
			startSearchItemGridActivity(searchWord);
		} else if (id == R.id.txt_query_ranking3) {
			final String searchWord = mQueryRanking3.getText().toString();
			startSearchItemGridActivity(searchWord);
		}
	}
	
	protected abstract void startSearchItemGridActivity(String searchWord);

	protected abstract void startRankingItemGridActivity();
	
	protected abstract GenreAdapter getAdapter(Context c, ArrayList<GenreData> genreData, String searchGenreTitle);

}
