package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.view.WindowManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lza.pad.R;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.exception.CrashHandler;
import com.lza.pad.core.utils.Consts;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by xiads on 14-9-8.
 */
public class AbstractPreferenceActivity extends SherlockPreferenceActivity implements Consts {

    protected NavigationInfo mNav;

    @Override
    protected void onResume() {
        super.onResume();
        //启动当前Activity的友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭当前Activity的友盟统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.pref_mod_title);

        Bundle arg = getIntent().getExtras();
        if (arg != null) {
            mNav = arg.getParcelable(KEY_NAVIGATION_INFO);
        }
        CrashHandler.getInstance(this).init();
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
