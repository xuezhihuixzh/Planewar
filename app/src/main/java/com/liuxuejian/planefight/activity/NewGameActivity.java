package com.liuxuejian.planefight.activity;
/**
 * 
 * @author bruce.liu
 * @version 1.0
 * @time 2015/4/09/10.33
 * 
 */
import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.view.Abvanceview;
import com.liuxuejian.planefight.view.EndlessView;
import com.liuxuejian.planefight.view.HellView;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class NewGameActivity extends BaseActivity {
	private Abvanceview abvanceview;
	private EndlessView endview;
	private HellView hellview;
	private boolean musiccheck,shockcheck;//音效，震动 默认开启
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		int mode =intent.getIntExtra("mode",0);
		musiccheck = intent.getBooleanExtra("musiccheck", true);
		shockcheck = intent.getBooleanExtra("shockcheck", true);
		abvanceview  = new Abvanceview(this,musiccheck,shockcheck);
		endview = new EndlessView(this,musiccheck,shockcheck); 
		hellview = new HellView(this, musiccheck, shockcheck);
		//根据模式选择View
		if (mode ==1) {
			setContentView(abvanceview);
		}else if(mode ==2){
			setContentView(endview);
		}else if(mode ==3){
			setContentView(hellview);
		}
		initFinishReceiver();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_game, menu);
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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mFinishReceiver);
		super.onDestroy();
	}
}
