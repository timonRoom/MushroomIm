package com.example.mushroomim.control.activity;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient.ConversationType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class MessagePushReceiver extends PushMessageReceiver  {
//	private Context context;
	private  NotificationManager manger;
//	
//	public MessagePushReceiver(Context context, NotificationManager manger) {
//		super();
//		this.context = context;
//		this.manger = manger;
//	}
	@Override
	public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
		Log.e("onNotificationMessageArrived", message.toString());
		Log.e("SenderId", message.getSenderId());
		Log.e("PushContent", message.getPushContent());
		Log.e("PushId", message.getPushId());
		Log.e("PushTitle", message.getPushTitle());
		Log.e("SenderId", message.getSenderId());
		Log.e("SenderName", message.getSenderName());
		Log.e("TargetId", message.getTargetId());
		Log.e("TargetUserName", message.getTargetUserName());
		Log.e("ReceivedTime", message.getReceivedTime()+"");
		// 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义
		shownotification(context,message);
		return true;
	}
	@SuppressLint("NewApi")
	private void shownotification(Context context, PushNotificationMessage message) {
		Intent intent = new Intent();
//		
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		Uri.Builder builder = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon();
//
//		builder.appendPath("conversation").appendPath(message.getConversationType().getName())
//		        .appendQueryParameter("targetId", message.getTargetId())
//		        .appendQueryParameter("title", message.getTargetUserName());
//		Uri uri = builder.build();
//		intent.setData(uri);
	       intent.setClass(context, LoadingActivity.class);
//	       intent.addCategory("android.intent.category.DEFAULT");
//	       intent.setAction("android.intent.action.VIEW");
	       String name  = message.getTargetUserName();
	    		   if (name==null||name.equals("")==true) {
					name=message.getTargetId();
				}
	       intent.putExtra("TargetId", message.getTargetId());
	       intent.putExtra("title", message.getTargetUserName());
	       intent.putExtra("isnotification", true);
		 PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	       Notification notification = new Notification.Builder(context)
	    		   .setSmallIcon(com.example.mushroomim.R.drawable.notification)
	    		   .setContentTitle("新消息")
	    		   .setContentText(name+"发来消息")
	    		   .setContentIntent(pIntent)
	    		   .build();
	       manger =   (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;
	       manger.notify(1, notification);
	}
	@Override
	public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
		RongIM.getInstance().startPrivateChat(context, message.getPushId(), message.getPushTitle());
		removnotification();
		return true;
	}
	private void removnotification() {
		manger.cancel(1);
	}

}
