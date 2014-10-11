package com.lza.pad.ui.fragment.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.EbookContentDao;
import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.EbookRequestDao;
import com.lza.pad.core.db.dao.HotBookContentDao;
import com.lza.pad.core.db.dao.HotBookDao;
import com.lza.pad.core.db.dao.JournalsContentDao;
import com.lza.pad.core.db.dao.JournalsDao;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.loader.NavigationLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.HotBook;
import com.lza.pad.core.db.model.HotBookContent;
import com.lza.pad.core.db.model.Journals;
import com.lza.pad.core.db.model.JournalsContent;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.lib.support.utils.UniversalUtility;
import com.lza.pad.ui.adapter.ModulePreferenceListAdapter;
import com.lza.pad.ui.fragment.AbstractListFragment;
import com.lza.pad.ui.fragment.preference.old.MainPreferenceActivity;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
public class ModulePreferenceFragment extends AbstractListFragment
        implements LoaderManager.LoaderCallbacks<List<NavigationInfo>>,
        MainPreferenceActivity.OnBackClickListener {

    public static final int LOADER_ID = 0;
    private ModulePreferenceListAdapter mAdapter;

    private Button mBtnClear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pref_module_layout, container, false);
        mBtnClear = (Button) view.findViewById(R.id.pref_mod_clear);
        initClearButton();
        return view;
    }

    @Override
    public Loader<List<NavigationInfo>> onCreateLoader(int id, Bundle args) {
        return new NavigationLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<NavigationInfo>> loader, List<NavigationInfo> data) {
        mAdapter = new ModulePreferenceListAdapter(getActivity(), data);
        setListAdapter(mAdapter);
        //dismissProgressDialog();
    }

    @Override
    public void onLoaderReset(Loader<List<NavigationInfo>> loader) {
        loader.forceLoad();
    }

    @Override
    public void onBack(Object tag) {
        //ToastUtilsSimplify.show("Oh, it's work!");
    }

    private void initClearButton() {
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtilsSimplify.test();
                final AlertDialog confirmDialog = UniversalUtility.createDialog(
                        getActivity(), R.string.pref_mod_clear_title, R.string.pref_mod_confirm_seconde,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearAllNavigationData();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                UniversalUtility.showDialog(
                        getActivity(), R.string.pref_mod_clear_title, R.string.pref_mod_confirm,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmDialog.show();
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        });
    }

    public void clearAllNavigationData() {
        //清空所有导航数据
        NavigationInfoDao.getInstance().clear();
        //重新初始化
        NavigationInfoDao.getInstance().initNavigationData(getActivity());

        //清空所有电子书数据
        EbookRequestDao.getInstance().clearByRaw(EbookRequest.TABLE_NAME);
        EbookDao.getInstance().clearByRaw(Ebook.TABLE_NAME);
        EbookContentDao.getInstance().clearByRaw(EbookContent.TABLE_NAME);
        JournalsDao.getInstance().clearByRaw(Journals.TABLE_NAME);
        JournalsContentDao.getInstance().clearByRaw(JournalsContent.TABLE_NAME);
        HotBookDao.getInstance().clearByRaw(HotBook.TABLE_NAME);
        HotBookContentDao.getInstance().clearByRaw(HotBookContent.TABLE_NAME);

        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
