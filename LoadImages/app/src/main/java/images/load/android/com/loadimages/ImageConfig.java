package images.load.android.com.loadimages;


import android.content.res.Resources;

public class ImageConfig {


    private static final String API_KEY;

    private static final boolean IS_TABLET;

    private static final String IMAGES_PER_PAGE;

    static {
        Resources res = LoadImagesApplication.getInstance().getResources();
        API_KEY = res.getString(R.string.api_key);
        IS_TABLET = res.getBoolean(R.bool.is_tablet);
        IMAGES_PER_PAGE = res.getString(R.string.images_per_page);
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static boolean IsTablet() {
        return IS_TABLET;
    }

    public static String getImagesPerPage(){
        return IMAGES_PER_PAGE;
    }
}
