package images.load.android.com.loadimages.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

    private boolean mIsSearching;

    private String mSearchText;

    private static final String TAG = PhotoGridFragment.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.grid_view);
        mGridView.setOnScrollListener(gridScrollListener);
        mGridView.setOnItemClickListener(gridItemClickListener);

        // Sets the choice mode of the GridView depending if the running device
        // is a tablet or not
        if (ImageConfig.IsTablet()) {
            mGridView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        } else {
            mGridView.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        }
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(querySearchListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadImages();
    }

    /**
     *  Loads by default the latest images from the service flickr.interestingness.getList()
     */
    private void loadImages() {
        showProgressBar(true);
        mIsLoading = true;
        LoadImagesAPI.getPhotoList(mPage, mPhotosListener, mErrorListener);
    }

    /**
     *  Loads by default the latest images from the service flickr.photos.search()
     */
    private void searchImages() {
        showProgressBar(true);
        mIsLoading = true;
        LoadImagesAPI.getSearchPhoto(mPage, mSearchText, mPhotosListener, mErrorListener);
    }


    /**
     * Hides or displays the progress bar
     */
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

                if (mAdapter == null) {
                    mAdapter = new PhotoAdapter(getActivity());
                    mGridView.setAdapter(mAdapter);
                    mAdapter.setPhotos(mPhotoObjectList);
                }

                if (mPage == 1 && ImageConfig.IsTablet()) {
                    checkFirstPosition();
                }
                mPage++;
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

            // Calls the corresponding service to display more images when the user has reach the end of the page
            if (!mIsLoading && mPage < mTotalPages && (firstVisibleItem + visibleItemCount) >= mPhotoObjectList.size()) {
                Log.i(TAG, "Displaying page: " + mPage + " out of: " + mTotalPages);
                if (mIsSearching) {
                    searchImages();
                } else {
                    loadImages();
                }
            }
        }
    };

    private AdapterView.OnItemClickListener gridItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            performClick(position);
        }
    };


    /**
     * Checks the first position of the gridview
     * This method should be called only when running in tablets
     */
    private void checkFirstPosition() {
        mGridView.setItemChecked(0, true);
        performClick(0);
    }


    /**
     *  Method called to when a click action is executed
     */
    private void performClick(int position) {
        PhotoObject photoObject = mPhotoObjectList.get(position);

        String photoUrl = LoadImagesAPI.getImageEndpoint(photoObject.getFarm(), photoObject.getServer(), photoObject.getId(), photoObject.getSecret());

        mListener.setPhotoUrl(photoUrl);
        mListener.setPhotoDescription(photoObject.getTitle());


        if (ImageConfig.IsTablet()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, PhotoDetailFragment.newInstance(), PhotoDetailFragment.TAG)
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.grid_fragment, PhotoDetailFragment.newInstance(), PhotoDetailFragment.TAG)
                    .addToBackStack(null).commit();
        }
    }

    /**
     * Query Listener used in the search view of the actionbar
     */
    private SearchView.OnQueryTextListener querySearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            mIsSearching = true;
            mSearchText = s;
            mPage = 1;
            mAdapter.clearAll();
            if (ImageConfig.IsTablet()) {
                mGridView.setItemChecked(mGridView.getCheckedItemPosition(), false);
            }
            searchImages();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };
}
