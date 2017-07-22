package com.example.mushroomim.control.fragment;

import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class BaseFragmentActiviy extends FragmentActivity {
//	private static final String TAG = "BaseActivity";
//	 @Override
//	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		 super.onActivityResult(requestCode, resultCode, data);
//	        FragmentManager fragmentManager=this.getSupportFragmentManager();
//	        for(int indext=0;indext<fragmentManager.getFragments().size();indext++)
//	        {
//	            Fragment fragment=fragmentManager.getFragments().get(indext); //找到第一层Fragment
//	            if(fragment==null)
//	                 Log.w(TAG, "Activity result no fragment exists for index: 0x"  
//	                         + Integer.toHexString(requestCode));  
//	            else 
//	                handleResult(fragment,requestCode,resultCode,data);
//	        }
//
//	 }

	 /**
	  * 递归调用，对所有子Fragement生效
	  * 
	  * @param frag
	  * @param requestCode
	  * @param resultCode
	  * @param data
	  */
//	 private void handleResult(Fragment frag, int requestCode, int resultCode,
//	   Intent data) {
//	  frag.onActivityResult(requestCode & 0xffff, resultCode, data);
//	  List<Fragment> frags = frag.getChildFragmentManager().getFragments();
//	  if (frags != null) {
//	   for (Fragment f : frags) {
//	    if (f != null)
//	     handleResult(f, requestCode, resultCode, data);
//	   }
//	  }
//	 }
}
