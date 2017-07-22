package com.example.mushroomim.Utils;

import com.example.mushroomim.R;
import com.example.mushroomim.mode.UserDataMode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class Imdbhelper extends SQLiteOpenHelper {
	private static String name = "userdata.db";
	private static int version = 1;
	public Imdbhelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table data(id integer primary key autoincrement,"//id 自增key
				+ "userid integer,"//用户id
				+ "usernikename varchar(10),"//用户昵称
				+ "password varchar(10),"//密码
				+ "token varchar(256),"
				+ "portraitUri varchar(256))";//token
		db.execSQL(sql);
//		Uri.parse("android.resource://com.example.mushroomim/" + R.drawable.app);
		db.execSQL("insert into "+UserDataMode.table
                    +"(userid,usernikename,password,token,portraitUri) values(?, ?, ?,?,?)", new Object[] { "782636200",  
                    		"Author","888888",
                    		"bLZ1jGko7hEulgMjRi7R3WlItmpKlBUlg0vqiyyEbw5G0QtOOp8NWJGeXkM1MnJZnqZxyVGnnvVH+I/yhlJMwQ==",
                    		"android.resource://com.example.mushroomim/" + R.drawable.app});
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
