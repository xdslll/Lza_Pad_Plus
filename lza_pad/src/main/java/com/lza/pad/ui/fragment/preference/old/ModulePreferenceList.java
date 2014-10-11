package com.lza.pad.ui.fragment.preference.old;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lza.pad.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.ui.adapter.ModulePreferenceListAdapter;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
@Deprecated
public class ModulePreferenceList extends Preference implements Consts {

    private Activity mActivity;
    private List<NavigationInfo> mNavs;

    private ListView mListView;
    private ModulePreferenceListAdapter mAdapter;



    private Intent mBroadIntent;


    public ModulePreferenceList(Activity activity, List<NavigationInfo> navs) {
        super(activity);
        this.mActivity = activity;
        this.mNavs = navs;
        //mBroadIntent = new Intent(ModulePreference.ACTION_FORCE_LOAD_BROAD);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pref_module_layout, parent, false);


        /*
        updateView();*/
        return view;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        //mAdapter = new ModulePreferenceListAdapter(mActivity, mNavs);
        if (mAdapter != null && mListView != null) {
            mListView.setAdapter(mAdapter);
        }
    }

    private void notifyParentUpdate() {
        mActivity.sendBroadcast(mBroadIntent);
    }

    private void updateSort(NavigationInfo newNav, NavigationInfo oldNav) {
        int ret1 = NavigationInfoDao.getInstance().updateData(newNav);
        int ret2 = NavigationInfoDao.getInstance().updateData(oldNav);
        if (ret1 == 1 && ret2 == 1) {
            notifyParentUpdate();
        }
    }

    /*private void sortUp() {
        int sortId = mNav.getSortId();
        if (sortId - 1 == 0) {
            ToastUtilsSimplify.show(R.string.pref_mod_sort_top);
            return;
        }

        NavigationInfo prevNav = NavigationInfoDao
                .getInstance().queryNotClosedBySortId(sortId - 1);
        mNav.setSortId(sortId - 1);
        prevNav.setSortId(sortId);
        updateSort(prevNav, mNav);
    }

    private void sortDown() {
        int sortId = mNav.getSortId();
        long maxId = NavigationInfoDao.getInstance().countNotClosed();
        if (sortId + 1 > maxId) {
            ToastUtilsSimplify.show(R.string.pref_mod_sort_bottom);
            return;
        }

        NavigationInfo nextNav = NavigationInfoDao
                .getInstance().queryNotClosedBySortId(sortId + 1);
        mNav.setSortId(sortId + 1);
        nextNav.setSortId(sortId);
        updateSort(nextNav, mNav);
    }

    private void setImageViewTouchEvent(ImageView view, final boolean ifUp) {
        view.setOnTouchListener(new View.OnTouchListener() {

            private void setBgAndUpate(View v) {
                v.setBackgroundResource(R.drawable.circle_background);
                if (ifUp)
                    sortUp();
                else
                    sortDown();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.circle_background_click);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        setBgAndUpate(v);
                        return true;
                    case MotionEvent.ACTION_UP:
                        setBgAndUpate(v);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        setBgAndUpate(v);
                        return true;
                }
                return false;
            }
        });
    }

    public void updateView() {
        if (mNav != null) {
            String type = mNav.getApiControlPar();
            long dataAmount = EbookDao.getInstance().countOfByType(type);
            mTxtDataAmount.setText("数据量: " + dataAmount + " 条");

            StringBuilder summary = new StringBuilder();
            summary.append("排序序号:").append(mNav.getSortId())
                    .append(",接口地址:").append(mNav.getApiUrl())
                    .append("\ncontrol:").append(mNav.getApiControlPar())
                    .append(",action:").append(mNav.getApiActionPar())
                    .append(",page:").append(mNav.getApiPagePar())
                    .append(",pagesize:").append(mNav.getApiPageSizePar())
                    .append(",schoolId:").append(mNav.getApiSchoolIdPar());

            setTitle(mNav.getName());
            setSummary(summary.toString());
            int isActivate = mNav.getIsActivated();
            if (isActivate == 0)
                mCbxLaunch.setChecked(false);
            else
                mCbxLaunch.setChecked(true);
            mCbxLaunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        mNav.setIsActivated(1);
                    else
                        mNav.setIsActivated(0);
                    int ret = NavigationInfoDao.getInstance().updateData(mNav);
                    if (ret == 1) {
                        mActivity.sendBroadcast(mBroadIntent);
                    }
                }
            });
            setImageViewTouchEvent(mImgSortUp, true);
            setImageViewTouchEvent(mImgSortDown, false);
            mBtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModuleMoreSettings preference = new ModuleMoreSettings();
                    if (mNav != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Consts.KEY_NAVIGATION_INFO, mNav);
                        preference.setArguments(bundle);
                    }
                    FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                    ft.replace(R.id.pref_container, preference).commit();
                }
            });
            mBtnCache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mIsCache) {
                        UniversalUtility.showDialog(mActivity,
                                R.string.pref_mod_cache_title,
                                R.string.pref_mod_cache_message,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //mTxtDownload.setVisibility(View.VISIBLE);
                                        //mTxtDownload.setText(R.string.pref_mod_cache_connection);
                                        CacheDataFragment fragment = new CacheDataFragment();
                                        Bundle arguments = new Bundle();
                                        arguments.putParcelable(KEY_NAVIGATION_INFO, mNav);
                                        fragment.setArguments(arguments);
                                        mActivity.getFragmentManager().beginTransaction()
                                                .replace(R.id.pref_container, fragment)
                                                .commit();

                                        *//*cacheData();
                                        mIsCache = true;
                                        mBtnCache.setEnabled(false);
                                        *//*
                                        //mBtnCache.setText(R.string.pref_mod_cache_btn_cancel);
                                    }
                                },
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();

                                    }
                                });
                    }else {
                        cancelCache();
                        mIsCache = false;
                        mBtnCache.setText(R.string.pref_mod_cache_btn_start);
                    }
                }
            });

        }
    }

    private boolean mIsCache = false;

    EbookRequestTask mTask = null;
    private void cacheData() {
        mTask = new EbookRequestTask(mActivity, mNav);
        mTask.setHandler(mHandler);
        mTask.execute();
    }

    private void cancelCache() {
        if (mTask != null) {
            mTask.cancel();
        }
    }

    public void setTitle(String s) {
        if (mTxtTitle != null)
            mTxtTitle.setText(s);
    }

    public void setSummary(String s) {
        if (mTxtSummary != null)
            mTxtSummary.setText(s);
    }

    public static final int UPDATE_DOWNLOAD_TEXT = 0x001;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_DOWNLOAD_TEXT:
                    mTxtDownload.setVisibility(View.VISIBLE);
                    mTxtDownload.setText("okok,it's work!");
                    break;
                default:
            }
        }
    };*/

}
