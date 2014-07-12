package images.load.android.com.loadimages.net;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import images.load.android.com.loadimages.ImageConfig;
import images.load.android.com.loadimages.LoadImagesApplication;
import images.load.android.com.loadimages.bo.Photos;

public class LoadImagesAPI {

    private static final String ENDPOINT_SUFIX = "&format=json&nojsoncallback=1&per_page=%s&page=%s";

    private static final String IMAGE_LIST_ENDPOINT = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=%s" + ENDPOINT_SUFIX;

    private static final String THUMB_IMAGE_ENDPOINT = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    private static final String SEARCH_IMAGE_ENDPOINT = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&text=%s" + ENDPOINT_SUFIX;

    private static final String THUMB_KEY = "m";

    private static final String IMAGE_KEY = "c";

    private static RequestQueue mRequestQueue = LoadImagesApplication.getInstance().getRequestQueue();

    public static void getPhotoList(int page, Response.Listener<Photos> listener, Response.ErrorListener errorListener) {
        JsonRequest request = new JsonRequest(Request.Method.GET, String.format(IMAGE_LIST_ENDPOINT,
                ImageConfig.getApiKey(), ImageConfig.getImagesPerPage(), page),
                Photos.class, listener, errorListener
        );
        mRequestQueue.add(request);
    }

    public static void getSearchPhoto(int page, String text, Response.Listener<Photos> listener, Response.ErrorListener errorListener) {
        JsonRequest request = new JsonRequest(Request.Method.GET, String.format(SEARCH_IMAGE_ENDPOINT,
                ImageConfig.getApiKey(), text, ImageConfig.getImagesPerPage(), page),
                Photos.class, listener, errorListener
        );
        mRequestQueue.add(request);
    }

    public static String getThumbnailEndpoint(int farm, String server, long id, String secret) {
        return String.format(THUMB_IMAGE_ENDPOINT, farm, server, id, secret, THUMB_KEY);
    }


    public static String getImageEndpoint(int farm, String server, long id, String secret) {
        return String.format(THUMB_IMAGE_ENDPOINT, farm, server, id, secret, IMAGE_KEY);
    }

}
