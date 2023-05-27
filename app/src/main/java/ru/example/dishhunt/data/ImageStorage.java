package ru.example.dishhunt.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;

public class ImageStorage {
    private static final String dirName = "recipe_imgs";

    public static String putImage(Bitmap bitmapImage, Context context){
        if(bitmapImage==null){
            return "";
        }
        // Create imageDir
        File sdcard = context.getFilesDir();
        File folder = new File(sdcard.getAbsoluteFile(), "/"+dirName+"/");
        folder.mkdirs();
        folder.setWritable(true);
        int count = 0;
        File newFile = null;
        do{
            // Find fileName new
            count++;
            String nameFile_jpg = folder + "/" + count + ".jpg";
            newFile = new File(nameFile_jpg);
        }while(newFile.exists());
        //Create jpg file to save to
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(newFile);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return newFile.getName();
    }

    public static Bitmap getImage(String imagename, Context context) {

        File mediaImage = null;
        try {
            String root = context.getFilesDir().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir.getPath() + "/"+dirName+"/"+imagename);
        } catch (Exception e) {
            //FirebaseCrash.report(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(mediaImage == null){
            return null;
        }
        return BitmapFactory.decodeFile(mediaImage.getAbsolutePath());
    }
    public static boolean checkImageExists(Context context,String imagename)
    {
        Bitmap b = null ;
        File mediaImage = null;
        try {
            String root = context.getFilesDir().toString();
            File myDir = new File(root);
            if (!myDir.exists()) mediaImage = null;
            else{mediaImage = new File(myDir.getPath() + "/"+dirName+"/"+imagename);
            }
        } catch (Exception e) {
            //FirebaseCrash.report(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String path = mediaImage.getAbsolutePath();
        if (path != null) b = BitmapFactory.decodeFile(path);
        if(b == null ||  b.equals("")) return false;
        return true ;
    }
    public static void clearStorage(Context context){
            File sdcard = context.getFilesDir();
            File folder = new File(sdcard.getAbsoluteFile(), "/"+dirName+"/");
            if(folder.exists()) {
                File[] files = folder.listFiles();
                if(files!=null) {
                    for(File f: files) {
                        f.delete();
                    }
                }
            }
    }
}
