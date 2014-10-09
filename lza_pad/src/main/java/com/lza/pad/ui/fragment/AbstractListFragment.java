package com.lza.pad.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockListFragment;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-18.
 */
public class AbstractListFragment extends SherlockListFragment implements Consts {

    protected Bundle mArguments;
    protected NavigationInfo mNavInfo;

    protected ProgressDialog mProgressDialog = null;
    protected Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mArguments = getArguments();
        if (mArguments != null) {
            mNavInfo = mArguments.getParcelable(KEY_NAVIGATION_INFO);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /*mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getResources().getString(R.string.ebook_content_loading));
        mProgressDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        }, DISMISS_PROGRESS_DIALOG_DELAY);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }*/
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
