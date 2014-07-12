package images.load.android.com.loadimages.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import images.load.android.com.loadimages.ImageConfig;
import images.load.android.com.loadimages.R;
import images.load.android.com.loadimages.bo.PhotoObject;
import images.load.android.com.loadimages.bo.Photos;
import images.load.android.com.loadimages.net.LoadImagesAPI;
import images.load.android.com.loadimages.ui.adapter.PhotoAdapter;


public class PhotoGridFragment extends PhotoFragment {

    private GridView mGridView;

    private int mPage = 1;

    private PhotoAdapter mAdapter;

    private List<PhotoObject> mPhotoObjectList = new ArrayList<PhotoObject>();

    private int mTotalPages;

    private boolean mIsLoading;

    private ProgressBar mProgressBar;

    private static final String TAG = PhotoGridFragment.class.getName();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.grid_view);
        mGridView.setOnScrollListener(gridScrollListener);
        mGridView.setOnItemClickListener(gridItemClickListener);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImages();
    }

    private void loadImages() {
        showProgressBar(true);
        mIsLoading = true;
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

    private void showProgressBar(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private Response.Listener<Photos> mPhotosListener = new Response.Listener<Photos>() {
        @Override
        public void onResponse(Photos photos) {
            showProgressBar(false);
            mIsLoading = false;
            if (photos.getPhotoInfo() != null) {
                mTotalPages = photos.getPhotoInfo().getPages();
                mPhotoObjectList.addAll(photos.getPhotoInfo().getPhotoList());
                updateGridView(photos.getPhotoInfo().getPhotoList());
            } else {
                Log.e(TAG, "Photo response is empty");
            }
        }

    };


    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            showProgressBar(false);
            mIsLoading = false;
            Log.e(TAG, volleyError.getMessage(), volleyError.getCause());
        }
    };

    private AbsListView.OnScrollListener gridScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!mIsLoading && mPage < mTotalPages && (firstVisibleItem + visibleItemCount) >= mPhotoObjectList.size()) {
                mPage++;
                Log.i(TAG, "Displaying page: " + mPage + " out of: " + mTotalPages);
                loadImages();
            }
        }
    };

    private AdapterView.OnItemClickListener gridItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            PhotoObject photoObject = mPhotoObjectList.get(position);

            String photoUrl = LoadImagesAPI.getImageEndpoint(photoObject.getFarm(), photoObject.getServer(), photoObject.getId(), photoObject.getSecret());

            mListener.setPhotoUrl(photoUrl);
            mListener.setPhotoDescription(photoObject.getTitle());


            if (!ImageConfig.IsTablet()) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.grid_fragment, PhotoDetailFragment.newInstance(), PhotoDetailFragment.TAG)
                        .addToBackStack(null).commit();
            } else {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment, PhotoDetailFragment.newInstance(), PhotoDetailFragment.TAG)
                        .commit();
            }
        }
    };
}
