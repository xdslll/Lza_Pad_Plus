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
import android.widget.SimpleAdapter;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.loader.GlobalPreferenceLoader;
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
        getLoaderManager().initLoader(0, null, this);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Map<String, String> map = mData.get(position);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.pref_global_dialog, null);
                final EditText dialogEdt = (EditText) dialogView.findViewById(R.id.pref_global_dialog_edt);
                dialogEdt.setText(map.get("value"));
                new AlertDialog.Builder(getActivity())
                        .setTitle(map.get("key"))
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });
    }

    private void updateData(Map<String, String> map) {
        String colName = map.get("col");
        String newValue = map.get("value");
        int ret = NavigationInfoDao.getInstance().updateExplicitCol(colName, newValue);
        showSuccessToast(ret);

        /*if (colName.equals(NavigationInfo._API_URL)) {
            int ret = NavigationInfoDao.getInstance().updateApiUrl(newValue);
            showSuccessToast(ret);
        } else if (colName.equals(NavigationInfo._API_CITY_CODE)) {
            int ret = NavigationInfoDao.getInstance().updateCityCode(newValue);
            showSuccessToast(ret);
        } else if (colName.equals(NavigationInfo._API_DEVICE_CODE)) {
            int ret = NavigationInfoDao.getInstance().updateDeviceCode(newValue);
            showSuccessToast(ret);
        } else if (colName.equals(NavigationInfo._API_SCHOOL_ID)) {
            int ret = NavigationInfoDao.getInstance().updateSchoolId(newValue);
            showSuccessToast(ret);
        }*/
    }

    private void showSuccessToast(int ret) {
        String resultText = RuntimeUtility.generateText(R.string.pref_glb_api_update_success, ret);
        ToastUtilsSimplify.show(resultText);
    }

    @Override
    public Loader<List<Map<String, String>>> onCreateLoader(int id, Bundle args) {
        return new GlobalPreferenceLoader(getActivity());
    }

    private List<Map<String, String>> mData;
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
