package com.example.mushroomim.Utils;

import com.example.mushroomim.mode.UserDataMode;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbManger {
	private Imdbhelper imdbhelper;
	public DbManger(Context context) {
		super();
		 imdbhelper = new Imdbhelper(context);
	}
	// �����¼  
    public int insert(UserDataMode dataMode) {  
        Log.e("SQLite", "----insert----");  
        SQLiteDatabase db = imdbhelper.getWritableDatabase();  
        db.beginTransaction();  
        try {  
            db.execSQL("insert into "+UserDataMode.table
                    +"(userid,usernikename,password,token) values(?, ?, ?,?)", new Object[] { dataMode.getUserid(),  
                    		dataMode.getUsernikename(),dataMode.getPassword(), dataMode.getToken()});  
            db.setTransactionSuccessful();  
            Log.e("SQLite", "----setTransactionSuccessful----"); 
        } catch (Exception e) {  
            return 0;  
        } finally {  
            db.endTransaction();  
        } 
        db.close();  
        return 1;  
    }  
  //����ͷ����Ϣ
    public int insertPortraitUri(String  userid,String portraitUri) {  
        Log.e("SQLite", "----insert----");  
        SQLiteDatabase db = imdbhelper.getWritableDatabase();  
        db.beginTransaction();  
        try {  
            db.execSQL("insert into "+UserDataMode.table
                    +"(portraitUri) values(?) where userid=? ", new Object[] {portraitUri ,  
                    		userid});  
            db.setTransactionSuccessful();  
            Log.e("SQLite", "----setTransactionSuccessful----"); 
        } catch (Exception e) {  
            return 0;  
        } finally {  
            db.endTransaction();  
        } 
        db.close();  
        return 1;  
    } 
    // ɾ����¼  
   /* public int delete(Person person) {  
        Log.e("SQLite", "----delete----");  
        SQLiteDatabase db = dbHelper.getWritableDatabase();  
        db.beginTransaction();  
        try {  
            db.execSQL("delete from " + Person.TABLENAME + " where id = ?",  
                    new Object[] { person.id });  
            db.setTransactionSuccessful();  
        } catch (Exception e) {  
            return 0;  
        } finally {  
            db.endTransaction();  
        }  
        db.close();  
        return 1;  
    }  */
  
    // ����ͷ����Ϣ
    public int updatePortraitUri(String  userid,String portraitUri) {  
        Log.e("SQLite", "----update----");  
        SQLiteDatabase db = imdbhelper.getWritableDatabase();  
        db.beginTransaction();  
        try {  
            db.execSQL("update " + UserDataMode.table 
                    + " set portraitUri=? where userid=?", new Object[] {  
                    		portraitUri, userid});  
            db.setTransactionSuccessful();  
        } catch (Exception e) {  
            return 0;  
        } finally {  
            db.endTransaction();  
        }  
        db.close();  
        return 1;  
    }  
    
    // �����ǳ���Ϣ
    public int updateNikename(String  userid,String usernikename) {  
        Log.e("SQLite", "----update----");  
        SQLiteDatabase db = imdbhelper.getWritableDatabase();  
        db.beginTransaction();  
        try {  
            db.execSQL("update " + UserDataMode.table 
                    + " set usernikename=? where userid=?", new Object[] {  
                    		usernikename, userid});  
            db.setTransactionSuccessful();  
        } catch (Exception e) {  
            return 0;  
        } finally {  
            db.endTransaction();  
        }  
        db.close();  
        return 1;  
    }  
  
    // ��֤�û����Ƿ��ظ�
    public boolean usernameisrepetition(String userid) {  
    	boolean isrepetition = false;
        Log.e("SQLite", "----query----");  
        SQLiteDatabase db = imdbhelper.getReadableDatabase();  
        Cursor cursor;
        // ��fileIdΪnull��""���ѯ���м�¼  
        if (userid == null || userid.equals("")) {  
            cursor = db.rawQuery("select * from " + UserDataMode.table, null);  
        } else {  
            cursor = db.rawQuery("select * from " + UserDataMode.table  
                    + " where userid=?", new String[] { userid });  
        }  
        if (cursor.moveToFirst()) {
			isrepetition = true;
		}
//        while (cursor.moveToNext()) {  
//            person = new Person();  
//            person.id = cursor.getString(cursor.getColumnIndex("id"));  
//            person.name = cursor.getString(cursor.getColumnIndex("name"));  
//            person.gender = cursor.getString(cursor.getColumnIndex("gender"));  
//            person.age = cursor.getInt(cursor.getColumnIndex("age"));  
//            Log.e("SQLite", person.toString());  
//            list.add(person);  
//        }  
        cursor.close();  
        db.close();  
        return isrepetition;  
    }  
 // ��¼��Ϣ��֤
    public boolean loadingcheck(String userid,String password) {  
    	boolean isright = false;
        Log.e("SQLite", "----query----");  
        SQLiteDatabase db = imdbhelper.getReadableDatabase();  
        Cursor cursor;  
        // ��fileIdΪnull��""���ѯ���м�¼  
            cursor = db.rawQuery("select * from " + UserDataMode.table  
                    + " where userid=?and password=?", new String[] { userid, password});  
        if (cursor.moveToFirst()) {
        	isright = true;
		}
//        while (cursor.moveToNext()) {  
//            person = new Person();  
//            person.id = cursor.getString(cursor.getColumnIndex("id"));  
//            person.name = cursor.getString(cursor.getColumnIndex("name"));  
//            person.gender = cursor.getString(cursor.getColumnIndex("gender"));  
//            person.age = cursor.getInt(cursor.getColumnIndex("age"));  
//            Log.e("SQLite", person.toString());  
//            list.add(person);  
//        }  
        cursor.close();  
        db.close();  
        return isright;  
    }  
 // ��ѯtoken��¼
    public String  querytoken(String userid) {  
    	String  token = "";
        Log.e("SQLite", "----query----");  
        SQLiteDatabase db = imdbhelper.getReadableDatabase();  
        Cursor cursor;  
        // ��fileIdΪnull��""���ѯ���м�¼  
            cursor = db.rawQuery("select * from " + UserDataMode.table  
                    + " where userid=?", new String[] { userid});  
        while (cursor.moveToNext()) {  
            token = cursor.getString(cursor.getColumnIndex("token"));
        }  
        cursor.close();  
        db.close();  
        return token;  
    }  
    // ��ѯͷ����Ϣ
    public String  queryportraitUri(String userid) {  
    	String  portraitUri = "";
        Log.e("SQLite", "----query----");  
        SQLiteDatabase db = imdbhelper.getReadableDatabase();  
        Cursor cursor;  
        // ��fileIdΪnull��""���ѯ���м�¼  
            cursor = db.rawQuery("select * from " + UserDataMode.table  
                    + " where userid=?", new String[] { userid});  
        while (cursor.moveToNext()) {  
        	portraitUri = cursor.getString(cursor.getColumnIndex("portraitUri"));
        }  
        cursor.close();  
        db.close();  
        return portraitUri;  
    } 
    
    // ��ѯ�ǳ�
    public String  querynikename(String userid) {  
    	String  nikename = "";
        Log.e("SQLite", "----query----");  
        SQLiteDatabase db = imdbhelper.getReadableDatabase();  
        Cursor cursor;  
        // ��fileIdΪnull��""���ѯ���м�¼  
            cursor = db.rawQuery("select * from " + UserDataMode.table  
                    + " where userid=?", new String[] { userid});  
        while (cursor.moveToNext()) {  
        	nikename = cursor.getString(cursor.getColumnIndex("usernikename"));
        }  
        cursor.close();  
        db.close();  
        return nikename;  
    } 
}
