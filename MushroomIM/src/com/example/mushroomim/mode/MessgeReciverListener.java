package com.example.mushroomim.mode;

import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.control.activity.LoadingActivity;
import com.example.mushroomim.control.activity.MainActivity;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import io.rong.imlib.RongIMClient.OnReceiveMessageListener;
import io.rong.imlib.model.Message;
import io.rong.push.notification.PushNotificationMessage;

public class MessgeReciverListener implements OnReceiveMessageListener {
	private Context context;
	private NotificationManager manager;
	public MessgeReciverListener(Context context) {
		super();
		this.context = context;
		dbmanger= new DbManger(context);
		manager =   (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE) ;
	}
	private DbManger dbmanger ;
	@Override
	public boolean onReceived(Message message, int left) {
		Log.e("onReceived", message.getTargetId());
		shownotification(message);
		return false;
	}
	@SuppressLint("NewApi")
	private void shownotification(Message message) {
		Intent intent = new Intent();
	       String name  = dbmanger.querynikename(message.getTargetId());
	    		   if (name==null||name.equals("")==true) {
					name=message.getTargetId();
				}
	    		   intent.setClass(context, MainActivity.class);
//	       intent.putExtra("TargetId", message.getTargetId());
//	       intent.putExtra("title", message.getTargetUserName());
//	       intent.putExtra("isnotification", true);
		 PendingIntent pIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	       Notification notification = new Notification.Builder(context)
	    		   .setSmallIcon(com.example.mushroomim.R.drawable.notification)
	    		   .setContentTitle("新消息")
	    		   .setContentText(name+"发来消息")
	    		   .setContentIntent(pIntent)
	    		   .build();
	       manager.notify(2, notification);
	}
}
