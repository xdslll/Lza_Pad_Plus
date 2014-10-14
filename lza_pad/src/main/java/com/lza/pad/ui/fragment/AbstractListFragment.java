package com.lza.pad.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.SherlockListFragment;
import com.lza.pad.R;
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
    private static final int SHOW_PROGRESS_DIALOG = 0x001;
    private static final int DISMISS_PROGRESS_DIALOG = 0x002;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS_DIALOG :
                    _showProgressDialog();
                    break;
                case DISMISS_PROGRESS_DIALOG:
                    _dismissProgressDialog();
                    break;
                default:
            }
        }
    };

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
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void _showProgressDialog() {
        if (mNavInfo != null && mNavInfo.getRunningMode() == 1) {
            if (getActivity() != null) {
                mProgressDialog = new ProgressDialog(getActivity());
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
                }, DISMISS_PROGRESS_DIALOG_DELAY);
            }
        }
    }

    private void _dismissProgressDialog() {
        if (mNavInfo != null && mNavInfo.getRunningMode() == 1 && mProgressDialog != null) {
            if (getActivity() != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    protected void showProgressDialog() {
        mHandler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
    }

    protected void dismissProgressDialog() {
        mHandler.sendEmptyMessage(DISMISS_PROGRESS_DIALOG);
    }
}
