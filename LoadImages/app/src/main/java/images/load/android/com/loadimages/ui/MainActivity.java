package images.load.android.com.loadimages.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import images.load.android.com.loadimages.R;


public class MainActivity extends ActionBarActivity implements PhotoCommunicator {

    private String mPhotoUrl;

    private String mPhotoDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
    }

    @Override
    public void setPhotoUrl(String photoUrl) {
        this.mPhotoUrl = photoUrl;
    }

    @Override
    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    @Override
    public void setPhotoDescription(String description) {
        this.mPhotoDescription = description;
    }

    @Override
    public String getPhotoDescription() {
        return mPhotoDescription;
    }
}
