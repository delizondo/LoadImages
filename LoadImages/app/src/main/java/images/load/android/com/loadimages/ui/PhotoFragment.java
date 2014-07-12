package images.load.android.com.loadimages.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;


public class PhotoFragment extends Fragment {

    protected PhotoCommunicator mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (PhotoCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PhotoCommunicator");
        }
    }
}
