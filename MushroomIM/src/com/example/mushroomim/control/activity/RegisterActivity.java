package com.example.mushroomim.control.activity;

import com.example.mushroomim.R;
import com.example.mushroomim.Utils.DbManger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText et_username;
	private EditText et_password;
	private Button btn_register;
	public static String USERID;
	private EditText et_usernikename;
	private EditText et_checkpassword;
	private TextView tv_cancel;
	DbManger dbManger ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_register = (Button) findViewById(R.id.btn_register);
		et_checkpassword = (EditText) findViewById(R.id.et_checkpassword);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(this);
		et_usernikename = (EditText) findViewById(R.id.et_usernikename);
		btn_register.setOnClickListener(this);
		dbManger = new DbManger(getApplicationContext());
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_cancel:
			finish();
			break;
		case R.id.btn_register:
			Log.e("view", view.getId()+"");
			String username = et_username.getText().toString();
			String usernikename = et_usernikename.getText().toString();
			String password = et_password.getText().toString();
			String check_password = et_checkpassword.getText().toString();
			if (username!=null&&!username.equals("")) {
				if (password!=null&&!password.equals("")) {
					if (password!=null&&!password.equals("")&&check_password!=null&&!check_password.equals("")&&check_password.equals(password)) {
						if (!dbManger.usernameisrepetition(username)) {
							String resultcode = HttpUtils.getToken(this,username, usernikename, password);
							Log.e("RegisterActivity>>>>>resultcode", resultcode);
							if (resultcode.equals("1")) {
								Toast.makeText(getApplicationContext(), "ע��ɹ�", Toast.LENGTH_SHORT).show();
								finish();
							}else {
								Toast.makeText(getApplicationContext(), "ע��ʧ��"+resultcode, Toast.LENGTH_SHORT).show();
							}
						}else {
							Toast.makeText(getApplicationContext(), "�û����ظ�", Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(getApplicationContext(), "ȷ����������", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(getApplicationContext(), "��������ȷ������", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(getApplicationContext(), "��������ȷ���û���", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}
