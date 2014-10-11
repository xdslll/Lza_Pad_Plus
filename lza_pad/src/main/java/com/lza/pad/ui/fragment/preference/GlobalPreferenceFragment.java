package com.lza.pad.ui.fragment.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.loader.GlobalPreferenceLoader;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.ui.fragment.AbstractListFragment;

import java.util.List;
import java.util.Map;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/8/14.
 */
public class GlobalPreferenceFragment extends AbstractListFragment
        implements LoaderManager.LoaderCallbacks<List<Map<String, String>>>{

    private SimpleAdapter mAdapter;
    private List<Map<String, String>> mData;
    private final int LOADER_ID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pref_global, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(LOADER_ID, null, this);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Map<String, String> map = mData.get(position);
                if (map.get("col").equals(NavigationInfo._API_IMG_SCALING)) {
                    showSeekBarDialog(position, MODE_IMG_SCALING);
                } else if (map.get("col").equals(NavigationInfo._API_VERTICAL_OFFSET)) {
                    showSeekBarDialog(position, MODE_VERTICAL_OFFSET);
                } else if (map.get("col").equals(NavigationInfo._API_RUNNING_MODE)) {
                    showListDialog(position);
                } else {
                    showEditTextDialog(position);
                }
            }
        });
    }

    private void showListDialog(final int position) {
        final Map<String, String> map = mData.get(position);
        new AlertDialog.Builder(getActivity())
                .setTitle(map.get("key"))
                .setItems(new String[]{"数据库模式", "网络模式"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                map.put("value", String.valueOf(which));
                                updateData(map);
                                mData.set(position, map);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                .show();
    }

    private final int MODE_IMG_SCALING = 0;
    private final int MODE_VERTICAL_OFFSET = 1;

    private void showSeekBarDialog(final int position, final int mode) {
        final Map<String, String> map = mData.get(position);
        View view = getActivity().getLayoutInflater().inflate(R.layout.pref_module_more_img_scaling, null);
        final SeekBar sebProgress = (SeekBar) view.findViewById(R.id.pref_module_more_img_scaling_seb);
        final TextView txtProgress = (TextView) view.findViewById(R.id.pref_module_more_img_scaling_progress);
        if (mode == MODE_IMG_SCALING) {
            float imgScaling = Float.valueOf(map.get("value"));
            int imgScalingInHundred = (int) (imgScaling * 100);
            txtProgress.setText(imgScalingInHundred + "%");
            sebProgress.setProgress(imgScalingInHundred);
            sebProgress.setMax(100);
        } else if (mode == MODE_VERTICAL_OFFSET) {
            int verticalOffset = Integer.valueOf(map.get("value"));
            txtProgress.setText(String.valueOf(verticalOffset));
            sebProgress.setProgress(verticalOffset);
            sebProgress.setMax(200);
        }
        sebProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mode == 0) {
                    txtProgress.setText(progress + "%");
                } else if (mode == 1) {
                    txtProgress.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new AlertDialog.Builder(getActivity())
                .setTitle(map.get("key"))
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int progress = sebProgress.getProgress();
                        if (mode == 0) {
                            float imgScalingChanged = (float) progress / 100;
                            if (imgScalingChanged <= 0) {
                                ToastUtilsSimplify.show("更新失败！所选值必须大于0！");
                            } else {
                                map.put("value", String.valueOf(imgScalingChanged));

                            }
                        } else if (mode == 1) {
                            map.put("value", String.valueOf(progress));
                        }
                        updateData(map);
                        mData.set(position, map);
                        mAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showEditTextDialog(final int position) {
        final Map<String, String> map = mData.get(position);
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.pref_global_dialog, null);
        final EditText dialogEdt = (EditText) dialogView.findViewById(R.id.pref_global_dialog_edt);
        dialogEdt.setText(map.get("value"));
        new AlertDialog.Builder(getActivity())
                .setTitle(map.get("key"))
                .setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String originalValue = map.get(position);
                        String newValue = dialogEdt.getText().toString();
                        if (!newValue.equals(originalValue)) {
                            map.put("value", newValue);
                            updateData(map);
                            mData.set(position, map);
                            mAdapter.notifyDataSetChanged();
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
    }

    private void updateData(Map<String, String> map) {
        String colName = map.get("col");
        String newValue = map.get("value");
        int ret = NavigationInfoDao.getInstance().updateExplicitCol(colName, newValue);
        showSuccessToast(ret);
    }

    private void showSuccessToast(int ret) {
        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
        ToastUtilsSimplify.show(resultText);
    }

    @Override
    public Loader<List<Map<String, String>>> onCreateLoader(int id, Bundle args) {
        return new GlobalPreferenceLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Map<String, String>>> loader, final List<Map<String, String>> data) {
        if (data != null) {
            mData = data;
            mAdapter = new SimpleAdapter(getActivity(), mData, android.R.layout.simple_list_item_2,
                    new String[]{"key", "value"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            setListAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Map<String, String>>> loader) {

    }
}
