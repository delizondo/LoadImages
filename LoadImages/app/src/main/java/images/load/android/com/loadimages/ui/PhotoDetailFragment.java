package images.load.android.com.loadimages.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import images.load.android.com.loadimages.ImageConfig;
import images.load.android.com.loadimages.LoadImagesApplication;
import images.load.android.com.loadimages.R;


public class PhotoDetailFragment extends PhotoFragment {

    private NetworkImageView mImageView;

    private TextView mPhotoDesc;

    public static final String TAG = PhotoDetailFragment.class.getName();

    public static PhotoDetailFragment newInstance() {
        return new PhotoDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ImageConfig.IsTablet()) {
            setHasOptionsMenu(false);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_photo_layout, container, false);
        mImageView = (NetworkImageView) view.findViewById(R.id.photo_view);
        mPhotoDesc = (TextView) view.findViewById(R.id.photo_desc);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView.setImageUrl(mListener.getPhotoUrl(), LoadImagesApplication.getInstance().getImageLoader());
        mPhotoDesc.setText(mListener.getPhotoDescription());
    }
}
