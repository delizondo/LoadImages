package images.load.android.com.loadimages.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import images.load.android.com.loadimages.LoadImagesApplication;
import images.load.android.com.loadimages.R;
import images.load.android.com.loadimages.bo.PhotoObject;
import images.load.android.com.loadimages.net.LoadImagesAPI;


public class PhotoAdapter extends BaseAdapter {

    private List<PhotoObject> mList = new ArrayList<PhotoObject>();

    private final LayoutInflater mInflater;

    public PhotoAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setPhotos(List<PhotoObject> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addPhotos(List<PhotoObject> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll(){
        mList.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PhotoObject getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.grid_cell, parent, false);
            viewHolder.mThumb = (NetworkImageView) convertView.findViewById(R.id.photo_thumb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhotoObject photoObject = getItem(position);

        viewHolder.mThumb.setImageUrl(LoadImagesAPI.getThumbnailEndpoint(photoObject.getFarm(), photoObject.getServer(), photoObject.getId(), photoObject.getSecret()), LoadImagesApplication.getInstance().getImageLoader());

        return convertView;
    }

    private class ViewHolder {
        private NetworkImageView mThumb;
    }
}
