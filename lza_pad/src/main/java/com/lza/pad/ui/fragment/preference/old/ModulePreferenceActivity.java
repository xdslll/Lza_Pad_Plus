package com.lza.pad.ui.fragment.preference.old;

import android.content.Intent;
import android.os.Bundle;

import com.lza.pad.ui.fragment.preference.BasePreferenceActivity;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
@Deprecated
public class ModulePreferenceActivity extends BasePreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getSupportActionBar().setSelectedNavigationItem(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setSelectedNavigationItem(1);
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.pref_container, new ModulePreference())
                .commit();*/
    }

}
