package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aphidmobile.flip.FlipViewController;
import com.lza.pad.core.db.loader.NewsLoader;
import com.lza.pad.core.db.model.News;
import com.lza.pad.ui.adapter.NewsFlipAdapter;

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
        getLoaderManager().initLoader(0, null, this);
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
        mAdapter = new NewsFlipAdapter(getActivity(), mNavInfo, data);
        mFlipView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        loader.forceLoad();
    }





}
