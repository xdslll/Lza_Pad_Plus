package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lza.pad.R;
import com.lza.pad.core.db.loader.EbookLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.ui.adapter.EbookAdapter;
import com.lza.pad.ui.drawable.FastBitmapDrawable;
import com.lza.pad.ui.widget.ShelvesView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现书架功能
 *
 * @author xiads
 * @Date 14-9-14.
 */
public class EbookShelvesFragment extends AbstractFragment
        implements LoaderManager.LoaderCallbacks<List<Ebook>>{

    //private ViewSwitcher mViewSwitcher;
    private ShelvesView mGrid;
    private FastBitmapDrawable mDefaultCover;
    private int mCurrentPage = 0;
    private int mTotalPage = 0;
    private Button mBtnPrev, mBtnNext;
    private final int LOADER_ID = 0;
    private EbookAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_shelves, container, false);
        mGrid = (ShelvesView) view.findViewById(R.id.ebook_list_grid_shelves);
        mBtnPrev = (Button) view.findViewById(R.id.ebook_list_page_prev);
        mBtnNext = (Button) view.findViewById(R.id.ebook_list_page_next);

        mBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPage = mNavInfo.getApiPagePar() + 1;
                mNavInfo.setApiPagePar(nextPage);
                getLoaderManager().restartLoader(LOADER_ID, null, EbookShelvesFragment.this);
            }
        });

        /*mViewSwitcher = (ViewSwitcher) view.findViewById(R.id.ebook_list_switcher);
        mViewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return getActivity().getLayoutInflater().inflate(R.layout.ebook_shelves_adapter, null);
            }
        });*/
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGrid = null;
        mDefaultCover = null;
    }

    private FastBitmapDrawable getDefaultCover() {
        return mDefaultCover;
    }

    private List<Ebook> mData = null;

    private void setupViews(List<Ebook> data) {

        //mGrid = (ShelvesView) mViewSwitcher.getNextView();
        mGrid.setNumColumns(4);
        int width = mGrid.getWidth();
        int height = mGrid.getHeight();

        if (mAdapter == null) {
            mData = data;
            mAdapter = new EbookAdapter(getActivity(), mData, width, height);
            mDefaultCover = mAdapter.getDefaultCover();
            mGrid.setAdapter(mAdapter);
        } else {
            //mData = data;
            if (mData != null) {
                mData.clear();
            } else {
                mData = new ArrayList<Ebook>();
            }
            for (int i = 0; i < data.size(); i++) {
                mData.add(data.get(i));
            }
            mAdapter.notifyDataSetChanged();
        }

        /*final ShelvesView grid = mGrid;
        grid.setTextFilterEnabled(true);
        grid.setAdapter(adapter);
        grid.setOnScrollListener(new ShelvesScrollManager());
        grid.setOnTouchListener(new FingerTracker());
        grid.setOnItemSelectedListener(new SelectionTracker());
        grid.setOnItemClickListener(new BookViewer());

        registerForContextMenu(grid);*/

        /*mGridPosition = getLayoutInflater().inflate(R.layout.grid_position, null);
        mGridPositionText = (TextView) mGridPosition.findViewById(R.id.text);*/
    }

    @Override
    public Loader<List<Ebook>> onCreateLoader(int id, Bundle args) {
        return new EbookLoader(getActivity(), mNavInfo);
    }

    @Override
    public void onLoadFinished(Loader<List<Ebook>> loader, List<Ebook> data) {
        setupViews(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Ebook>> loader) {
        loader.forceLoad();
    }
}
