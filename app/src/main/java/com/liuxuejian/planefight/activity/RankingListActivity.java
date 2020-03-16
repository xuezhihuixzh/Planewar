package com.liuxuejian.planefight.activity;

import java.util.ArrayList;
import java.util.List;

import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.SQLiteDB.PlaneFightDB;
import com.liuxuejian.planefight.adapter.GalleryAdapter;
import com.liuxuejian.planefight.entity.Advance_Score;
import com.liuxuejian.planefight.entity.Endless_Score;
import com.liuxuejian.planefight.entity.Hell_Score;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;

public class RankingListActivity extends BaseActivity {
	private Gallery gallery;
	private String[] num = { "无尽模式", "闯关模式", "地狱模式" };
	private PlaneFightDB db = new PlaneFightDB(this).getInstance();
	private List<Advance_Score> aslist = new ArrayList<Advance_Score>();
	private List<Endless_Score> eslist = new ArrayList<Endless_Score>();
	private List<Hell_Score> hslist  = new ArrayList<Hell_Score>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_list);
		aslist = db.SelectAs();
		eslist = db.SelectEs();
		hslist = db.SelectHs();
		gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new GalleryAdapter(this, num,eslist,aslist,hslist));
		gallery.setSelection(1);

		
		initFinishReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ranking_list, menu);
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
}




