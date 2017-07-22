package com.example.mushroomim;

import org.xutils.x;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import io.rong.imkit.RongIM;

public class MyApp extends Application {
	private static Context content;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 RongIM.init(this);
		 x.Ext.init(this);
		
	}
	public static Context getObjectContext() {
		return content;
	}
	/**
	 * 获得当前进程的名字
	 * 
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}
}
