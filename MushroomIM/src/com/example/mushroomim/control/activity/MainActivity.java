package com.example.mushroomim.control.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.Utils.SpUtils;
import com.example.mushroomim.control.fragment.ContactFragment;
import com.example.mushroomim.control.fragment.GroupFragment;
import com.example.mushroomim.control.fragment.SetFragment;
import com.example.mushroomim.mode.MessgeReciverListener;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.ConversationListBehaviorListener;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.OnReceiveMessageListener;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener ,ConversationListBehaviorListener, android.content.DialogInterface.OnClickListener{
	

	private RadioGroup fg_mian_item;
	private List<Fragment> fragmentlist;
	private FrameLayout  fl_main;
	private   int position=0;
	public  static  String userId ;
	private String AUTHOR = "782636200";
	private ImageView img_search;
	private View fg_contact_list;
	private SetFragment setFragment;
	private Fragment mConversationFragment = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("mainactivity", "onCreate");
		setContentView(R.layout.activity_main);
		fg_mian_item = (RadioGroup) findViewById(R.id.fg_mian_item);
		fg_mian_item.check(R.id.rb_message);
		fg_mian_item.setOnCheckedChangeListener(this);
		fragmentlist = new ArrayList<Fragment>();
		img_search = (ImageView) findViewById(R.id.img_search);
		img_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setToFragment(1);
				fg_mian_item.check(R.id.rb_contact);
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AddContactActivity.class);
				intent.putExtra("userId", userId);
				startActivity(intent);
				
			}
		});
		inindata();
		initfragment();
		position = 0;
		setFragment();
		if (getIntent().getBooleanExtra("isnotification", false)) {
			NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.cancelAll();
			RongIM.getInstance().startPrivateChat(this, getIntent().getStringExtra("TargetId"), getIntent().getStringExtra("title"));
		}
	}
	private void inindata() {
		userId = getIntent().getStringExtra("userId");
		new SpUtils().putContact(getApplicationContext(),userId,ContactFragment.DATANAME_CONTACT,AUTHOR);
		
//		RongIM.setUserInfoProvider(new UserInfoProvider() {
//			@Override
//			public UserInfo getUserInfo(String userId) {
//				String path = new DbManger(getApplicationContext()).queryportraitUri(userId);
//				Uri portraitUri = null;
//				String nikename = null;
//				if (path!=null&&!path.equals("")) {
//					
//					 portraitUri = Uri.parse(path);
//					 nikename = new DbManger(getApplicationContext()).querynikename(userId);
//				}
//				Log.e("getUserInfo", nikename);
//				return new UserInfo(userId, nikename, portraitUri);
//			}
//		}, true);
		//设置消息阅读回执类型
		Conversation.ConversationType[] types = new Conversation.ConversationType[] {
			    Conversation.ConversationType.PRIVATE,
			    Conversation.ConversationType.GROUP,
			    Conversation.ConversationType.DISCUSSION
			};
			RongIM.getInstance().setReadReceiptConversationTypeList(types);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {  
			moveTaskToBack(true);
			Log.e("onKeyDown", "back");  
            return true;   
            }
		else {
			return super.onKeyDown(keyCode, event);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("MainActivity", "onDestroy");  
		RongIM.getInstance().disconnect();
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		Log.e("MainActivity","onActivityResult");
		setFragment.onActivityResult(arg0, arg1, arg2);
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("MainActivity","onPause");
		RongIM.setOnReceiveMessageListener(new MessgeReciverListener(this));
		
		}
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e("MainActivity","onRestart");
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancelAll();
		Log.e("MainActivity","onResume");
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.e("MainActivity","onBackPressed");
//		moveTaskToBack(false);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("MainActivity","onStop");
	}
	private void initfragment() {
		mConversationFragment = initConversationList();
		if (mConversationFragment!=null) {
			fragmentlist.add(mConversationFragment);
		}
		 setFragment = new SetFragment();
		ContactFragment contactFragment = new ContactFragment();
//		GroupFragment gFragment = new GroupFragment();
		fragmentlist.add(contactFragment);
//		fragmentlist.add(gFragment);
		fragmentlist.add(setFragment);
		
	}
	private void setFragment() {
		//得到fragmentmanger对象
				FragmentManager manager = getSupportFragmentManager();
				//开启事物
				FragmentTransaction beginTransaction = manager.beginTransaction();
				//替换
				beginTransaction.replace(R.id.fl_mian, fragmentlist.get(position)).commit();
				//提交事物
	}
	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch (id) {
		case R.id.rb_message:
			position = 0;
			setFragment();
			break;
		case R.id.rb_contact:
			position = 1;
			setFragment();
			break;
//		case R.id.rb_group:
//			position = 2;
//			setFragment();
//			break;
		case R.id.rb_discussgroup:
			position = 2;
			setFragment();
			break;
		}
	}
	private Fragment  initConversationList(){  
		Fragment fragment = null;
	    if (mConversationFragment == null) {  
	        ConversationListFragment listFragment = new ConversationListFragment();
	        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()  
	                .appendPath("conversationlist")  
	                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示  
	                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组  
	                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//讨论组  
	                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号  
	                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号  
	                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//系统  
	                .build();  
	        listFragment.setUri(uri);
	        fragment = listFragment;
	        Log.e("initConversationList>>>>>>", fragment+"");
	        return listFragment;  
	    } else {  
	    	fragment = mConversationFragment;
	        return  fragment;  
	    }
	}
	
	/**
	 * 会话列表事件监听
	 * */
	//点击会话列表中的 item 时执行。
	@Override
	public boolean onConversationClick(Context arg0, View arg1, UIConversation arg2) {
		return false;
	}
	//长按会话列表中的 item 时执行。
	@Override
	public boolean onConversationLongClick(Context arg0, View arg1, UIConversation arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	//当点击会话头像后执行。
	@Override
	public boolean onConversationPortraitClick(Context arg0, ConversationType arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	//当长按会话头像后执行。
	@Override
	public boolean onConversationPortraitLongClick(Context arg0, ConversationType arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	public   void setToFragment(int i) {
		position = i;
		setFragment();
	}
//	private void showfinshDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("提示");
//		builder.setPositiveButton("确定", this);
//		builder.setNegativeButton("取消", this);
//		builder.show();
//	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
	}
}
