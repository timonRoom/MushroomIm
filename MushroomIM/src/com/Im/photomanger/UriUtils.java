package com.Im.photomanger;

import java.io.File;
import java.io.FileNotFoundException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

public class UriUtils {

    /**
     * ��Uriת����Bitmap
     */
    public static Bitmap decodeUriAsBitmap(Context context, Uri uri){

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }


    /**
     * ��UriתΪ·����ַ
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
//    	Log.e("getRealFilePath", uri+"");
//    	Log.e("getRealFilePath", uri.getPath());
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
        	Log.e("getRealFilePath", uri.getPath());
//            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
        	 Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns._ID }, null, null, null );
//        	ContentResolver contentResolver = context.getContentResolver();
//            Cursor cursor = contentResolver.query( uri, new String[] { ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
//                    int index = cursor.getColumnIndex( MediaStore.Images.MediaColumns.DATA );
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    Log.e("getRealFilePath", index+"");
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                        Log.e("getRealFilePath", cursor.getString( index ));
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * ��·����ַת��ΪUri
     */
    public static Uri getUriFromFilePath(final String path){

        return Uri.fromFile(new File(path));
    }
    
    @SuppressLint("NewApi")
	public static String getImageAbsolutePath(Activity context, Uri imageUri) {  
        if (context == null || imageUri == null)  
            return null;  
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {  
            if (isExternalStorageDocument(imageUri)) {  
                String docId = DocumentsContract.getDocumentId(imageUri);  
                String[] split = docId.split(":");  
                String type = split[0];  
                if ("primary".equalsIgnoreCase(type)) {  
                    return Environment.getExternalStorageDirectory() + "/" + split[1];  
                }  
            } else if (isDownloadsDocument(imageUri)) {  
                String id = DocumentsContract.getDocumentId(imageUri);  
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));  
                return getDataColumn(context, contentUri, null, null);  
            } else if (isMediaDocument(imageUri)) {  
                String docId = DocumentsContract.getDocumentId(imageUri);  
                String[] split = docId.split(":");  
                String type = split[0];  
                Uri contentUri = null;  
                if ("image".equals(type)) {  
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
                } else if ("video".equals(type)) {  
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;  
                } else if ("audio".equals(type)) {  
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;  
                }  
                String selection = MediaStore.Images.Media._ID + "=?";  
                String[] selectionArgs = new String[] { split[1] };  
                return getDataColumn(context, contentUri, selection, selectionArgs);  
            }  
        } // MediaStore (and general)  
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {  
            // Return the remote address  
            if (isGooglePhotosUri(imageUri))  
                return imageUri.getLastPathSegment();  
            return getDataColumn(context, imageUri, null, null);  
        }  
        // File  
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {  
            return imageUri.getPath();  
        }  
        return null;  
    }  
      
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {  
        Cursor cursor = null;  
        String column = MediaStore.Images.Media.DATA;  
        String[] projection = { column };  
        try {  
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);  
            if (cursor != null && cursor.moveToFirst()) {  
                int index = cursor.getColumnIndexOrThrow(column);  
                return cursor.getString(index);  
            }  
        } finally {  
            if (cursor != null)  
                cursor.close();  
        }  
        return null;  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is ExternalStorageProvider. 
     */  
    public static boolean isExternalStorageDocument(Uri uri) {  
        return "com.android.externalstorage.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is DownloadsProvider. 
     */  
    public static boolean isDownloadsDocument(Uri uri) {  
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is MediaProvider. 
     */  
    public static boolean isMediaDocument(Uri uri) {  
        return "com.android.providers.media.documents".equals(uri.getAuthority());  
    }  
      
    /** 
     * @param uri The Uri to check. 
     * @return Whether the Uri authority is Google Photos. 
     */  
    public static boolean isGooglePhotosUri(Uri uri) {  
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
    }  
}
