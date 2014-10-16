package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.loader.EbookLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.strategy.EbookCountStrategy;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.ui.widget.BookShelfItem;
import com.lza.pad.ui.widget.BookSize;
import com.lza.pad.ui.widget.Bookshelf;
import com.lza.pad.ui.widget.TitleBar;

import java.util.List;

/**
 * 电子图书列表的模板页
 *
 * @author Sam
 * @Date 14-9-12
 */
@Deprecated
public class EbookListFragment extends AbstractFragment implements
        BookShelfItem.OnBookClickListener,
        LoaderManager.LoaderCallbacks<List<Ebook>> {

    private FrameLayout mContainer;
    private Button mBtnPrevPage, mBtnNextPage;
    private TextView mTxtEmpty;

    private int mContainerWidth = 0, mContainerHeight = 0;
    private final int CREATE_LAYOUT_CHILD = 0x001;
    private final int RESET_LAYOUT_CHILD = 0x002;
    private int mMaxPage = 0;
    private List<Ebook> mEbooks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mContainer != null) {
            mContainer.removeAllViews();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_list_old, container, false);
        mTitleBar = (TitleBar) view.findViewById(R.id.ebook_list_title_bar);
        mContainer = (FrameLayout) view.findViewById(R.id.ebook_list_container);
        mBtnPrevPage = (Button) view.findViewById(R.id.ebook_list_page_prev);
        mBtnNextPage = (Button) view.findViewById(R.id.ebook_list_page_next);
        mTxtEmpty = (TextView) view.findViewById(R.id.ebook_list_empty_txt);
        initPageButton();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void resetLayoutViews() {
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
            createLayoutViews();
        }
    }

    private void createLayoutViews() {
        if (mEbooks != null) {
            int page = mNavInfo.getApiPagePar();
            if (page == 1) {
                mBtnPrevPage.setVisibility(View.GONE);
            } else {
                mBtnPrevPage.setVisibility(View.VISIBLE);
            }
            if (mEbooks.size() == 0) {
                mBtnNextPage.setVisibility(View.GONE);
            } else {
                mBtnNextPage.setVisibility(View.VISIBLE);
            }

            if (mEbooks.size() > 0) {
                //获取书架的尺寸
                BookSize size = new BookSize(mContainerWidth, mContainerHeight, mNavInfo);
                //生成书架
                if (getActivity() != null) {
                    Bookshelf bookshelf = new Bookshelf(getActivity(), size, this, mEbooks);
                    mContainer.addView(bookshelf);
                    hideEmptyText();
                }
            }else {
                showEmptyText();
            }
        }

    }

    private void initPageButton() {
        mBtnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int page = mNavInfo.getApiPagePar();
                if (page < mMaxPage) {
                    /*if (mContainer.getChildCount() > 0) {
                        mContainer.removeAllViews();
                    }*/
                    mNavInfo.setApiPagePar(page + 1);
                    if (mLoader != null) {
                        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                            mLoader.setNavigationinfo(mNavInfo);
                            mLoader.forceLoad();
                        }
                    }
                    //createLayoutViews();
                }else {
                    ToastUtilsSimplify.show(R.string.ebook_list_page_at_bottom);
                }
            }
        });
        mBtnPrevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = mNavInfo.getApiPagePar();
                if (page > 1) {
                    /*if (mContainer.getChildCount() > 0) {
                        mContainer.removeAllViews();
                    }*/
                    mNavInfo.setApiPagePar(page - 1);
                    if (mLoader != null) {
                        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                            mLoader.setNavigationinfo(mNavInfo);
                            mLoader.forceLoad();
                        }
                    }
                    //createLayoutViews();
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CREATE_LAYOUT_CHILD:
                    if (mContainerWidth > 0 && mContainerHeight > 0) {
                        if (mContainer.getChildCount() == 0) {
                            createLayoutViews();
                        }
                    }
                    break;
                case RESET_LAYOUT_CHILD:
                    if (mContainerWidth > 0 && mContainerHeight > 0) {
                        resetLayoutViews();
                    }
                    break;
            }
        }
    };

    /**
     * 电子书的点击事件
     *
     * @param v
     * @param rowIndex
     * @param colIndex
     */
    @Override
    public void onBookClick(View v, int rowIndex, int colIndex) {
        if (mEbooks != null && mEbooks.size() > 0) {
            int position = mNavInfo.getDataColumnNumber() * rowIndex + colIndex;
            Ebook ebook = mEbooks.get(position);
            //ToastUtilsSimplify.show(rowIndex + "," + colIndex + "," + ebook.getName());
            EbookContentFragment fragment = new EbookContentFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_NAVIGATION_INFO, mNavInfo);
            bundle.putParcelable(KEY_EBOOK_INFO, ebook);

            fragment.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.home_container, fragment, EbookContentFragment.TAG_EBOOK_CONTENT).commit();
        }
    }

    private EbookLoader mLoader;

    @Override
    public Loader<List<Ebook>> onCreateLoader(int id, Bundle args) {
        mLoader = new EbookLoader(getActivity(), mNavInfo);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Ebook>> loader, List<Ebook> data) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        mContainerWidth = mContainer.getWidth();
        mContainerHeight = mContainer.getHeight();
        mEbooks = data;
        AppLogger.e("mContainerWidth=" + mContainerWidth + ",mContainerHeight=" + mContainerHeight);

        if (mContainer.getChildCount() == 0) {
            //获取最大页数
            //long dataAmount = EbookDao.getInstance().countOfByType(mNavInfo.getApiControlPar());
            EbookCountStrategy countStrategy = new EbookCountStrategy(mNavInfo);
            long dataAmount = countStrategy.operation();
            long pageSize = mNavInfo.getApiPageSizePar();
            float _maxPage = (float) dataAmount / pageSize;
            //BigDecimal bd = new BigDecimal(_maxPage).setScale(0, BigDecimal.ROUND_HALF_UP);
            //mMaxPage = (int) _maxPage;
            mMaxPage = (int) Math.ceil(_maxPage);
            //AppLogger.e("dataAmount-->" + dataAmount + ",pageSize-->" + pageSize + ",_maxPage-->" + _maxPage + ",maxPage-->" + mMaxPage);

            mHandler.sendEmptyMessage(CREATE_LAYOUT_CHILD);
        }else {
            mHandler.sendEmptyMessage(RESET_LAYOUT_CHILD);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Ebook>> loader) {

    }

    public void setEmptyText(String text) {
        if (mTxtEmpty != null) {
            mTxtEmpty.setText(text);
        }
    }

    public void hideEmptyText() {
        setEmptyTextVisibility(false);
    }

    public void showEmptyText() {
        setEmptyTextVisibility(true);
    }

    private void setEmptyTextVisibility(boolean ifShow) {
        if (mTxtEmpty != null) {
            if (ifShow) {
                mTxtEmpty.setVisibility(View.VISIBLE);
            } else {
                mTxtEmpty.setVisibility(View.GONE);
            }
        }
    }
}
