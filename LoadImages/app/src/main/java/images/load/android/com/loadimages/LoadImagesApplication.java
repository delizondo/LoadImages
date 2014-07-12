package images.load.android.com.loadimages;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadImagesApplication extends Application {

    private ObjectMapper mObjectMapper;

    private RequestQueue mRequestQueue;

    private static LoadImagesApplication sInstance;

    private ImageLoader mImageLoader;

    public static LoadImagesApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mObjectMapper = new ObjectMapper();
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, BitmapLruCache.getInstance());
    }

    public ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
