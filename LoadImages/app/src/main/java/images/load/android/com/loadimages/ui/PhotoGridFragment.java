package images.load.android.com.loadimages.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import images.load.android.com.loadimages.R;
import images.load.android.com.loadimages.bo.PhotoObject;
import images.load.android.com.loadimages.bo.Photos;
import images.load.android.com.loadimages.net.LoadImagesAPI;
import images.load.android.com.loadimages.ui.adapter.PhotoAdapter;


public class PhotoGridFragment extends Fragment {

    private GridView mGridView;

    private int mPage = 1;

    private PhotoAdapter mAdapter;

    private List<PhotoObject> mPhotoObjectList = new ArrayList<PhotoObject>();

    private int mTotalPages;

    private static final String TAG = PhotoGridFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.grid_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadImagesAPI.getPhotoList(mPage, mPhotosListener, mErrorListener);
    }

    private void updateGridView(List<PhotoObject> list) {
        if (mAdapter == null) {
            mAdapter = new PhotoAdapter(getActivity());
            mGridView.setAdapter(mAdapter);
            mAdapter.setPhotos(mPhotoObjectList);
        } else {
            mAdapter.addPhotos(list);
        }
    }


    private Response.Listener<Photos> mPhotosListener = new Response.Listener<Photos>() {
        @Override
        public void onResponse(Photos photos) {
            if (photos.getPhotoInfo() != null) {
                mTotalPages = photos.getPhotoInfo().getPages();
                mPhotoObjectList.addAll(photos.getPhotoInfo().getPhotoList());
                updateGridView(photos.getPhotoInfo().getPhotoList());
            } else {
                Log.e(TAG, "Photo response is empty");
            }

        }

        @Override
        public void onResponseHeader(Map<String, String> stringStringMap) {

        }
    };


    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.getMessage(), volleyError.getCause());
        }
    };
}
