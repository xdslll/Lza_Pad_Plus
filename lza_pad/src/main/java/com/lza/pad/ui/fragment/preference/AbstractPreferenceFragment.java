package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-18.
 */
@Deprecated
public abstract class AbstractPreferenceFragment extends PreferenceFragment
        implements MainPreferenceActivity.OnBackClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainPreferenceActivity pa = (MainPreferenceActivity) getActivity();
        if (pa != null) {
            pa.setOnBackClickListener(this);
        }
    }
}
