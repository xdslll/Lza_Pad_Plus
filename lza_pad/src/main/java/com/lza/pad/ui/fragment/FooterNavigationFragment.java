package com.lza.pad.ui.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.ui.activity.main.HomeActivity;

import java.util.List;

/**
 * 底部导航栏
 *
 * @author Sam
 * @Date 14-9-11
 */
public class FooterNavigationFragment extends Fragment implements Consts {

    private RadioGroup mNavigationGroup;
    private NavigationInfoDao mNavigationInfoDao = NavigationInfoDao.getInstance();
    private static List<NavigationInfo> sNavigationInfos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.footer_navigation, container, false);
        mNavigationGroup = (RadioGroup) view.findViewById(R.id.footer_navigation_group);
        if (mNavigationGroup.getChildCount() > 0)
            resetNavigation();
        else
            initNavigation();
        return view;
    }

    /**
     * 如果没有导航栏数据，则初始化导航栏数据库
     */
    public void initNavigationData() {
        long count = NavigationInfoDao.getInstance().count();
        if (count == 0)
            mNavigationInfoDao.initNavigationData(getActivity());
    }

    /**
     * 重置导航栏(当导航栏数据发生变更时调用)
     */
    public void resetNavigation() {
        mNavigationGroup.removeAllViews();
        initNavigationData();
    }

    /**
     * 初始化导航栏界面,包括:
     * 1. 读取数据库中导航栏数据,动态添加RadioButton
     * 2. 添加导航栏的点击事件，链接到对应的Fragment
     */
    public void initNavigation() {
        initNavigationData();
        //sNavigationInfos = mNavigationInfoDao.queryAllData();
        sNavigationInfos = mNavigationInfoDao.queryNotClosedAndActivated();

        if (sNavigationInfos != null && sNavigationInfos.size() > 0) {
            Resources res = getActivity().getResources();
            String packageName = getActivity().getPackageName();
            //计算底部导航栏各元素的宽度,根据屏幕尺寸来确定每行显示的数据量
            //int W = UniversalUtility.getPoint(getActivity()).x;
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int W = display.getWidth();
            int size = sNavigationInfos.size();
            int maxSize = res.getInteger(R.integer.navigation_max_child);
            int width;
            if (size > 0 && size <= maxSize) {
                width = W / size;
            } else {
                width = W / maxSize;
            }

            mNavigationGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    try {
                        String activityPath = sNavigationInfos.get(checkedId).getActivityPath();
                        Class intentClass = Class.forName(activityPath);
                        Fragment fragment = (Fragment) intentClass.newInstance();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        /*Fragment frt = fm.findFragmentByTag(TAG_EBOOK_CONTENT);
                        if (frt != null) {
                            ft.remove(frt);
                        }*/
                        //传递参数
                        Bundle bundle = new Bundle();
                        NavigationInfo nav = sNavigationInfos.get(checkedId);
                        bundle.putParcelable(Consts.KEY_NAVIGATION_INFO, nav);
                        //bundle.putParcelable(Consts.KEY_NAVIGATION_INFO, sNavigationInfos.get(checkedId));
                        fragment.setArguments(bundle);

                        ft.replace(R.id.home_container, fragment, HomeActivity.CONTAINER_FRAGMENT_TAG);
                        ft.commit();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });

            for (int i = 0; i < size; i++) {
                RadioButton rb = new RadioButton(getActivity());
                int resId = res.getIdentifier(sNavigationInfos.get(i).getResName(), "drawable", packageName);
                Drawable drawable = res.getDrawable(resId);
                rb.setBackgroundDrawable(drawable);
                //rb.setText(sNavigationInfos.get(i).getName());
                rb.setSingleLine();
                rb.setGravity(Gravity.CENTER);
                rb.setId(i);
                rb.setLayoutParams(new RadioGroup.LayoutParams(
                        width,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                mNavigationGroup.addView(rb);
                if (i == 0)
                    rb.setChecked(true);
                else
                    rb.setChecked(false);
            }
        }
    }

}
