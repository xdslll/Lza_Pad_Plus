package com.lza.pad.ui.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lza.pad.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.EbookCountStrategy;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.utils.UniversalUtility;
import com.lza.pad.ui.fragment.preference.CacheDataFragment;
import com.lza.pad.ui.fragment.preference.ModuleMoreSettings;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class ModulePreferenceListAdapter extends BaseAdapter implements Consts {

    private Context mContext;
    private List<NavigationInfo> mNavs;

    public ModulePreferenceListAdapter(Context mContext, List<NavigationInfo> navs) {
        this.mContext = mContext;
        this.mNavs = navs;
    }

    @Override
    public int getCount() {
        return mNavs.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pref_module_item, null);
        viewHolder.mBtnMore = (Button) convertView.findViewById(R.id.pref_mod_more_settings);
        viewHolder.mBtnCache = (Button) convertView.findViewById(R.id.pref_mod_cache);
        viewHolder.mCbxLaunch = (CheckBox) convertView.findViewById(R.id.pref_mod_cbx);
        viewHolder.mTxtTitle = (TextView) convertView.findViewById(R.id.pref_mod_title);
        viewHolder.mTxtSummary = (TextView) convertView.findViewById(R.id.pref_mod_summary);
        viewHolder.mImgSortUp = (ImageView) convertView.findViewById(R.id.pref_mod_sort_up);
        viewHolder.mImgSortDown = (ImageView) convertView.findViewById(R.id.pref_mod_sort_down);
        viewHolder.mTxtDownload = (TextView) convertView.findViewById(R.id.pref_mod_download_text);
        viewHolder.mTxtDataAmount = (TextView) convertView.findViewById(R.id.pref_mod_data_amout);
        convertView.setTag(viewHolder);

        final NavigationInfo nav = mNavs.get(position);
        if (nav != null) {

            //String type = nav.getApiControlPar();
            //long dataAmount = EbookDao.getInstance().countOfByType(type);

            EbookCountStrategy countStrategy = new EbookCountStrategy(nav);
            long dataAmount = countStrategy.operation();
            viewHolder.mTxtDataAmount.setText("数据量: " + dataAmount + " 条");

            StringBuilder summary = new StringBuilder();
            summary.append("排序序号:").append(nav.getSortId())
                    .append("\n接口地址:").append(nav.getApiUrl());
                    /*.append("\ncontrol:").append(nav.getApiControlPar())
                    .append(",action:").append(nav.getApiActionPar())
                    .append(",page:").append(nav.getApiPagePar())
                    .append(",pagesize:").append(nav.getApiPageSizePar())
                    .append(",schoolId:").append(nav.getApiSchoolIdPar());*/

            viewHolder.mTxtTitle.setText(nav.getName());
            viewHolder.mTxtSummary.setText(summary.toString());
            int isActivate = nav.getIsActivated();
            if (isActivate == 0)
                viewHolder.mCbxLaunch.setChecked(false);
            else
                viewHolder.mCbxLaunch.setChecked(true);
            viewHolder.mCbxLaunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        nav.setIsActivated(1);
                    }else {
                        nav.setIsActivated(0);
                    }
                    updateNavigation(nav);
                }
            });
            setImageViewTouchEvent(nav, viewHolder.mImgSortUp, true);
            setImageViewTouchEvent(nav, viewHolder.mImgSortDown, false);
            viewHolder.mBtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModuleMoreSettings preference = new ModuleMoreSettings();
                    if (nav != null) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Consts.KEY_NAVIGATION_INFO, nav);
                        preference.setArguments(bundle);
                    }
                    FragmentTransaction ft = ((Activity) mContext).getFragmentManager().beginTransaction();
                    ft.replace(R.id.pref_container, preference).commit();
                }
            });
            viewHolder.mBtnCache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UniversalUtility.showDialog(mContext,
                            R.string.pref_mod_cache_title,
                            R.string.pref_mod_cache_message,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CacheDataFragment fragment = new CacheDataFragment();
                                    Bundle arguments = new Bundle();
                                    arguments.putParcelable(KEY_NAVIGATION_INFO, nav);
                                    fragment.setArguments(arguments);
                                    ((SherlockFragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.pref_container, fragment)
                                            .commit();
                                }
                            },
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                }
            });
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView mTxtTitle, mTxtSummary, mTxtDataAmount, mTxtDownload;
        public Button mBtnMore, mBtnCache;
        public CheckBox mCbxLaunch;
        public ImageView mImgSortUp, mImgSortDown;
    }

    private void setImageViewTouchEvent(final NavigationInfo nav, ImageView view, final boolean ifUp) {
        view.setOnTouchListener(new View.OnTouchListener() {

            private void setBgAndUpate(View v) {
                v.setBackgroundResource(R.drawable.circle_background);
                if (ifUp)
                    sortUp(nav);
                else
                    sortDown(nav);
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

    private void sortUp(NavigationInfo nav) {
        int sortId = nav.getSortId();
        if (sortId - 1 <= 0) {
            ToastUtilsSimplify.show(R.string.pref_mod_sort_top);
            return;
        }

        int index = sortId - 1;
        int prevIndex = index - 1;
        NavigationInfo prevNav = mNavs.get(prevIndex);
        prevNav.setSortId(sortId);
        nav.setSortId(sortId - 1);

        mNavs.set(prevIndex, nav);
        mNavs.set(index, prevNav);
        notifyDataSetChanged();

        updateSort(prevNav, nav);
    }

    private void sortDown(NavigationInfo nav) {
        int sortId = nav.getSortId();
        //long maxId = NavigationInfoDao.getInstance().countNotClosed();
        int maxSortId = mNavs.size();
        if (sortId + 1 > maxSortId) {
            ToastUtilsSimplify.show(R.string.pref_mod_sort_bottom);
            return;
        }

        int index = sortId - 1;
        int nextIndex = index + 1;
        NavigationInfo nextNav = mNavs.get(nextIndex);
        nextNav.setSortId(sortId);
        nav.setSortId(sortId + 1);

        mNavs.set(nextIndex, nav);
        mNavs.set(index, nextNav);
        notifyDataSetChanged();

        updateSort(nextNav, nav);
    }

    private void updateSort(NavigationInfo newNav, NavigationInfo oldNav) {
        updateNavigation(newNav);
        updateNavigation(oldNav);
    }

    private void updateNavigation(NavigationInfo nav) {
        NavigationInfoDao.getInstance().updateData(nav);
    }
}
