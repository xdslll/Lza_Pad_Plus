package com.lza.pad.ui.fragment.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.utils.ToastUtilsSimplify;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
public class ModuleMoreSettings extends AbstractPreferenceActivity {

    private static final String PREF_ROW_NUMBER = "pref_mod_shelves_row_number";
    private static final String PREF_COL_NUMBER = "pref_mod_shelves_col_number";
    private static final String PREF_PREVIEW = "pref_mod_shelves_preview";
    private static final String PREF_API = "pref_mod_more_api";
    private static final String PREF_IMG_SCALING = "pref_mod_shelves_img_scaling";
    private static final String PREF_RUNNING_MODE = "pref_mod_shelves_running_mode";

    private EditTextPreference mRowPref;
    private EditTextPreference mColPref;
    private PreferenceScreen mApiPref, mImgScalingPref;
    private ListPreference mRunningModePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_module_more);
        //Bundle arg = getArguments();
    }

    @Override
    public void onResume() {
        super.onResume();

        mRowPref = (EditTextPreference) getPreferenceScreen().findPreference(PREF_ROW_NUMBER);
        mColPref = (EditTextPreference) getPreferenceScreen().findPreference(PREF_COL_NUMBER);
        mApiPref = (PreferenceScreen) getPreferenceScreen().findPreference(PREF_API);
        mImgScalingPref = (PreferenceScreen) getPreferenceScreen().findPreference(PREF_IMG_SCALING);
        mRunningModePref = (ListPreference) getPreferenceScreen().findPreference(PREF_RUNNING_MODE);

        if (mNav != null) {
            //更新到最新数据
            mNav = NavigationInfoDao.getInstance().queryForId(mNav.getId());
            int rowNumber = mNav.getDataRowNumber();
            int colNumber = mNav.getDataColumnNumber();
            float imgScaling = mNav.getImgScaling();
            int imgScalingInHundred = (int) (imgScaling * 100);
            int runningMode = mNav.getRunningMode();
            updateRowTitle(rowNumber);
            updateColTitle(colNumber);
            updateImgScalingTitle(imgScalingInHundred);
            updateRunningModeTitle(runningMode);
        }

        mRowPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
                Integer finalValue = Integer.valueOf(value);
                if (finalValue > 10) {
                    ToastUtilsSimplify.show("更新失败！数值不能大于10！");
                    return false;
                } else {
                    mNav.setDataRowNumber(finalValue);

                    int colNumber = mNav.getDataColumnNumber();
                    int pageSize = finalValue * colNumber;
                    mNav.setApiPageSizePar(pageSize);

                    NavigationInfoDao.getInstance().updateData(mNav);

                    updateRowTitle(finalValue);
                    return true;
                }
            }
        });

        mColPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
                Integer finalValue = Integer.valueOf(value);
                if (finalValue > 10) {
                    ToastUtilsSimplify.show("更新失败！数值不能大于10！");
                    return false;
                } else {
                    mNav.setDataColumnNumber(finalValue);

                    int rowNumber = mNav.getDataRowNumber();
                    int pageSize = finalValue * rowNumber;
                    mNav.setApiPageSizePar(pageSize);

                    NavigationInfoDao.getInstance().updateData(mNav);

                    updateColTitle(finalValue);
                    return true;
                }
            }
        });

        mApiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                /*ModuleApiSettings apiSettings = new ModuleApiSettings();
                apiSettings.setArguments(getArguments());
                getFragmentManager().beginTransaction()
                        .replace(R.id.pref_container, apiSettings)
                        .commit();*/
                Intent intent = new Intent(ModuleMoreSettings.this, ModuleApiSettings.class);
                intent.putExtra(KEY_NAVIGATION_INFO, mNav);
                startActivity(intent);
                return true;
            }
        });

        mImgScalingPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                View view = getLayoutInflater().inflate(R.layout.pref_module_more_img_scaling, null);
                final SeekBar sebProgress = (SeekBar) view.findViewById(R.id.pref_module_more_img_scaling_seb);
                final TextView txtProgress = (TextView) view.findViewById(R.id.pref_module_more_img_scaling_progress);
                float imgScaling = mNav.getImgScaling();
                int imgScalingInHundred = (int) (imgScaling * 100);
                txtProgress.setText(imgScalingInHundred + "%");
                sebProgress.setProgress(imgScalingInHundred);
                sebProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtProgress.setText(progress + "%");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                new AlertDialog.Builder(ModuleMoreSettings.this)
                        .setTitle(R.string.pref_glb_api_update_title)
                        .setView(view)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int progress = sebProgress.getProgress();
                                float imgScalingChanged = (float) progress / 100;
                                if (imgScalingChanged <= 0) {
                                    ToastUtilsSimplify.show("更新失败！封面图片比例必须大于0！");
                                } else {
                                    mNav.setImgScaling(imgScalingChanged);
                                    NavigationInfoDao.getInstance().updateData(mNav);
                                    updateImgScalingTitle(progress);
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });

        mRunningModePref.setDefaultValue(mNav.getRunningMode());
        mRunningModePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = newValue.toString();
                Integer finalValue = Integer.valueOf(value);
                mNav.setRunningMode(finalValue);
                NavigationInfoDao.getInstance().updateData(mNav);
                updateRunningModeTitle(finalValue);
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

    private void updateImgScalingTitle(int imgScaling) {
        String imgScalingTitle = String.format(getString
                (R.string.pref_mod_more_img_scaling_title), imgScaling);
        mImgScalingPref.setTitle(imgScalingTitle);
    }

    private void updateRunningModeTitle(int runningMode) {
        String title = "";
        if (runningMode == 0) {
            title = "数据库模式";
        } else if (runningMode == 1) {
            title = "网络模式";
        }
        String runnngModeTitle = String.format(getString
                (R.string.pref_mod_more_running_mode_title), title);
        mRunningModePref.setTitle(runnngModeTitle);
    }
}
