package com.example.mushroomim.control.activity;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.mode.UserDataMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.util.Log;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.UserInfo;

public class HttpUtils {
	public HttpUtils() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static final String APP_KEY = "c9kqb3rdcxrwj";
	private static final String APP_SECRET = "8do3WAPEF4";
	public static SharedPreferences getSharedPreferences(Context context) {
		SharedPreferences sharedPreferences = com.example.mushroomim.MyApp.getObjectContext()
				.getSharedPreferences("user", Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public static Editor getEditor() {
		Editor editor = getSharedPreferences(com.example.mushroomim.MyApp.getObjectContext())
				.edit();
		return editor;
	}

	public static String getUserName() {

		return getSharedPreferences(com.example.mushroomim.MyApp.getObjectContext())
				.getString("username", "");
	}

	public static String getUserPass() {
		return getSharedPreferences(com.example.mushroomim.MyApp.getObjectContext())
				.getString("userpass", "");
	}

	public static String getUserToken() {

		return getSharedPreferences(com.example.mushroomim.MyApp.getObjectContext())
				.getString("token", "");
	}

	public static RequestParams addHeader(RequestParams params) {
		Random r = new Random();
		String Nonce = (r.nextInt(10000) + 10000) + "";
		String Timestamp = (System.currentTimeMillis() / 1000) + "";
		params.addHeader("App-Key", APP_KEY);
		params.addHeader("Nonce", Nonce);
		params.addHeader("Timestamp", Timestamp);
		params.addHeader("Signature",
				com.example.mushroomim.Utils.MD5.encryptToSHA(APP_SECRET + Nonce + Timestamp));

		return params;
	}
	 static String result ="1";
	public static String  getToken(final Context context,final String id, final String usernikename,final String password) {
		RequestParams params = new RequestParams("https://api.cn.ronghub.com/user/getToken.json");
		addHeader(params);
		params.addBodyParameter("userId", id);
		params.addBodyParameter("name", usernikename);
		Log.e("onError", id+""+usernikename);
		x.http().post(params, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				arg0.getCause();
				 result = arg0.getCause().toString();
				Log.e("onError", result);
//				EBmessage eb = new EBmessage();
//				eb.setStatus(false);
//				eb.setMessage(arg0.toString());
//				eb.setFrom("getToken");
//				EventBus.getDefault().post(eb);
			}

			@Override
			public void onFinished() {
				Log.e("onFinished", "onFinished");
			}

			@Override
			public void onSuccess(String s) {
				Log.e("onSuccess", s);
				try {
					UserDataMode dataMode = new UserDataMode();
					JSONObject jsonObject = new JSONObject(s);
					String userid = jsonObject.getString("userId");
					String token = jsonObject.getString("token");
					dataMode.setPassword(password);
					dataMode.setToken(token);
					dataMode.setUserid(userid);
					dataMode.setUsernikename(usernikename);
					DbManger dbManger = new DbManger(context);
					Log.e("insert", dataMode.toString());
					dbManger.insert(dataMode);
					result = "1";
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				TokenMod mod = new Gson().fromJson(s, TokenMod.class);
//				Editor editor = getEditor();
//				editor.putString("username", username);
//				editor.putString("userpass", mod.getUserId());
//				editor.putString("token", mod.getToken());
//				editor.commit();
//
//				EBmessage eb = new EBmessage();
//				eb.setStatus(true);
//				eb.setMessage(mod.getToken());
//				eb.setFrom("getToken");
//				EventBus.getDefault().post(eb);
			}
		});
		Log.e("result>>>", result);
		return result;
	}

	/**
	 * 建立与融云服务器的连接
	 * 
	 * @param token
	 */
	public static void connect(String token, final Context context) {

		if (context.getApplicationInfo().packageName.equals(com.example.mushroomim.MyApp
				.getCurProcessName(context.getApplicationContext()))) {

			/**
			 * IMKit SDK调用第二步,建立与服务器的连接
			 */
			RongIM.connect(token, new ConnectCallback() {
				
				@Override
				public void onSuccess(String userId) {
					Log.e("onSuccess", userId);
					String path = new DbManger(context).queryportraitUri(userId);
					Uri portraitUri = null;
					if (path!=null&&!path.equals("")) {
						 portraitUri = Uri.parse(path);
					}
					String	nikename = new DbManger(context).querynikename(userId);
					RongIM.getInstance().setCurrentUserInfo(new UserInfo(userId, nikename, portraitUri));
					RongIM.getInstance().setMessageAttachedUserInfo(true);
				}
				
				@Override
				public void onError(ErrorCode arg0) {
					Log.e("onError", arg0.toString());
				}
				
				@Override
				public void onTokenIncorrect() {
					Log.e("onTokenIncorrect", "onTokenIncorrect");
				}
			});
		}
	}

	public static  void refreshUserinfo(Context context,String userId, String nikename, Uri uri) {
		String path = new DbManger(context).queryportraitUri(userId);
		Uri portraitUri = null;
		if (path!=null&&!path.equals("")) {
			 portraitUri = Uri.parse(path);
		}
		String	name = new DbManger(context).querynikename(userId);
		if (nikename!=null) {
			name = nikename;
		}
		if (uri!=null) {
			portraitUri = uri;
		}
		RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, portraitUri));
	}
	
}
