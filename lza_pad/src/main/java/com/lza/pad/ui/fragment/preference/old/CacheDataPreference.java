package com.lza.pad.ui.fragment.preference.old;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lza.pad.R;
import com.lza.pad.core.utils.ToastUtilsSimplify;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
@Deprecated
public class CacheDataPreference extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_cache);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pref_cache_layout, container, false);
        Button btn = (Button) view.findViewById(R.id.pref_cache_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtilsSimplify.show("It's work!");
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
