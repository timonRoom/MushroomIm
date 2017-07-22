package com.example.mushroomim.control.fragment;

import com.Im.photomanger.ImageUtils;
import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.control.activity.AboutActivity;
import com.example.mushroomim.control.activity.HttpUtils;
import com.example.mushroomim.control.activity.LoadingActivity;
import com.example.mushroomim.control.activity.MainActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;

public class SetFragment extends Fragment implements OnClickListener, android.content.DialogInterface.OnClickListener{

	private RelativeLayout rl_nicheng;
	private TextView tv_userid;
	private String Nickname ;
	private TextView tv_nicheng;
	private Button btn_changeid;
	private Button btn_logout;
	private Button btn_aboutapp;
	private Button btn_finish;
	private String userId;
	private RelativeLayout rl_touxian;
	private ImageView iv_settouxian;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e("SetFragment", "onCreateView");
		View view = View.inflate(getActivity(), R.layout.setfragment, null);
		rl_nicheng = (RelativeLayout) view.findViewById(R.id.rl_nicheng);
		tv_nicheng = (TextView) view.findViewById(R.id.tv_nicheng);
		btn_finish = (Button) view.findViewById(R.id.btn_finish);
		btn_finish.setOnClickListener(this);
		tv_userid = (TextView) view.findViewById(R.id.tv_userid);
		rl_touxian = (RelativeLayout) view.findViewById(R.id.rl_touxian);
		btn_changeid = (Button) view.findViewById(R.id.btn_changeid);
		btn_logout = (Button) view.findViewById(R.id.btn_logout);
		btn_aboutapp = (Button) view.findViewById(R.id.btn_aboutapp);
		btn_aboutapp.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		
		btn_changeid.setOnClickListener(this);
		rl_touxian.setOnClickListener(this);
		iv_settouxian = (ImageView) view.findViewById(R.id.iv_settouxian);
		iv_settouxian.setOnClickListener(this);
		rl_nicheng.setOnClickListener(this);
		initdata();
		return view;
	}
	private void initdata() {
		userId = MainActivity.userId;
		btn_logout.setText("退出登录("+userId+")");
		Nickname = new DbManger(getActivity()).querynikename(userId);
		if (Nickname!=null&&!Nickname.equals("")) {
			setnikename(Nickname);
			
		}
		tv_userid.setText(userId);
		String path = new DbManger(getActivity()).queryportraitUri(userId);
		if (path!=null&&!path.equals("")) {
			Uri uri = Uri.parse(path);
			settouxian(uri);
		}
	}
	
	private void setnikename(String nikename) {
		tv_nicheng.setText(nikename);
	}
	private void settouxian(Uri uri) {
		iv_settouxian.setImageURI(uri);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_nicheng :
			getresultfordialog();
			break;
		case R.id.rl_touxian:
			ImageUtils.showImagePickDialog(getActivity());
			break;
		case R.id.btn_changeid:
			Intent intent =  new Intent();
			intent.setClass(getActivity(), LoadingActivity.class);
			intent.putExtra("ischangeid", true);
			startActivity(intent);
			RongIM.getInstance().logout();
			getActivity().finish();
			break;
		case R.id.btn_logout:
			RongIM.getInstance().logout();
			getActivity().finish();
			break;
		case R.id.btn_aboutapp:
			Intent intent1 =  new Intent();
			intent1.setClass(getActivity(), AboutActivity.class);
			startActivity(intent1);
			break;
		case R.id.btn_finish:
			RongIM.getInstance().disconnect();
			getActivity().finish();
			break;
		}
	}
	EditText editText ;
	private void getresultfordialog() {
		editText= new EditText(getActivity());
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		editText.setHint(Nickname);
		builder.setView(editText);
		builder.setTitle("修改昵称");
		builder.setPositiveButton("确定", this);
		builder.setNegativeButton("取消", this);
		builder.show();
	}
	@Override
	public void onClick(DialogInterface dialog, int which) {
		int result = new DbManger(getActivity()).updateNikename(userId, editText.getText().toString());
		if (result==1) {
			Nickname = editText.getText().toString();
			setnikename(Nickname);
			HttpUtils.refreshUserinfo(getContext(),userId,Nickname,null);
		}else {
			Toast.makeText(getActivity(), "修改昵称失败", Toast.LENGTH_SHORT).show();
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("onActivityResult", requestCode+"");
		   switch (requestCode){
           case ImageUtils.REQUEST_CODE_FROM_ALBUM: {

//               if (resultCode == RESULT_CANCELED) {   //取消操作
//                   return;
//               }

               Uri imageUri = data.getData();
               ImageUtils.copyImageUri(getActivity(), imageUri);
               ImageUtils.cropImageUri(getActivity(), ImageUtils.getCurrentUri(), 200, 200);
//               if (imageUri!=null) {
//            	   Log.e("iv_settouxian", iv_settouxian.toString());
//            	   iv_settouxian.setImageURI(imageUri);
//			}
               break;
           }
           case ImageUtils.REQUEST_CODE_FROM_CAMERA: {

//               if (resultCode == RESULT_CANCELED) {     //取消操作
//                   ImageUtils.deleteImageUri(this, ImageUtils.getCurrentUri());   //删除Uri
//               }

               ImageUtils.cropImageUri(getActivity(), ImageUtils.getCurrentUri(), 200, 200);
               break;
           }
           case ImageUtils.REQUEST_CODE_CROP: {
//
//               if (resultCode == RESULT_CANCELED) {     //取消操作
//                   return;
//               }

               Uri imageUri = ImageUtils.getCurrentUri();
               if (imageUri != null) {
            	   settouxian(imageUri);
            	  int insertcode =  new DbManger(getActivity()).updatePortraitUri(userId, imageUri.toString());
            	  if (insertcode == 1) {
            		  HttpUtils.refreshUserinfo(getContext(),userId,null,imageUri);
				}
            	   Log.e("insertcode>>>", insertcode+"");
               }
               break;
           }
           default:
               break;
       }
	}
}
