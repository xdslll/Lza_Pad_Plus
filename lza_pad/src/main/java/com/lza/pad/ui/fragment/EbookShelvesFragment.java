package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.db.loader.EbookLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.EbookCountStrategy;
import com.lza.pad.core.request.EbookUrlRequest;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.ui.adapter.EbookAdapter;
import com.lza.pad.ui.widget.ShelvesView;

import java.util.List;

/**
 * 实现书架功能
 *
 * @author xiads
 * @Date 14-9-14.
 */
public class EbookShelvesFragment extends AbstractFragment
        implements LoaderManager.LoaderCallbacks<List<Ebook>>, Consts {

    private ShelvesView mGrid;
    private int mCurrentPage = 0;
    private int mTotalPage = 0;
    private Button mBtnPrev, mBtnNext;
    private final int LOADER_ID = 0;
    private EbookAdapter mAdapter;
    private int mRowNumber, mColNumber, mVerticalOffset;
    private float mImgScaling;
    private List<Ebook> mEbooks = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImgScaling = mNavInfo.getImgScaling();
        if (mImgScaling == 0) {
            mImgScaling = NavigationInfo.DEFAULT_IMG_SCALING;
        }
        mRowNumber = mNavInfo.getDataRowNumber();
        mColNumber = mNavInfo.getDataColumnNumber();
        mVerticalOffset = mNavInfo.getVerticalOffset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_shelves, container, false);
        mGrid = (ShelvesView) view.findViewById(R.id.ebook_list_grid_shelves);
        mGrid.setNumColumns(mColNumber);
        mGrid.setRowNumber(mRowNumber);
        mGrid.setVerticalOffset(mVerticalOffset);

        mBtnPrev = (Button) view.findViewById(R.id.ebook_list_page_prev);
        mBtnNext = (Button) view.findViewById(R.id.ebook_list_page_next);

        mBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPage--;
                if (mCurrentPage >= 1) {
                    mNavInfo.setApiPagePar(mCurrentPage);
                    if (mNavInfo.getRunningMode() == 0) {
                        getLoaderManager().restartLoader(LOADER_ID, null, EbookShelvesFragment.this);
                    } else {
                        loadFromNetwork();
                    }
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPage++;
                mNavInfo.setApiPagePar(mCurrentPage);
                if (mNavInfo.getRunningMode() == 0) {
                    getLoaderManager().restartLoader(LOADER_ID, null, EbookShelvesFragment.this);
                } else {
                    loadFromNetwork();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化当前页数
        mCurrentPage = mNavInfo.getApiPagePar();
        if (mCurrentPage == 1) {
            mBtnPrev.setVisibility(View.GONE);
        } else {
            mBtnPrev.setVisibility(View.VISIBLE);
        }
        if (mNavInfo.getRunningMode() == 0) {
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            loadFromNetwork();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setupViews(List<Ebook> data) {

        if (mGrid != null) {
            int width = mGrid.getWidth();
            int height = mGrid.getHeight();
            int maxW = width / mColNumber;
            int maxH = height / mRowNumber;

            if (mAdapter == null) {
                mEbooks = data;
                mAdapter = new EbookAdapter(getActivity(), mEbooks, mNavInfo, width, height);
                mGrid.setAdapter(mAdapter);
            } else {
                if (mEbooks != null) {
                    mEbooks.clear();
                    for (int i = 0; i < data.size(); i++) {
                        mEbooks.add(data.get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mEbooks != null && mEbooks.size() > 0) {
                        Ebook ebook = mEbooks.get(position);
                        EbookContentFragment fragment = new EbookContentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_NAVIGATION_INFO, mNavInfo);
                        bundle.putParcelable(KEY_EBOOK_INFO, ebook);

                        fragment.setArguments(bundle);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment oldFrt = fm.findFragmentByTag(EbookContentFragment.EBOOK_CONTENT_TAG);
                        if (oldFrt != null) {
                            ft.remove(oldFrt);
                        }
                        ft.add(R.id.home_container, fragment, EbookContentFragment.EBOOK_CONTENT_TAG).commit();
                    }
                }
            });
            caclTotalPages();
        }
    }

    private void caclTotalPages() {
        if (mTotalPage == 0) {
            EbookCountStrategy countStrategy = new EbookCountStrategy(mNavInfo);
            long dataAmount = countStrategy.operation();
            long pageSize = mNavInfo.getApiPageSizePar();
            float _maxPage = (float) dataAmount / pageSize;
            mTotalPage = (int) Math.ceil(_maxPage);
        } else {
            mCurrentPage = mNavInfo.getApiPagePar();
            if (mCurrentPage == 1) {
                mBtnPrev.setVisibility(View.GONE);
            } else {
                if (mCurrentPage < mTotalPage) {
                    mBtnPrev.setVisibility(View.VISIBLE);
                    mBtnNext.setVisibility(View.VISIBLE);
                } else {
                    mBtnNext.setVisibility(View.GONE);
                }
            }
        }
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
                            mEbooks = ebookRequest.getContents();
                            if (mEbooks != null) {
                                setupViews(mEbooks);
                            }
                            mTotalPage = ebookRequest.getYe();
                        }
                    }
                },
                EbookRequest.class);
        request.send();
    }
}
