package com.liuxuejian.planefight.activity;

import com.liuxuejian.planefight.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ModeSelectActivity extends BaseActivity implements OnClickListener{
	private TextView advance_btn,endless_btn,hell_btn,mode_tv;
	private View v11,v12,v21,v22,v31,v32;
	private Button start;
	private int mode = 1;//默认选择闯关模式
	private boolean musiccheck,shockcheck;//音效，震动 默认开启
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mde_select);
		Intent intent =getIntent();
		musiccheck = intent.getBooleanExtra("musiccheck", true);
		shockcheck = intent.getBooleanExtra("shockcheck", true);
		initview();
		initFinishReceiver();
	}

	private void initview() {
		// TODO Auto-generated method stub
		mode_tv = (TextView)findViewById(R.id.mode_tv);
		advance_btn = (TextView)findViewById(R.id.advance_btn);
		advance_btn.setOnClickListener(this);
		endless_btn = (TextView)findViewById(R.id.endless_btn);
		endless_btn.setOnClickListener(this);
		hell_btn = (TextView)findViewById(R.id.hell_btn);
		hell_btn.setOnClickListener(this);
		start = (Button)findViewById(R.id.start);
		start.setOnClickListener(this);
		v11 = (View)findViewById(R.id.v11);
		v12 = (View)findViewById(R.id.v12);
		v21 = (View)findViewById(R.id.v21);
		v22 = (View)findViewById(R.id.v22);
		v31 = (View)findViewById(R.id.v31);
		v32 = (View)findViewById(R.id.v32);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
			Intent intent = new Intent(ModeSelectActivity.this,NewGameActivity.class);
			intent.putExtra("musiccheck", musiccheck);
			intent.putExtra("shockcheck", shockcheck);
			intent.putExtra("mode", mode);
			startActivity(intent);
			break;
		case R.id.advance_btn:
			mode_tv.setText("在这个模式里，你将面对强大的BOSS，运用你的智慧和勇气去击败它吧！");
			v11.setVisibility(View.VISIBLE);
			v12.setVisibility(View.VISIBLE);
			v21.setVisibility(View.GONE);
			v22.setVisibility(View.GONE);
			v31.setVisibility(View.GONE);
			v32.setVisibility(View.GONE);
			mode = 1;
			break;
		case R.id.endless_btn:
			mode_tv.setText("在这个模式里，你将面对无穷无尽的敌人，别想太多，争取更长的生存时间！");
			v11.setVisibility(View.GONE);
			v12.setVisibility(View.GONE);
			v21.setVisibility(View.VISIBLE);
			v22.setVisibility(View.VISIBLE);
			v31.setVisibility(View.GONE);
			v32.setVisibility(View.GONE);
			mode = 2;
			break;
		case R.id.hell_btn:
			mode_tv.setText("在这个模式里，你将面对面对最疯狂的敌人，坚持到最后一刻才是勇士！");
			v11.setVisibility(View.GONE);
			v12.setVisibility(View.GONE);
			v21.setVisibility(View.GONE);
			v22.setVisibility(View.GONE);
			v31.setVisibility(View.VISIBLE);
			v32.setVisibility(View.VISIBLE);
			mode = 3;
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mde_select, menu);
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
