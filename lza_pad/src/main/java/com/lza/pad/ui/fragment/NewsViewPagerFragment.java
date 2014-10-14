package com.lza.pad.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.db.adapter.NewsAdapter;
import com.lza.pad.core.db.loader.NewsLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.News;
import com.lza.pad.core.request.EbookUrlRequest;
import com.lza.pad.ui.adapter.NewsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsViewPagerFragment extends AbstractFragment
        implements LoaderManager.LoaderCallbacks<List<News>> {

    private ViewPager mViewPager;
    private NewsPagerAdapter mAdapter;
    private NewsLoader mLoader;
    private Context mContext;
    private ArrayList<View> mViews = new ArrayList<View>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        showProgressDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewPager = new ViewPager(getActivity());
        return mViewPager;
    }

    @Override
    public void onResume() {
        super.onResume();
        dismissProgressDialog();
        if (mNavInfo.getRunningMode() == 0) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            loadFromNetwork();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
        Context context = getActivity();
        if (context != null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            for (int i = 0; i < data.size(); i++) {
                View view = inflater.inflate(R.layout.news_item, null);
                mViews.add(view);
            }
            mAdapter = new NewsPagerAdapter(context, mNavInfo, data, mViews);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(0);
        }

        dismissProgressDialog();
    }

}
