package com.lza.pad.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.SherlockFragment;
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
    protected Handler mHandler = new Handler();

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
        if (mTitleBar != null && mNavInfo != null) {
            mTitleBar.handleTitleBar(mNavInfo);
        }
    }

    @Override
    public void onStart() {
        AppLogger.d("onStart");
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

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
