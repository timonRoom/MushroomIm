package com.example.mushroomim.control.activity;

import javax.security.auth.login.LoginException;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;
import com.example.mushroomim.Utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoadingActivity extends Activity implements OnClickListener {
	private EditText et_username;
	private EditText et_password;
	private Button btn_login;
	public static String USERID;
	private CheckBox cb_remember_password;
	private TextView tv_toregister;
	private CheckBox cb_login_author;
	private TextView tv_loading_type;
	private boolean isremember_password=false;
	private boolean islogin_author=false;
	private String CB_REMEMBER_PASSWORD_VALUES = "a";
	private String CB_LOGIN_AUTHOR_VALUES = "b";
	private String USERNAME = "x";
	private String PASSWORD = "y";
	private String DATANAME_ISREMEMBER = "q";
	private String DATANAME_ISAUTHOR = "w";
	private String DATANAME_USERNAME = "e";
	private String DATANAME_PASSWORD = "r";
	private SpUtils spUtils = new SpUtils();
	DbManger dbManger ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		tv_toregister = (TextView) findViewById(R.id.tv_toregister);
		tv_loading_type = (TextView) findViewById(R.id.tv_loading_type);
		cb_login_author = (CheckBox) findViewById(R.id.cb_login_author);
		cb_login_author.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.getId()==R.id.cb_login_author) {
					isremember_password = isChecked;
				}
			}
		});
		cb_remember_password = (CheckBox) findViewById(R.id.cb_remember_password);
		cb_remember_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (buttonView.getId()==R.id.cb_remember_password) {
					islogin_author = isChecked;
				}
			}
		});
		tv_toregister.setOnClickListener(this);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		dbManger = new DbManger(getApplicationContext());
		init();
	}
	private void init() {
		if (getIntent().getBooleanExtra("ischangeid", false)) {
			tv_loading_type.setText("切换账号");
		}else {
			isremember_password = spUtils.searchBoolean(this,DATANAME_ISREMEMBER, CB_REMEMBER_PASSWORD_VALUES);
			cb_remember_password.setChecked(isremember_password);

			islogin_author = spUtils.searchBoolean(this,DATANAME_ISAUTHOR, CB_LOGIN_AUTHOR_VALUES);
			cb_login_author.setChecked(islogin_author);

			if (isremember_password) {
				String username = spUtils.searchvalue(this, DATANAME_USERNAME,USERNAME);
				String password = spUtils.searchvalue(this,DATANAME_PASSWORD, PASSWORD);
				if ((username!=null&&!username.equals(""))&&(password!=null&&!password.equals(""))) {
					et_username.setText(username);
					et_password.setText(password);
					if (islogin_author) {
						login(username,password);
					}
				}
			}
		}

	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_toregister :
			Intent intent = new Intent();
			intent.setClass(getApplication(), RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login :
			String username_login = et_username.getText().toString();
			String passwordl_login = et_password.getText().toString();
			//			String username_login = "10010";
			//			String passwordl_login = "123456";
			if (dbManger.loadingcheck(username_login, passwordl_login)) {
				if (isremember_password) {
					spUtils.putvalue(this, DATANAME_USERNAME,USERNAME, username_login);
					spUtils.putvalue(this,DATANAME_PASSWORD, PASSWORD, passwordl_login);
					spUtils.putBoolean(this, DATANAME_ISAUTHOR,CB_LOGIN_AUTHOR_VALUES, islogin_author);
					spUtils.putBoolean(this, DATANAME_ISREMEMBER,CB_REMEMBER_PASSWORD_VALUES, isremember_password);
				}
				login(username_login,passwordl_login);
			}else {
				Toast.makeText(getApplicationContext(), "用户名或密码有误", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	private void login(String username_login, String passwordl_login) {
		String token = dbManger.querytoken(username_login);
		if (token!=null&&!token.equals("")) {
			HttpUtils.connect(token, getApplicationContext());
			Intent intent2 = new Intent();
			USERID= username_login;
			if (getIntent().getBooleanExtra("isnotification", false)) {
				intent2.putExtra("isnotification", true);
				intent2.putExtra("TargetId", getIntent().getStringExtra("TargetId"));
			    intent2.putExtra("title",  getIntent().getStringExtra("title"));
			}
			intent2.putExtra("userId", username_login);
			intent2.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent2);
			finish();
		}
	}




}
