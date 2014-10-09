package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lza.pad.R;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
@Deprecated
public class MainPreferenceActivity extends AbstractPreferenceActivity
        implements ActionBar.TabListener {

    public static final String TAB_GLOBAL_TAG = "global_tag";
    public static final String TAB_MODULE_TAG = "ebook_tag";
    public static final String TAB_SHELVES_TAG = "shelves_tag";
    /*public static final String TAB_CACHE_TAG = "cache_tag";
    public static final String TAB_JOURNAL_TAG = "journal_tag";
    public static final String TAB_NEWS_TAG = "news_tag";
    public static final String TAB_HELP_TAG = "help_tag";*/

    //public Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.pref_home_layout);
        addPreferencesFromResource(R.xml.pref_global);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(R.string.pref_title);

        ActionBar.Tab tab1 = actionBar.newTab()
                .setText(R.string.pref_tab_global)
                .setTabListener(this)
                .setTag(TAB_GLOBAL_TAG);
        ActionBar.Tab tab2 = actionBar.newTab()
                .setText(R.string.pref_tab_moudle)
                .setTabListener(this)
                .setTag(TAB_MODULE_TAG);
        /*ActionBar.Tab tab3 = actionBar.newTab()
                .setText(R.string.pref_tab_shelves)
                .setTabListener(this)
                .setTag(TAB_SHELVES_TAG);*/

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        //actionBar.addTab(tab3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setResult(RESULT_OK);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pref_back:
                //ToastUtilsSimplify.show("预留功能,返回上一个界面");
                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.pref_container, mCurrentFragment).commit();
                Object tag = getSupportActionBar().getSelectedTab().getTag();
                if (tag != null && mOnBackClickListener != null) {
                    mOnBackClickListener.onBack(tag);
                }
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private OnBackClickListener mOnBackClickListener;

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.mOnBackClickListener = onBackClickListener;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        /*if (tab.getTag().equals(TAB_GLOBAL_TAG)) {
            mCurrentFragment = new GlobalPreference();
        }else if(tab.getTag().equals(TAB_MODULE_TAG)) {
            mCurrentFragment = new ModulePreference();
        }else if (tab.getTag().equals(TAB_SHELVES_TAG)) {
            mCurrentFragment = new Fragment();
        }
        ft.replace(R.id.pref_container, mCurrentFragment);*/

        if (tab.getTag().equals(TAB_GLOBAL_TAG)) {

        }else if(tab.getTag().equals(TAB_MODULE_TAG)) {

        }else if (tab.getTag().equals(TAB_SHELVES_TAG)) {

        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    public interface OnBackClickListener {
        void onBack(Object tag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
