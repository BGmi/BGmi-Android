package bgmi.app.bgmi_android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImageWorker {
    private static final int lruSizeMaxSize = 10*1024*1024;
    private final static LruCache<String,Bitmap> mLruCache = new LruCache<String, Bitmap>(lruSizeMaxSize);
    private static ExecutorService mExecutorService = null;
    private static final int threadPoolMaxSize = 6;
    private ImageWorker() {
    }
    private static ImageWorker mImageWorker = null;
    private static String bitmapPath = "";

    private static synchronized void getImageWorkerInstance(){
        if(mImageWorker == null){
            mImageWorker = new ImageWorker();
            mExecutorService = Executors.newFixedThreadPool(threadPoolMaxSize);
        }
    }

    public static void loadImage(ImageView mImageView, String imgUrl,
                                 Handler mHandler) {
        getImageWorkerInstance();
        Bitmap bitmapCache = getBitmapFromCache(imgUrl);
        if(bitmapCache!=null){
            mImageView.setImageBitmap(bitmapCache);
        }else {
            Bitmap bitmapDisk = getBitmapFromDisk(imgUrl);
            if(bitmapDisk!=null){
                addBitmapToLruCache(imgUrl, bitmapDisk);
                mImageView.setImageBitmap(bitmapDisk);
            }else{
                loadImageFromNetwork(mImageView, imgUrl, mHandler, mLruCache, bitmapPath);
            }
        }
    }


    private static void loadImageFromNetwork(ImageView mImageView, String imgUrl, Handler mHandler, LruCache<String, Bitmap> mLruCache, String bitmapPath) {
        mExecutorService.submit(new RemoteImage(mImageView,imgUrl,mHandler,mLruCache,bitmapPath));
    }



    private static void addBitmapToLruCache(String imgUrl, Bitmap bitmapDisk) {
        mLruCache.put(imgUrl,bitmapDisk);
    }



    private static Bitmap getBitmapFromDisk(String imgUrl) {
        InputStream inputStream = null;
        String[] fileNames = imgUrl.split("/");
        String fileName = fileNames[fileNames.length-1];
        try {
            inputStream = new FileInputStream(bitmapPath + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }


    private static Bitmap getBitmapFromCache(String imgUrl) {
        return mLruCache.get(imgUrl);
    }
}
