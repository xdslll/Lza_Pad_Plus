package com.lza.pad.ui.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.db.adapter.SubjectTypeAdapter;
import com.lza.pad.core.db.dao.SubjectTypeDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.SubjectType;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 各功能模块通用模板,包括:
 * 1. 对TitleBar的控制
 * 2. 获取NavigationInfo对象,并进行参数的初始化
 *
 * @author Sam
 * @Date 14-9-11
 */
public class AbstractFragment extends SherlockFragment implements Consts {

    protected Bundle mArguments;
    //protected TitleBar mTitleBar;
    protected TextView mTxtTitle;
    protected Button mBtnSubject;
    protected NavigationInfo mNavInfo;

    protected ProgressDialog mProgressDialog = null;
    private static final int SHOW_PROGRESS_DIALOG = 0x001;
    private static final int DISMISS_PROGRESS_DIALOG = 0x002;
    private static final int SHOW_PROGRESS_DIALOG_ANYWAY = 0x003;
    private static final int DISMISS_PROGRESS_DIALOG_ANYWAY = 0x004;

    protected List<SubjectType> mSubjectTypes = new ArrayList<SubjectType>();
    protected List<String> mSubjectName = new ArrayList<String>();

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS_DIALOG :
                    _showProgressDialog();
                    break;
                case DISMISS_PROGRESS_DIALOG:
                    _dismissProgressDialog();
                    break;
                case SHOW_PROGRESS_DIALOG_ANYWAY:
                    _showProgressDialogAnyway();
                    break;
                case DISMISS_PROGRESS_DIALOG_ANYWAY:
                    _dismissProgressDialogAnyway();
                    break;
                default:
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.d("onCreate");

        mArguments = getArguments();
        if (mArguments != null) {
            mNavInfo = mArguments.getParcelable(KEY_NAVIGATION_INFO);
        }

        if (mNavInfo != null && mNavInfo.getHasTitleButton() == 1) {
            loadSubject();
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null && !isDetached()) {
            mTxtTitle = (TextView) view.findViewById(R.id.title_bar_title);
            mBtnSubject = (Button) view.findViewById(R.id.title_bar_subject);
            if (mTxtTitle != null && mBtnSubject != null) {
                mTxtTitle.setText(mNavInfo.getName());
                int hasTitleButton = mNavInfo.getHasTitleButton();
                if (hasTitleButton == 1) {
                    mBtnSubject.setVisibility(View.VISIBLE);
                    mBtnSubject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mSubjectName != null && mSubjectName.size() > 0) {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("请选择学科")
                                        .setItems(mSubjectName.toArray(new String[0]), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //ToastUtils.showShort(getActivity(), mSubjectTypes.get(which).getValue());
                                                if (which == 0) {
                                                    onSelectSubject(null);
                                                } else {
                                                    String subValue = mSubjectTypes.get(which).getValue();
                                                    onSelectSubject(subValue);
                                                }
                                            }
                                        })
                                        .show();
                            }
                        }
                    });
                } else {
                    mBtnSubject.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void onSelectSubject(String subValue) {
        if (TextUtils.isEmpty(subValue)) {
            mNavInfo.setApiXkPar("");
        } else {
            mNavInfo.setApiXkPar(subValue);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        AppLogger.d("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        /*mTitleBar = (TitleBar) getActivity().findViewById(R.id.ebook_list_title_bar);
        if (mTitleBar != null && mNavInfo != null) {
            mTitleBar.handleTitleBar(mNavInfo);
        }*/
    }

    @Override
    public void onStart() {
        AppLogger.d("onStart");
        super.onStart();
    }

    private void _showProgressDialog() {
        if (mNavInfo != null && mNavInfo.getRunningMode() == 1) {
            if (getActivity() != null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage(getResources().getString(R.string.ebook_content_loading));
                mProgressDialog.show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                        }
                    }
                }, DISMISS_PROGRESS_DIALOG_DELAY);
            }
        }
    }

    private void _dismissProgressDialog() {
        if (mNavInfo.getRunningMode() == 1 && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void _showProgressDialogAnyway() {
        if (getActivity() != null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getResources().getString(R.string.ebook_content_loading));
            mProgressDialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                }
            }, DISMISS_PROGRESS_DIALOG_DELAY);
        }
    }

    private void _dismissProgressDialogAnyway() {
        if(mProgressDialog != null && getActivity() != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showProgressDialog() {
        mHandler.sendEmptyMessage(SHOW_PROGRESS_DIALOG);
    }

    protected void dismissProgressDialog() {
        mHandler.sendEmptyMessage(DISMISS_PROGRESS_DIALOG);
    }

    protected void showProgressDialogAnyway() {
        mHandler.sendEmptyMessage(SHOW_PROGRESS_DIALOG_ANYWAY);
    }

    protected void dismissProgressDialogAnyway() {
        mHandler.sendEmptyMessage(DISMISS_PROGRESS_DIALOG_ANYWAY);
    }

    private void loadSubject() {
        //先从数据库中读取，如果读取不到再从网络中获取
        List<SubjectType> data = SubjectTypeDao.getInstance().queryForNotClosed();
        if (data != null && data.size() > 0) {
            mSubjectTypes.add(new SubjectType());
            mSubjectTypes.addAll(data);

            int index = 0;
            for (SubjectType sub : mSubjectTypes) {
                if (index == 0) {
                    mSubjectName.add("全部");
                } else {
                    mSubjectName.add(sub.getTitle());
                }
                index++;
            }
            return;
        }

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
                                mSubjectTypes.add(new SubjectType());
                                mSubjectName.add("全部");
                                for (Ebook data : contents) {
                                    mSubjectTypes.add(adapter.apdater(data));
                                    mSubjectName.add(data.getTitle());
                                }
                            }
                        }
                    }
                });
        if (request != null && getActivity() != null) {
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
        }
    }


}
