package com.example.mushroomim.Utils;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class SpUtils {

	public  int  putContact(Context context ,String dataname,String key,String inputcontext) {
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		Set<String> set = null;
		 set = sp.getStringSet(key, set);
		if (set==null) {
			set = new HashSet<String>();
		}
		set.add(inputcontext);
		Editor et = sp.edit();
		et.clear();
		et.putStringSet(key, set);
		et.commit();
		return 1;
	}
	public  int  putBoolean(Context context ,String dataname,String key,Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		Editor et = sp.edit();
		et.clear();
		et.putBoolean(key, value);
		et.commit();
		return 1;
	}
	public  int  putvalue(Context context ,String dataname,String key,String  value) {
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		Editor et = sp.edit();
		et.clear();
		et.putString(key, value);
		et.commit();
		return 1;
	}
	public  String  searchvalue(Context context ,String dataname,String key) {
		String result = null;
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		result = sp.getString(key, null);
		return result;
	}
	public  boolean  searchBoolean(Context context ,String dataname,String key) {
		boolean result = false;
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
		result = sp.getBoolean(key, false);
		return result;
	}
	public String[] searContactInfo(Context context,String dataname, String key) {
		String[] data= null;
//		 final SharedPreferences prefs = PreferenceManager  
//	                .getDefaultSharedPreferences(context);  
		SharedPreferences sp = context.getSharedPreferences(dataname, Context.MODE_PRIVATE);
	        Set<String> siteno = null;  
	        siteno = sp.getStringSet(key, siteno);  
	        if (siteno==null) {
				return data;
			}
	        Log.e("siteno", siteno.size()+"");
	        Log.e("siteno", siteno.toString());
	        if (siteno.size() > 0) {
	             data = (String[]) siteno.toArray(new String[siteno.size()]);   //将SET转换为数组   
//	            Unit_PublicVar.arr_DeatilContent = new String[data.length][];  
//	            for (int i = 0; i < data.length; i++) {  
//	                Unit_PublicVar.arr_DeatilContent[i] = data[i].trim().split(  
//	                        ",");  
//	            }  
	}
			return data;  
	}
}
