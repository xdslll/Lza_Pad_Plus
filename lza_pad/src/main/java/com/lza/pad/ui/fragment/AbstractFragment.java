package com.lza.pad.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.SherlockFragment;
import com.lza.pad.R;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.ui.widget.TitleBar;

/**
 * 各功能模块通用模板,包括:
 * 1. 对TitleBar的控制
 * 2. 获取NavigationInfo对象,并进行参数的初始化
 *
 * @author Sam
 * @Date 14-9-11
 */
public class AbstractFragment extends SherlockFragment implements Consts {

    protected Bundle mArguments;
    protected TitleBar mTitleBar;
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
        AppLogger.d("onCreate");

        mArguments = getArguments();
        if (mArguments != null) {
            mNavInfo = mArguments.getParcelable(KEY_NAVIGATION_INFO);
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        AppLogger.d("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        mTitleBar = (TitleBar) getActivity().findViewById(R.id.ebook_list_title_bar);
        if (mTitleBar != null && mNavInfo != null) {
            mTitleBar.handleTitleBar(mNavInfo);
        }
    }

    @Override
    public void onStart() {
        AppLogger.d("onStart");
        super.onStart();
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
        if (mNavInfo.getRunningMode() == 1 && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showProgressDialog() {
        mHandler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
    }

    protected void dismissProgressDialog() {
        mHandler.sendEmptyMessage(DISMISS_PROGRESS_DIALOG);
    }
}
