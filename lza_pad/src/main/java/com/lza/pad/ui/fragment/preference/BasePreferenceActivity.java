package com.lza.pad.ui.fragment.preference;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lza.pad.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiads on 14-9-8.
 */
public class BasePreferenceActivity extends SherlockFragmentActivity
        implements ActionBar.TabListener, Consts {

    private List<String> mData = new ArrayList<String>();
    private List<String> mTags = new ArrayList<String>();
    public static final String TAB_GLOBAL_TAG = "global_tag";
    public static final String TAB_MODULE_TAG = "ebook_tag";
    /*public static final String TAB_CACHE_TAG = "cache_tag";
    public static final String TAB_JOURNAL_TAG = "journal_tag";
    public static final String TAB_NEWS_TAG = "news_tag";
    public static final String TAB_HELP_TAG = "help_tag";*/

    @Override
    protected void onResume() {
        super.onResume();
        AppLogger.d("onResume");
        GlobalContext.getInstance().setCurrentRunningActivity(this);
        //启动当前Activity的友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppLogger.d("onPause");
        if (GlobalContext.getInstance().getCurrentRunningActivity() == this) {
            GlobalContext.getInstance().setCurrentRunningActivity(null);
        }
        //关闭当前Activity的友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        AppLogger.d("onConfigurationChanged");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppLogger.d("onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        AppLogger.d("onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLogger.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppLogger.d("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLogger.d("onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AppLogger.d("onSaveInstanceState");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppLogger.d("onCreate");
        GlobalContext.getInstance().setActivity(this);
        //设置友盟的DEBUG模式，发布时应关闭
        MobclickAgent.setDebugMode(true);

        mData.add("全局设置");
        mData.add("模块设置");
        mTags.add(TAB_GLOBAL_TAG);
        mTags.add(TAB_MODULE_TAG);
        /*ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                textView.setText(getItem(position));
                return convertView;
            }
        };
        mAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);*/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.pref_title);
        for (int i = 0; i < mData.size(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(mData.get(i))
                    .setTabListener(this)
                    .setTag(mTags.get(i)));
        }
        setContentView(R.layout.pref_home_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(TAG_EBOOK_CONTENT);
            if (fragment != null) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getTag().equals(TAB_GLOBAL_TAG)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.pref_container, new GlobalPreferenceFragment())
                    .commit();
        } else if (tab.getTag().equals(TAB_MODULE_TAG)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.pref_container, new ModulePreferenceFragment())
                    .commit();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
