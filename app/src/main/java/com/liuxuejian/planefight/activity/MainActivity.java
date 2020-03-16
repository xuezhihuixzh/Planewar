package com.liuxuejian.planefight.activity;

/**
 * 
 * @author bruce.liu
 * @version 1.0
 * @time 2015/4/07/13.45
 * 
 */
import com.liuxuejian.planefight.R;
import com.liuxuejian.planefight.view.Abvanceview;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseActivity {
	private boolean musiccheck, shockcheck;// 音效，震动 默认开启
	private Abvanceview myview;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFinishReceiver();
	}

	public void mybtn(View v) {
		switch (v.getId()) {
		case R.id.modeselect:
			Intent intent = new Intent(this, ModeSelectActivity.class);
			intent.putExtra("musiccheck", musiccheck);
			intent.putExtra("shockcheck", shockcheck);
			startActivity(intent);
			break;
		case R.id.ranking:
			startActivity(new Intent(this, RankingListActivity.class));
			break;
		case R.id.gamesetting:
			startActivityForResult(new Intent(this, GameSetActivity.class), 1);
			break;
		case R.id.exit:
//			sendFinish();
			System.exit(0);
			break;
		}
	}

	// 处理返回的选项设置
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			musiccheck = data.getBooleanExtra("musiccheck", true);
			shockcheck = data.getBooleanExtra("shockcheck", true);
		}
	}

	/**
	 * 发送广播退出多个Activity
	 */
	private void sendFinish() {
		getApplicationContext().sendBroadcast(new Intent(INIENT_FINISH));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
