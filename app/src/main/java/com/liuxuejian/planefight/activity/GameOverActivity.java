package com.liuxuejian.planefight.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.SQLiteDB.PlaneFightDB;
import com.liuxuejian.planefight.entity.Advance_Score;
import com.liuxuejian.planefight.entity.Endless_Score;
import com.liuxuejian.planefight.entity.Hell_Score;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GameOverActivity extends BaseActivity {
	private ArrayList<Advance_Score> sclist = new ArrayList<Advance_Score>();
	private PlaneFightDB db = new PlaneFightDB(this).getInstance();
	private ListView scorelv;
	private TextView scoretextview;
	private EditText nameet;
	private int MODE;// 模式种类
	private int score;
	private int advance_level;
	private ImageView ranking;
	private List<Advance_Score> aslist = new ArrayList<Advance_Score>();
	private List<Endless_Score> eslist = new ArrayList<Endless_Score>();
	private List<Hell_Score> hslist = new ArrayList<Hell_Score>();
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		ranking = (ImageView) findViewById(R.id.ranking);
		scoretextview = (TextView) findViewById(R.id.scoretextview);
		nameet = (EditText) findViewById(R.id.nameet);

		intent = getIntent();
		MODE = intent.getIntExtra("mode", 0);
		score = intent.getIntExtra("score", 0);
		scoretextview.setText(score + "");

		pictureshow();
		initFinishReceiver();
	}

	// 判断显示哪张抬头图片
	private void pictureshow() {
		if (MODE == 1) {// 闯关模式
			aslist = db.SelectAs();
			int last = aslist.size();
			if (aslist.size() > 9) {
				if (score >= aslist.get(last - 1).getScore()) {
					ranking.setBackgroundResource(R.drawable.refurbishranking);
				} else {
					ranking.setBackgroundResource(R.drawable.sorry_again);
					nameet.setVisibility(View.INVISIBLE);
				}
			}
		} else if (MODE == 2) {// 无尽模式
			eslist = db.SelectEs();
			int last = eslist.size();
			if (eslist.size() > 9) {
				if (score >= eslist.get(last - 1).getScore()) {
					ranking.setBackgroundResource(R.drawable.refurbishranking);
				} else {
					ranking.setBackgroundResource(R.drawable.sorry_again);
					nameet.setVisibility(View.INVISIBLE);
				}
			}
		} else if (MODE == 3) {// 地狱模式
			eslist = db.SelectEs();
			int last = hslist.size();
			if (hslist.size() > 9) {
				if (score >= hslist.get(last - 1).getScore()) {
					ranking.setBackgroundResource(R.drawable.refurbishranking);
				} else {
					ranking.setBackgroundResource(R.drawable.sorry_again);
					nameet.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	// 判断是否将数据插入数据库
	private void insertdb() {
		if (MODE == 1) {// 闯关模式
			String name = nameet.getText().toString();
			Advance_Score as = new Advance_Score();
			as.setPlayer_name(name);
			as.setScore(score);
			as.setAdvance_level(advance_level);

			int last = aslist.size();
			if (aslist.size() > 9) {
				if (score >= aslist.get(last - 1).getScore()) {
					db.InsertAs(as);
				}
			} else if (aslist.size() < 9) {
				db.InsertAs(as);
			}
		} else if (MODE == 2) {// 无尽模式
			String name = nameet.getText().toString();
			Endless_Score es = new Endless_Score();
			es.setPlayer_name(name);
			es.setScore(score);
			int last = eslist.size();
			if (eslist.size() > 9) {
				if (score >= eslist.get(last - 1).getScore()) {
					db.InsertEs(es);
				}
			} else if (eslist.size() < 9) {
				db.InsertEs(es);
			}
		} else if (MODE == 3) {// 地狱模式
			String name = nameet.getText().toString();
			Endless_Score es = new Endless_Score();
			es.setPlayer_name(name);
			es.setScore(score);
			int last = hslist.size();
			if (hslist.size() > 9) {
				if (score >= hslist.get(last - 1).getScore()) {
					db.InsertEs(es);
				}
			} else if (hslist.size() < 9) {
				db.InsertEs(es);
			}
		}
	}

	public void onclick(View v) {
		switch (v.getId()) {
		case R.id.playagain:
			insertdb();
			Intent in = new Intent(this, NewGameActivity.class);
			in.putExtra("mode", MODE);
			startActivity(in);
			break;
		case R.id.gobackmain:
			insertdb();
			startActivity(new Intent(this, MainActivity.class));
			break;
		case R.id.toranking:
			insertdb();
			startActivity(new Intent(this, RankingListActivity.class));
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mFinishReceiver);
		super.onDestroy();
	}
	//设置返回键不可点
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
}
