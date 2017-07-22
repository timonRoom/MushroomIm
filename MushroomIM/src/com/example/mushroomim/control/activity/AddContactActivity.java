package com.example.mushroomim.control.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.SpUtils;
import com.example.mushroomim.control.fragment.ContactFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.OnSendMessageListener;
import io.rong.imkit.RongIM.SentMessageErrorCode;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.SendMessageCallback;
import io.rong.imlib.model.Message;
import io.rong.message.ContactNotificationMessage;

public class AddContactActivity extends Activity implements OnClickListener,OnSendMessageListener {
	private ImageView img_back;
	private EditText et_context;
	private Button btn_addcontact;
	private String userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchpager);
		initview();
		iniddata();
	}
	private void iniddata() {
		userId = getIntent().getStringExtra("userId");
	}
	private void initview() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		et_context = (EditText) findViewById(R.id.et_context);
		btn_addcontact = (Button) findViewById(R.id.btn_addcontact);
		btn_addcontact.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.btn_addcontact:
			String inputcontext = et_context.getText().toString();
			int result = new SpUtils().putContact(getApplicationContext(),userId,ContactFragment.DATANAME_CONTACT,inputcontext);
			Log.e("putContact", result+"");
			if (result==1) {
				finish();
			}
//			String [] strings = new ArrayList<String >();
//			if (getSharedPreference(userId)==null) {
//				 strings[0]= String.valueOf(inputcontext);
//			}else {
//				strings = getSharedPreference(userId);
//				strings[strings.length] = String.valueOf(inputcontext);
//			}
//			Log.e("strings", strings.toString());
//			setSharedPreference(userId, strings);
			if (inputcontext!=null&&!inputcontext.equals("")) {
				//{"operation":"Request","sourceUserId":"123","targetUserId":"456","message":"我是小艾，能加一下好友吗？","extra":""}
				//				String [] strings = {"{operation:"+ContactNotificationMessage.CONTACT_OPERATION_REQUEST,
				//						"sourceUserId:"+123,
				//						"targetUserId:"+456,
				//						"message:"+"我是小艾，能加一下好友吗？",
				//						"extra:"+"}"};
				//				 byte[] data = StringToByteArray(strings);
				////				message msg = new message(data);
				//				ContactNotificationMessage notificationMessage = new ContactNotificationMessage(data);
				Log.e("ContactNotificationMessage", "ContactNotificationMessage");
				ContactNotificationMessage obtain = ContactNotificationMessage.obtain(ContactNotificationMessage.CONTACT_OPERATION_REQUEST, 
						userId, 
						inputcontext,
						"hello");
				Log.e("ContactNotificationMessage", "ContactNotificationMessage");
				//				obtain.encode();
				ContactNotificationMessage contactNotificationMessage = new  ContactNotificationMessage(obtain.encode());
				Log.e("ContactNotificationMessage", "ContactNotificationMessage");

				//				RongIM.getInstance().sendMessage(obtain, null, null,  new IRongCallback.ISendMessageCallback(){

				//					@Override
				//					public void onAttached(Message arg0) {
				//						// TODO Auto-generated method stub
				//						
				//					}
				//
				//					@Override
				//					public void onError(Message arg0, ErrorCode arg1) {
				//						// TODO Auto-generated method stub
				//						
				//					}
				//
				//					@Override
				//					public void onSuccess(Message arg0) {
				//						// TODO Auto-generated method stub
				//						
				//					}
				//					
				//				});
			}
			break;
		}
	}
	//	class message extends ContactNotificationMessage{
	//
	//		@Override
	//		public String getExtra() {
	//			// TODO Auto-generated method stub
	//			return super.getExtra();
	//		}
	//		public message(byte[] arg0) {
	//			super(arg0);
	//		}
	//		
	//	}
	//字符数组转byte数组
	private  byte[] StringToByteArray(String[] str_ary) {
		int n = str_ary.length;
		byte[] bt_ary = new byte[n];
		for (int i = 0; i < n; i++)
			bt_ary[i] = (byte)Integer.parseInt(str_ary[i], 16) ;
		return bt_ary;
	}

	@Override
	public Message onSend(Message arg0) {
		Log.e("onSend", arg0.toString());
		return null;
	}
	@Override
	public boolean onSent(Message arg0, SentMessageErrorCode arg1) {
		Log.e("SentMessageErrorCode", arg1.toString());
		return false;
	}
	public String[] getSharedPreference(String key) {
		String regularEx = "#";
		String[] str = null;
		SharedPreferences sp =getSharedPreferences("data", Context.MODE_PRIVATE);
		String values;
		values = sp.getString(key, "");
		if (values.equals("")) {
			return str;
		}else {
			str = values.split(regularEx);
			return str;
		}
	}
	public void setSharedPreference(String key, String[] values) {
		String regularEx = "#";
		String str = "";
		SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
		if (values != null && values.length > 0) {
			for (String value : values) {
				str += value;
				str += regularEx;
			}
			Editor et = sp.edit();
			et.putString(key, str);
			et.commit();
		}
	}
}
