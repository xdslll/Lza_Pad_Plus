package com.lza.pad.ui.fragment.preference;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.db.adapter.SubjectTypeAdapter;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.dao.SubjectTypeDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.SubjectType;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.ui.activity.base.AbstractActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 11/10/14.
 */
public class SubjectPreference extends AbstractActivity {

    private ActionBar mActionBar;
    private Button mBtnGet;
    private ListView mList;
    private BaseAdapter mAdapter;
    protected List<SubjectType> mSubjectTypes = new ArrayList<SubjectType>();
    protected List<String> mSubjectName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("设置学科");
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(false);

        setContentView(R.layout.pref_subject);
        mBtnGet = (Button) findViewById(R.id.pref_subject_get);
        mList = (ListView) findViewById(R.id.pref_subject_list);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSubject();
            }
        });
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
                return mSubjectTypes.size();
            }

            @Override
            public SubjectType getItem(int position) {
                return mSubjectTypes.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final SubjectType data = getItem(position);
                LayoutInflater inflater = LayoutInflater.from(SubjectPreference.this);
                convertView = inflater.inflate(R.layout.pref_subject_item, null);
                TextView name = (TextView) convertView.findViewById(R.id.pref_subject_item_name);
                TextView value = (TextView) convertView.findViewById(R.id.pref_subject_item_value);
                CheckBox toggle = (CheckBox) convertView.findViewById(R.id.pref_subject_item_switch);
                ImageView imgUp = (ImageView) convertView.findViewById(R.id.pref_subject_item_up);
                ImageView imgDown = (ImageView) convertView.findViewById(R.id.pref_subject_item_down);

                name.setText(data.getTitle());
                value.setText(data.getValue() + "," + data.getXk_type() + "," + data.getPx());
                if (data.getIfShow() == 0) {
                    toggle.setChecked(false);
                } else {
                    toggle.setChecked(true);
                }

                toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            data.setIfShow(1);
                        } else {
                            data.setIfShow(0);
                        }
                        SubjectTypeDao.getInstance().updateData(data);
                    }
                });

                imgUp.setVisibility(View.GONE);
                imgDown.setVisibility(View.GONE);

                return convertView;
            }
        };
        mList.setAdapter(mAdapter);
    }

    private NavigationInfo mNavInfo;

    private void loadSubject() {
        mSubjectTypes = SubjectTypeDao.getInstance().queryForAllOrderById();
        if (mSubjectTypes != null && mSubjectTypes.size() > 0) {
            initAdapter();
            return;
        }
        mNavInfo = NavigationInfoDao.getInstance().queryNotClosedBySortId(1);
        String url = mNavInfo.getApiUrl();
        Map<String, String> map = new HashMap<String, String>();
        map.put("control", mNavInfo.getApiControlPar());
        map.put("action", "getXkMessage");
        map.put("schoolId", String.valueOf(mNavInfo.getApiSchoolIdPar()));
        String subjectUrl = ApiUrlFactory.createApiUrl(url, map);
        GsonRequest<EbookRequest> request = new GsonRequest<EbookRequest>(
                com.android.volley.Request.Method.GET,
                subjectUrl,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },
                EbookRequest.class, null,
                new Response.Listener<EbookRequest>() {
                    @Override
                    public void onResponse(EbookRequest ebookRequest) {
                        if (ebookRequest != null) {
                            List<Ebook> contents = ebookRequest.getContents();
                            if (contents != null && contents.size() > 0) {
                                for (Ebook content : contents) {
                                    content.setName(mNavInfo.getApiControlPar());
                                }
                                SubjectTypeDao.getInstance().createOrUpdateNews(contents);
                                SubjectTypeAdapter adapter = new SubjectTypeAdapter();
                                for (Ebook data : contents) {
                                    mSubjectTypes.add(adapter.apdater(data));
                                    mSubjectName.add(data.getTitle());
                                }
                            }
                            initAdapter();
                        }
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
