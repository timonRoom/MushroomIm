package com.Im.photomanger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class FileUtils {

    /**
     *
     * @param fromPath �����Ƶ��ļ�·��
     * @param toPath ���Ƶ�Ŀ¼�ļ�·��
     * @param rewrite �Ƿ����´����ļ�
     *
     * <p>�ļ��ĸ��Ʋ�������
     */
    public static void copyfile(String fromPath, String toPath, Boolean rewrite ){
    	Log.e("copyfile",fromPath );
        File fromFile = new File(fromPath);
        File toFile = new File(toPath);

        if(!fromFile.exists()){
            return;
        }
        if(!fromFile.isFile()){
            return;
        }
        if(!fromFile.canRead()){
            return;
        }
        if(!toFile.getParentFile().exists()){
            toFile.getParentFile().mkdirs();
        }
        if(toFile.exists() && rewrite){
            toFile.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while((c=fosfrom.read(bt)) > 0){
                fosto.write(bt,0,c);
            }
            //�ر����롢�����
            fosfrom.close();
            fosto.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

