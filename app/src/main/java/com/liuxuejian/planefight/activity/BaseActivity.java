package com.liuxuejian.planefight.activity;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BaseActivity extends Activity {
	/** 退出广播对应的ACTION*/
	public final static String INIENT_FINISH = "allfinish";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化退出广播
	 */
	public void initFinishReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(INIENT_FINISH);
		registerReceiver(mFinishReceiver, filter);
	}
	
	/**
	 * 监听是否退出的广播
	 */
	public BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (INIENT_FINISH.equals(intent.getAction())) {
				finish();
			}
		}
	};
}	
