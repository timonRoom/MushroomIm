package com.example.mushroomim.control.activity;


import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LanuchActivity extends Activity{
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacksAndMessages(null);
	}
	private int LANUCH = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
	           finish();
	           return;
	       }
		setContentView(com.example.mushroomim.R.layout.launch);
		handler.sendEmptyMessageDelayed(LANUCH, 2000);

	}
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			new Thread(new Runnable() {

				@Override
				public void run() {
					
//					if (EMClient.getInstance().isLoggedInBefore()) {
//						Intent intent =  new Intent();
//						intent.setClass(LanuchActivity.this, MainActivity.class);
//						startActivity(intent);
//					}else {
						Intent intent =  new Intent();
						intent.setClass(LanuchActivity.this, LoadingActivity.class);
						startActivity(intent);
//					}
					finish();
				}
			}).start();

		}

	};

}
