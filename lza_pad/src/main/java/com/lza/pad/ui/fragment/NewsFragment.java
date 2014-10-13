package com.lza.pad.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aphidmobile.flip.FlipViewController;
import com.lza.pad.core.db.adapter.NewsAdapter;
import com.lza.pad.core.db.loader.NewsLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.News;
import com.lza.pad.core.request.EbookUrlRequest;
import com.lza.pad.ui.adapter.NewsFlipAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsFragment extends AbstractFragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private FlipViewController mFlipView;
    private NewsFlipAdapter mAdapter;
    private NewsLoader mLoader;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        showProgressDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFlipView = new FlipViewController(getActivity(), FlipViewController.HORIZONTAL);
        return mFlipView;
    }

    @Override
    public void onResume() {
        super.onResume();
        dismissProgressDialog();
        if (mFlipView != null) {
            mFlipView.onResume();
        }
        if (mNavInfo.getRunningMode() == 0) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            loadFromNetwork();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFlipView != null) {
            mFlipView.onPause();
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        mLoader = new NewsLoader(getActivity(), mNavInfo);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        setupViews(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        loader.forceLoad();
    }

    private void loadFromNetwork() {
        EbookUrlRequest request = new EbookUrlRequest<EbookRequest>(mNavInfo,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                },
                new Response.Listener<EbookRequest>() {

                    @Override
                    public void onResponse(EbookRequest ebookRequest) {
                        if (ebookRequest != null) {
                            List<Ebook> ebooks = ebookRequest.getContents();
                            if (ebooks != null) {
                                List<News> news = new ArrayList<News>();
                                for (Ebook ebook : ebooks) {
                                    NewsAdapter adapter = new NewsAdapter();
                                    news.add(adapter.apdater(ebook));
                                }
                                setupViews(news);
                            }
                        }
                    }
                },
                EbookRequest.class);
        request.send();
    }

    private void setupViews(List<News> data) {
        mAdapter = new NewsFlipAdapter(mContext, mNavInfo, data);
        mFlipView.setAdapter(mAdapter);
        dismissProgressDialog();
    }

}
