package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.task.AbstractStringRequestTask;
import com.lza.pad.ui.adapter.EbookSearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/15/14.
 */
public class BookSearchListFragment extends AbstractListFragment
        implements AbstractStringRequestTask.OnStringRequestResponse {

    private ImageButton mImgBack, mImgPrev, mImgNext;
    private TextView mTxtPageInfo;
    private EbookSearchAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_search_list, container, false);

        mTxtPageInfo = (TextView) view.findViewById(R.id.ebook_search_list_page_info);
        mImgPrev = (ImageButton) view.findViewById(R.id.ebook_search_list_prev_pages);
        mImgNext = (ImageButton) view.findViewById(R.id.ebook_search_list_next_pages);
        mImgBack = (ImageButton) view.findViewById(R.id.ebook_search_list_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(BookSearchListFragment.this).commit();
            }
        });
        handlePageButtonListener();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mNavInfo.setApiPagePar(1);//第一次查询时从第一页开始查询
        sendRequest();
    }

    private void sendRequest() {
        AbstractStringRequestTask requestTask = new AbstractStringRequestTask(mNavInfo);
        requestTask.registerListener(this);
        requestTask.sendRequest();
        showProgressDialogAnyway();
    }

    private void handleListViewListener() {
        if (getListView() != null) {
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Ebook ebook = mAdapter.getItem(position);
                    EbookContentFragment fragment = new EbookContentFragment();
                    Bundle bundle = new Bundle();
                    NavigationInfo nav = mNavInfo.clone();
                    nav.setRunningMode(NavigationInfo.RUNNING_MODE_NET);//开启网络模式
                    bundle.putParcelable(KEY_NAVIGATION_INFO, nav);
                    bundle.putParcelable(KEY_EBOOK_INFO, ebook);
                    fragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment oldFrt = fm.findFragmentByTag(EbookContentFragment.TAG_EBOOK_CONTENT);
                    if (oldFrt != null) {
                        ft.remove(oldFrt);
                    }
                    ft.add(R.id.home_container, fragment, EbookContentFragment.TAG_EBOOK_CONTENT).commit();
                }
            });
        }
    }
    private void handlePageButtonListener() {
        mImgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPage > 1) {
                    mCurrentPage--;
                    mNavInfo.setApiPagePar(mCurrentPage);
                    sendRequest();
                }
            }
        });
        mImgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPage < mTotalPage && mCurrentPage > 0) {
                    mCurrentPage++;
                    mNavInfo.setApiPagePar(mCurrentPage);
                    sendRequest();
                }
            }
        });
    }

    private void handlePageButtonVisibility() {
        if (mCurrentPage <= 1) {
            mImgPrev.setVisibility(View.GONE);
        } else {
            mImgPrev.setVisibility(View.VISIBLE);
        }
        if (mTotalPage == mCurrentPage) {
            mImgNext.setVisibility(View.GONE);
        } else if (mCurrentPage < mTotalPage) {
            mImgNext.setVisibility(View.VISIBLE);
        } else {
            mImgNext.setVisibility(View.GONE);
        }
    }

    private int mCurrentPage, mTotalPage;
    @Override
    public void onSuccess(String data) {
        EbookRequest request = parseResponse(data);
        if (request != null) {
            List<Ebook> contents = request.getContents();
            if (contents != null && getActivity() != null) {
                mCurrentPage = mNavInfo.getApiPagePar();
                mTotalPage = request.getYe();
                mTxtPageInfo.setText("第" + mCurrentPage + "页，共" + mTotalPage + "页");
                handlePageButtonVisibility();
                mAdapter = new EbookSearchAdapter(getActivity(), contents);
                setListAdapter(mAdapter);
                handleListViewListener();
            }
        }
        dismissProgressDialogAnyway();
    }

    @Override
    public void onError() {
        dismissProgressDialogAnyway();
    }

    private static final String pagesizeLabel = "pagesize";
    private static final String statusLabel = "status";
    private static final String returnnumLabel = "returnnum";
    private static final String contentsLabel = "contents";
    private static final String pagenumLabel = "pagenum";
    private static final String yeLabel = "ye";
    private static final String titleLabel = "title";
    private static final String marc_noLabel = "marc_no";
    private static final String authorLabel = "author";
    private static final String typeLabel = "type";
    private static final String haoLabel = "hao";
    private static final String fb1Label = "fb1";
    private static final String fb2Label = "fb2";
    private static final String publisherLabel = "publisher";
    private static final String xhLabel = "xh";

    private EbookRequest parseResponse (String json) {
        EbookRequest searchBookRequest = new EbookRequest();
        try {
            if (json != null) {
                JSONObject jsonOB = new JSONObject(json);
                if (jsonOB.has(pagesizeLabel))
                    searchBookRequest.setPagesize(Integer.valueOf(jsonOB.getString(pagesizeLabel)));

                if (jsonOB.has(statusLabel))
                    searchBookRequest.setState(jsonOB.getString(statusLabel));

                if (jsonOB.has(returnnumLabel))
                    searchBookRequest.setReturnnum(Integer.valueOf(jsonOB.getString(returnnumLabel)));

                if (jsonOB.has(contentsLabel)) {
                    ArrayList<Ebook> searchContentList = new ArrayList<Ebook>();

                    JSONArray contentJSON = jsonOB.getJSONArray(contentsLabel);
                    for (int i = 0, iLen = contentJSON.length(); i < iLen; i++) {
                        Ebook content = new Ebook();
                        JSONObject contentOB = contentJSON.getJSONObject(i);
                        if (contentOB.has(titleLabel))
                            content.setTitle(contentOB.getString(titleLabel));
                        if (contentOB.has(marc_noLabel))
                            content.setMarc_no(contentOB
                                    .getString(marc_noLabel));
                        if (contentOB.has(authorLabel))
                            content.setAuthor(contentOB.getString(authorLabel));
                        if (contentOB.has(typeLabel))
                            content.setType(contentOB.getString(typeLabel));
                        if (contentOB.has(haoLabel))
                            content.setHao(contentOB.getString(haoLabel));
                        if (contentOB.has(fb1Label))
                            content.setFb1(contentOB.getString(fb1Label));
                        if (contentOB.has(fb2Label))
                            content.setFb2(contentOB.getString(fb2Label));
                        if (contentOB.has(publisherLabel))
                            content.setPublisher(contentOB
                                    .getString(publisherLabel));
                        if (contentOB.has(xhLabel))
                            content.setXh(Integer.valueOf(contentOB.getString(xhLabel)));

                        searchContentList.add(content);
                    }

                    searchBookRequest.setContents(searchContentList);

                    if (jsonOB.has(pagenumLabel))
                        searchBookRequest.setPagenum(Integer.valueOf(jsonOB.getString(pagenumLabel)));

                    if (jsonOB.has(yeLabel))
                        searchBookRequest.setYe(Integer.valueOf(jsonOB.getString(yeLabel)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return searchBookRequest;
    }
}
