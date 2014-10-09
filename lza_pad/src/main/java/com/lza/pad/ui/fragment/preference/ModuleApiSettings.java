package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.core.utils.ToastUtilsSimplify;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-23.
 */
public class ModuleApiSettings extends AbstractPreferenceFragment {

    private static final String PREF_CATEGORY = "global_api_settings";
    private static final String PREF_API_URL = "global_api_url";
    private static final String PREF_CONTROL = "global_api_control";
    private static final String PREF_ACTION = "global_api_action";
    private static final String PREF_CITY_CODE = "global_api_city_code";
    private static final String PREF_SCHOOL_ID = "global_api_school_id";
    private static final String PREF_DEVICE_CODE = "global_api_device_code";

    private static final int REQUEST_UPDATE_API_URL = 0x001;
    private static final int REQUEST_UPDATE_API_CITY_CODE = 0x002;
    private static final int REQUEST_UPDATE_API_DEVICE_CODE = 0x003;
    private static final int REQUEST_UPDATE_API_SCHOOL_ID = 0x004;
    private static final int REQUEST_UPDATE_API_CONTROL = 0x005;
    private static final int REQUEST_UPDATE_API_ACTION = 0x006;

    private PreferenceCategory mPrefCategory;
    private EditTextPreference mPrefApiUrl, mPrefCityCode, mPrefSchoolId, mPrefDeviceCode, mPrefControl, mPrefAction;

    private NavigationInfo mNav;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_module_more_api);
        Bundle arg = getArguments();
        mNav = arg.getParcelable(Consts.KEY_NAVIGATION_INFO);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPrefCategory = (PreferenceCategory) getPreferenceScreen().findPreference(PREF_CATEGORY);
        mPrefApiUrl = (EditTextPreference) getPreferenceScreen().findPreference(PREF_API_URL);
        mPrefCityCode = (EditTextPreference) getPreferenceScreen().findPreference(PREF_CITY_CODE);
        mPrefSchoolId = (EditTextPreference) getPreferenceScreen().findPreference(PREF_SCHOOL_ID);
        mPrefDeviceCode = (EditTextPreference) getPreferenceScreen().findPreference(PREF_DEVICE_CODE);
        mPrefControl = (EditTextPreference) getPreferenceScreen().findPreference(PREF_CONTROL);
        mPrefAction = (EditTextPreference) getPreferenceScreen().findPreference(PREF_ACTION);

        if (mNav != null) {
            mPrefCategory.setTitle(mNav.getName());
            mPrefApiUrl.setSummary(mNav.getApiUrl());
            mPrefCityCode.setSummary(String.valueOf(mNav.getApiCityCode()));
            mPrefSchoolId.setSummary(String.valueOf(mNav.getApiSchoolIdPar()));
            mPrefDeviceCode.setSummary(String.valueOf(mNav.getApiDeviceCode()));
            mPrefControl.setSummary(mNav.getApiControlPar());
            mPrefAction.setSummary(mNav.getApiActionPar());
        }

        mPrefApiUrl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_URL);
                return true;
            }
        });

        mPrefCityCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_CITY_CODE);
                return true;
            }
        });

        mPrefSchoolId.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_SCHOOL_ID);
                return true;
            }
        });

        mPrefDeviceCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_DEVICE_CODE);
                return true;
            }
        });

        mPrefControl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_CONTROL);
                return true;
            }
        });

        mPrefAction.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                updateApi(preference, newValue, REQUEST_UPDATE_API_ACTION);
                return true;
            }
        });

    }

    private void updateApi(Preference preference, Object newValue, int requestCode) {
        String strValue = newValue.toString();
        int id = mNav.getId();
        int ret = 0;
        switch (requestCode) {
            case REQUEST_UPDATE_API_URL:
                if (strValue.endsWith("?")) {
                    strValue += "?";
                }
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._API_URL, strValue, NavigationInfo._ID, id);
                break;
            case REQUEST_UPDATE_API_CITY_CODE:
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._CITY_CODE, strValue, NavigationInfo._ID, id);
                break;
            case REQUEST_UPDATE_API_DEVICE_CODE:
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._DEVICE_CODE, strValue, NavigationInfo._ID, id);
                break;
            case REQUEST_UPDATE_API_SCHOOL_ID:
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._SCHOOL_ID, strValue, NavigationInfo._ID, id);
                break;
            case REQUEST_UPDATE_API_CONTROL:
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._CONTROL, strValue, NavigationInfo._ID, id);
                break;
            case REQUEST_UPDATE_API_ACTION:
                ret = NavigationInfoDao.getInstance().updateById(NavigationInfo._ACTION, strValue, NavigationInfo._ID, id);
                break;
        }
        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
        ToastUtilsSimplify.show(resultText);
        preference.setSummary(strValue);
    }


    @Override
    public void onBack(Object tag) {
        ModuleMoreSettings moreSettings = new ModuleMoreSettings();
        moreSettings.setArguments(getArguments());
        getFragmentManager().beginTransaction()
                .replace(R.id.pref_container, moreSettings)
                .commit();
    }
}
