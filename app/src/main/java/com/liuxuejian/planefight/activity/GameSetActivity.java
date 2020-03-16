package com.liuxuejian.planefight.activity;

import com.liuxuejian.planefight.R;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class GameSetActivity extends BaseActivity implements OnClickListener {
	private CheckBox musiccheckbox, shockcheckbox;
	private Button maker, goback;
	private boolean musiccheck, shockcheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_set);
		musiccheckbox = (CheckBox) findViewById(R.id.musiccheckbox);
		shockcheckbox = (CheckBox) findViewById(R.id.shockcheckbox);
		maker = (Button) findViewById(R.id.maker);
		goback = (Button) findViewById(R.id.goback);
		maker.setOnClickListener(this);
		goback.setOnClickListener(this);
		initFinishReceiver();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.maker:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = LayoutInflater.from(this).inflate(R.layout.dialogview,
					null);
			builder.setView(view);
			final AlertDialog dialog = builder.create();
			Button close_btn = (Button) view.findViewById(R.id.close_btn);
			// 获取屏幕宽高
			WindowManager wm = this.getWindowManager();
			double dwidth = wm.getDefaultDisplay().getWidth() * 0.9;
			int width = (int) dwidth;
			double dheight = wm.getDefaultDisplay().getHeight() * 0.7;
			int height = (int) dheight;
			dialog.show();
			dialog.getWindow().setLayout(width, height);
			close_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			break;
		case R.id.goback:
			musiccheck = musiccheckbox.isChecked();
			shockcheck = shockcheckbox.isChecked();
			Intent intent = new Intent();
			intent.putExtra("musiccheck", musiccheck);
			intent.putExtra("shockcheck", shockcheck);
			setResult(1, intent);
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_set, menu);
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

	// 在返回上一个Activity时，如调用onActivityResult，必须返回一个requestcode值
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		setResult(0, intent);
		super.onBackPressed();
	}
}
