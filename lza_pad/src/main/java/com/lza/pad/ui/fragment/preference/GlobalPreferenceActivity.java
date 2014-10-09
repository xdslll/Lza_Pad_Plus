package com.lza.pad.ui.fragment.preference;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.text.TextUtils;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.utils.UniversalUtility;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
@Deprecated
public class GlobalPreferenceActivity extends AbstractPreferenceActivity {

    private EditTextPreference mPrefApiUrl, mPrefCityCode, mPrefUniCode, mPrefDeviceCode;
    private static final String PREF_API_URL = "global_api_url";
    private static final String PREF_CITY_CODE = "global_api_city_code";
    private static final String PREF_UNIVERSITY_CODE = "global_api_university_code";
    private static final String PREF_DEVICE_CODE = "global_api_device_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_global);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getSupportActionBar().setSelectedNavigationItem(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setSelectedNavigationItem(0);
        mPrefApiUrl = (EditTextPreference) getPreferenceScreen().findPreference(PREF_API_URL);
        mPrefCityCode = (EditTextPreference) getPreferenceScreen().findPreference(PREF_CITY_CODE);
        mPrefUniCode = (EditTextPreference) getPreferenceScreen().findPreference(PREF_UNIVERSITY_CODE);
        mPrefDeviceCode = (EditTextPreference) getPreferenceScreen().findPreference(PREF_DEVICE_CODE);

        NavigationInfo nav = NavigationInfoDao.getInstance().queryNotClosedBySortId(1);
        if (nav != null) {
            if (TextUtils.isEmpty(mPrefApiUrl.getSummary())) {
                mPrefApiUrl.setSummary(nav.getApiUrl());
            }
            if (TextUtils.isEmpty(mPrefCityCode.getSummary())) {
                int cityCode = nav.getApiCityCode();
                String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_city_code_summary, cityCode);
                mPrefCityCode.setSummary(summaryText);
            }
            if (TextUtils.isEmpty(mPrefDeviceCode.getSummary())) {
                int deviceCode = nav.getApiDeviceCode();
                String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_device_code_summary, deviceCode);
                mPrefDeviceCode.setSummary(summaryText);
            }
            if (TextUtils.isEmpty(mPrefUniCode.getSummary())) {
                int universityCode = nav.getApiSchoolIdPar();
                String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_university_code_summary, universityCode);
                mPrefUniCode.setSummary(summaryText);
            }
        }

        mPrefApiUrl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                showDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strValue = newValue.toString();
                        if (!strValue.endsWith("?")) {
                            strValue += "?";
                        }
                        int ret = NavigationInfoDao.getInstance().updateApiUrl(strValue);
                        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
                        ToastUtilsSimplify.show(resultText);
                        preference.setSummary(strValue);
                    }
                });
                return true;
            }
        });
        mPrefCityCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                showDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strValue = newValue.toString();
                        int ret = NavigationInfoDao.getInstance().updateCityCode(strValue);
                        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
                        ToastUtilsSimplify.show(resultText);
                        String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_city_code_summary, strValue);
                        preference.setSummary(summaryText);
                    }
                });
                return true;
            }
        });
        mPrefDeviceCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                showDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strValue = newValue.toString();
                        int ret = NavigationInfoDao.getInstance().updateDeviceCode(strValue);
                        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
                        ToastUtilsSimplify.show(resultText);
                        String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_device_code_summary, strValue);
                        preference.setSummary(summaryText);
                    }
                });
                return true;
            }
        });
        mPrefUniCode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(final Preference preference, final Object newValue) {
                showDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strValue = newValue.toString();
                        int ret = NavigationInfoDao.getInstance().updateSchoolId(strValue);
                        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
                        ToastUtilsSimplify.show(resultText);
                        String summaryText = RuntimeUtility.generateText(R.string.pref_glb_api_university_code_summary, strValue);
                        preference.setSummary(summaryText);
                    }
                });
                return true;
            }
        });
    }

    private void showDialog(DialogInterface.OnClickListener onClickListener) {
        UniversalUtility.showDialog(
                this,
                R.string.pref_glb_api_update_title,
                R.string.pref_glb_api_message,
                onClickListener,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

}
