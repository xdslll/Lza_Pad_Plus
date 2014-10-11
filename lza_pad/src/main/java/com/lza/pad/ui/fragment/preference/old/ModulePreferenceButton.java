package com.lza.pad.ui.fragment.preference.old;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.Preference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.EbookContentDao;
import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.EbookRequestDao;
import com.lza.pad.core.db.dao.JournalsContentDao;
import com.lza.pad.core.db.dao.JournalsDao;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.Journals;
import com.lza.pad.core.db.model.JournalsContent;
import com.lza.pad.lib.support.utils.UniversalUtility;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
@Deprecated
public class ModulePreferenceButton extends Preference {

    private Context mContext;
    private Intent mBroadIntent;

    public ModulePreferenceButton(Context context) {
        super(context);
        mContext = context;
        //mBroadIntent = new Intent(ModulePreference.ACTION_FORCE_LOAD_BROAD);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        Button btnClear = new Button(mContext);
        btnClear.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        btnClear.setText("恢复出厂设置");
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog confirmDialog = UniversalUtility.createDialog(
                        mContext, R.string.pref_mod_clear_title, R.string.pref_mod_confirm_seconde,
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
                        mContext, R.string.pref_mod_clear_title, R.string.pref_mod_confirm,
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

        layout.addView(btnClear);
        return layout;
    }

    public void clearAllNavigationData() {
        //清空所有导航数据
        NavigationInfoDao.getInstance().clear();
        //重新初始化
        NavigationInfoDao.getInstance().initNavigationData(mContext);

        //清空所有电子书数据
        EbookRequestDao.getInstance().clearByRaw(EbookRequest.TABLE_NAME);
        EbookDao.getInstance().clearByRaw(Ebook.TABLE_NAME);
        EbookContentDao.getInstance().clearByRaw(EbookContent.TABLE_NAME);
        JournalsDao.getInstance().clearByRaw(Journals.TABLE_NAME);
        JournalsContentDao.getInstance().clearByRaw(JournalsContent.TABLE_NAME);

        //更新界面
        notifyParentUpdate();
    }

    private void notifyParentUpdate() {
        mContext.sendBroadcast(mBroadIntent);
    }
}
