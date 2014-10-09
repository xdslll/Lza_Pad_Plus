package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
public class ModuleMoreSettings extends AbstractPreferenceFragment implements Consts {

    private NavigationInfo mNav;
    private static final String PREF_ROW_NUMBER = "pref_mod_shelves_row_number";
    private static final String PREF_COL_NUMBER = "pref_mod_shelves_col_number";
    private static final String PREF_PREVIEW = "pref_mod_shelves_preview";
    private static final String PREF_API = "pref_mod_more_api";

    private EditTextPreference mRowPref;
    private EditTextPreference mColPref;
    private PreferenceScreen mApiPref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_module_more);
        Bundle arg = getArguments();
        if (arg != null) {
            mNav = arg.getParcelable(KEY_NAVIGATION_INFO);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mRowPref = (EditTextPreference) getPreferenceScreen().findPreference(PREF_ROW_NUMBER);
        mColPref = (EditTextPreference) getPreferenceScreen().findPreference(PREF_COL_NUMBER);
        mApiPref = (PreferenceScreen) getPreferenceScreen().findPreference(PREF_API);

        if (mNav != null) {
            int rowNumber = mNav.getDataRowNumber();
            int colNumber = mNav.getDataColumnNumber();
            updateRowTitle(rowNumber);
            updateColTitle(colNumber);
        }

        mRowPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
                Integer finalValue = Integer.valueOf(value);
                mNav.setDataRowNumber(finalValue);

                int colNumber = mNav.getDataColumnNumber();
                int pageSize = finalValue * colNumber;
                mNav.setApiPageSizePar(pageSize);

                NavigationInfoDao.getInstance().updateData(mNav);

                updateRowTitle(finalValue);
                return true;
            }
        });

        mColPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
                Integer finalValue = Integer.valueOf(value);
                mNav.setDataColumnNumber(finalValue);

                int rowNumber = mNav.getDataRowNumber();
                int pageSize = finalValue * rowNumber;
                mNav.setApiPageSizePar(pageSize);

                NavigationInfoDao.getInstance().updateData(mNav);

                updateColTitle(finalValue);
                return true;
            }
        });

        mApiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ModuleApiSettings apiSettings = new ModuleApiSettings();
                apiSettings.setArguments(getArguments());
                getFragmentManager().beginTransaction()
                        .replace(R.id.pref_container, apiSettings)
                        .commit();
                return true;
            }
        });
    }

    private void updateRowTitle(int rowNumber) {
        String rowTitle = String.format(getString(
                R.string.pref_mod_more_row_title), rowNumber);
        mRowPref.setTitle(rowTitle);

    }

    private void updateColTitle(int colNumber) {
        String colTitle = String.format(getString
                (R.string.pref_mod_more_col_title), colNumber);
        mColPref.setTitle(colTitle);
    }

    @Override
    public void onBack(Object tag) {
        ModulePreference fragment = new ModulePreference();
        /*getFragmentManager().beginTransaction()
                .replace(R.id.pref_container, fragment).commit();*/
    }
}
