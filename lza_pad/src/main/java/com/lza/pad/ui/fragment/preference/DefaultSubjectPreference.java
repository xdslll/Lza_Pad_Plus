package com.lza.pad.ui.fragment.preference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.dao.SubjectTypeDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.SubjectType;
import com.lza.pad.ui.activity.base.AbstractActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 11/10/14.
 */
public class DefaultSubjectPreference extends AbstractActivity {

    private ActionBar mActionBar;
    private Button mBtnGet;
    private ListView mList;
    private BaseAdapter mAdapter;
    protected List<NavigationInfo> mNavInfos = new ArrayList<NavigationInfo>();
    protected List<SubjectType> mSubjectTypes = new ArrayList<SubjectType>();
    protected List<String> mSubjectName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("设置默认学科");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(false);

        setContentView(R.layout.pref_subject);
        mBtnGet = (Button) findViewById(R.id.pref_subject_get);
        mList = (ListView) findViewById(R.id.pref_subject_list);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNavInfo();
            }
        });
        mBtnGet.setText("获取模块名称");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        mAdapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return mNavInfos.size();
            }

            @Override
            public NavigationInfo getItem(int position) {
                return mNavInfos.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final NavigationInfo data = getItem(position);
                LayoutInflater inflater = LayoutInflater.from(DefaultSubjectPreference.this);
                convertView = inflater.inflate(R.layout.pref_subject_item, null);
                TextView name = (TextView) convertView.findViewById(R.id.pref_subject_item_name);
                TextView value = (TextView) convertView.findViewById(R.id.pref_subject_item_value);
                CheckBox toggle = (CheckBox) convertView.findViewById(R.id.pref_subject_item_switch);
                ImageView imgUp = (ImageView) convertView.findViewById(R.id.pref_subject_item_up);
                ImageView imgDown = (ImageView) convertView.findViewById(R.id.pref_subject_item_down);

                name.setText(data.getName());
                value.setText("默认学科：" + (TextUtils.isEmpty(data.getApiXkPar()) ? "全部" : data.getApiXkPar()));

                toggle.setVisibility(View.GONE);
                imgUp.setVisibility(View.GONE);
                imgDown.setVisibility(View.GONE);

                return convertView;
            }
        };
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final NavigationInfo data = mNavInfos.get(position);
                new AlertDialog.Builder(DefaultSubjectPreference.this)
                        .setTitle("请选择")
                        .setItems(mSubjectName.toArray(new String[0]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = "";
                                if (which < mSubjectTypes.size() - 1) {
                                    value = mSubjectTypes.get(which).getValue();
                                }
                                data.setApiXkPar(value);
                                NavigationInfoDao.getInstance().updateData(data);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
            }
        });
    }

    private void loadNavInfo() {
        mNavInfos = NavigationInfoDao.getInstance().queryNotClosedAndActivated();
        initAdapter();
        loadSubject();
    }

    private void loadSubject() {
        mSubjectTypes = SubjectTypeDao.getInstance().queryForNotClosed();
        for (SubjectType sub : mSubjectTypes) {
            mSubjectName.add(sub.getTitle());
        }
        mSubjectTypes.add(new SubjectType());
        mSubjectName.add("全部");
    }

}
