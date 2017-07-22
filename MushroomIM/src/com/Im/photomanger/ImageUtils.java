package com.Im.photomanger;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class ImageUtils {

    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;
    public static final int REQUEST_CODE_CROP = 5003;

    /**
     * �������ͼƬ��uri��ַ
     */
    private static Uri imageUriFromALBUM;
    private static Uri imageUriFromCamera;

    /**
     * ��¼�Ǵ���ʲô״̬������or���
     */
    private static int state = 0;

    /**
     * ��ʾ��ȡ��Ƭ��ͬ��ʽ�Ի���
     */
    public static void showImagePickDialog(final Activity activity){

        String title = "ѡ���ȡͼƬ��ʽ";
        String[] items = new String[]{"����","���"};

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch (which){
                            case 0:
                                state = 1;
                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                state = 2;
                                pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }


    /**
     * �򿪱������ѡȡͼƬ
     */
    public static void pickImageFromAlbum(final Activity activity){

        //��ʽ���ã����ܳ��ֶ���ѡ��
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        Log.e("ImageUtils", "pickImageFromAlbum");
        activity.startActivityForResult(intent,REQUEST_CODE_FROM_ALBUM);

        /**
         Intent intent = new Intent();
         intent.setAction(Intent.ACTION_PICK);
         intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         activity.startActivityForResult(intent,REQUEST_CODE_FROM_ALBUM);
         */
    }


    /**
     * ��������ջ�ȡͼƬ
     */
    public static void pickImageFromCamera(final Activity activity){

        imageUriFromCamera = getImageUri();

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUriFromCamera);    //ָ�����յ�ͼƬ����imageUriFromCamera��,���ֱ���ڷ���ʱʹ��getData()��ȡ����ѹ������Bitmap����
        activity.startActivityForResult(intent,REQUEST_CODE_FROM_CAMERA);
    }


    /**
     * ����ָ��Ŀ¼����һ��ͼƬUri
     */
    private static Uri getImageUri(){
        String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpg";
        String path = MyConstant.PhotoDir + imageName;
        return UriUtils.getUriFromFilePath(path);
    }

    /**
     * ����һ��Uri��������Ϊ��������ԭͼƬ����Ӱ��
     */
    public static void copyImageUri(Activity activity, Uri uri){

        String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpg";
        String copyPath = MyConstant.PhotoDir + imageName;
        
        FileUtils.copyfile(UriUtils.getImageAbsolutePath(activity, uri),copyPath,true);
        imageUriFromALBUM = UriUtils.getUriFromFilePath(copyPath);
    }

    /**
     * ɾ��һ��ͼƬUri
     */
    public static void deleteImageUri(Context context, Uri uri){
        context.getContentResolver().delete(uri, null, null);
    }


    /**
     * �ü�ͼƬ����
     */
    public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY){

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        activity.startActivityForResult(intent, REQUEST_CODE_CROP);

    }


    /**
     * ����״̬����ͼƬUri
     */
    public static Uri getCurrentUri(){

        if (state == 1){
            return imageUriFromCamera;
        }
        else if (state == 2){
            return imageUriFromALBUM;
        }
        else return null;
    }

}
