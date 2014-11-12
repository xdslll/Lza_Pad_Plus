package com.lza.pad.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.loader.EbookContentLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.strategy.CacheEbookContentStrategy;
import com.lza.pad.core.db.strategy.Create2DCodeStrategy;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.ui.adapter.EbookContentAdapterStrategy;

import java.util.List;

/**
 * 电子书的内容页面
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class EbookContentFragment extends AbstractListFragment
        implements Consts, LoaderManager.LoaderCallbacks<List<EbookContent>> {
        //LoaderManager.LoaderCallbacks<List<EbookContent>> {

    private TextView mTxtBookName, mTxtBookAuthor, mTxtBookPress, mTxtBookPubdate, mTxtBookIsbn;
    private ImageButton mImgBtnBack;
    private ImageView mImgBookCover, mImg2DCodeGetEbook, mImg2DCodeGetApk;
    private NetworkImageView mImgBookCoverFromNet;

    private Ebook mEbook;
    private List<EbookContent> mEbookContents;

    private BaseAdapter mAdapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mArguments != null) {
            mEbook = mArguments.getParcelable(KEY_EBOOK_INFO);
        }
        showProgressDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_content, container, false);

        mTxtBookName = (TextView) view.findViewById(R.id.ebook_content_book_name);
        mTxtBookAuthor = (TextView) view.findViewById(R.id.ebook_content_book_author);
        mTxtBookPress = (TextView) view.findViewById(R.id.ebook_content_book_press);
        mTxtBookPubdate = (TextView) view.findViewById(R.id.ebook_content_book_pubdate);
        mTxtBookIsbn = (TextView) view.findViewById(R.id.ebook_content_book_isbn);

        mImgBtnBack = (ImageButton) view.findViewById(R.id.ebook_content_btn_back);

        mImg2DCodeGetEbook = (ImageView) view.findViewById(R.id.ebook_content_2dc_get_ebook);
        mImg2DCodeGetApk = (ImageView) view.findViewById(R.id.ebook_content_2dc_get_apk);

        mImgBookCover = (ImageView) view.findViewById(R.id.ebook_content_img_cover);

        mImgBookCoverFromNet = (NetworkImageView) view.findViewById(R.id.ebook_content_img_cover_net);

        //更新书的信息
        String bookName = mEbook.getName();
        String qkName = mEbook.getTitle_c();
        String bookTitle = mEbook.getTitle();
        if (!TextUtils.isEmpty(bookName)) {
            mTxtBookName.setText(bookName.trim());
            mTxtBookName.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(qkName)){
            mTxtBookName.setText(qkName.trim());
            mTxtBookName.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(bookTitle)) {
            mTxtBookName.setText(bookTitle.trim());
            mTxtBookName.setVisibility(View.VISIBLE);
        }

        String bookAuthor = mEbook.getAuthor();
        String qkCompany = mEbook.getCompany();
        if (!TextUtils.isEmpty(bookAuthor)) {
            mTxtBookAuthor.setText(bookAuthor.trim());
            mTxtBookAuthor.setVisibility(View.VISIBLE);
        }else if (!TextUtils.isEmpty(qkCompany)) {
            mTxtBookAuthor.setText(qkCompany.trim());
            mTxtBookAuthor.setVisibility(View.VISIBLE);
        }

        String bookPress = mEbook.getPress();
        if (!TextUtils.isEmpty(bookPress)) {
            mTxtBookPress.setText(bookPress.trim());
            mTxtBookPress.setVisibility(View.VISIBLE);
        }

        String bookPubdate = mEbook.getPubdate();
        String qkPubdate = mEbook.getCreat_pubdate();
        if (!TextUtils.isEmpty(bookPubdate)) {
            mTxtBookPubdate.setText(bookPubdate.trim());
            mTxtBookPubdate.setVisibility(View.VISIBLE);
        }else if (!TextUtils.isEmpty(qkPubdate)) {
            mTxtBookPubdate.setText(qkPubdate);
            mTxtBookPubdate.setVisibility(View.VISIBLE);
        }

        String bookIsbn = mEbook.getIsbn();
        String qkIssn = mEbook.getIssn();
        if (!TextUtils.isEmpty(bookIsbn)) {
            mTxtBookIsbn.setText(bookIsbn.trim());
            mTxtBookIsbn.setVisibility(View.VISIBLE);
        }else if (!TextUtils.isEmpty(bookIsbn)) {
            mTxtBookIsbn.setText(qkIssn);
            mTxtBookIsbn.setVisibility(View.VISIBLE);
        }

        //更新封面
        if (mNavInfo.getRunningMode() == 0) {
            if (mEbook != null) {
                //String imgPath = mEbook.getImgPath();
                String imgPath = RuntimeUtility.getEbookImageFilePath(mEbook);
                if (!TextUtils.isEmpty(imgPath)) {
                    BitmapDrawable drawable = new BitmapDrawable(getResources(), imgPath);
                    if (drawable != null) {
                        mImgBookCover.setImageDrawable(drawable);
                    } else {
                        mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
                    }
                } else {
                    mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
                }
            } else {
                mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
            }
        } else {
            if (mEbook != null) {
                String imgUrl = RuntimeUtility.getEbookImageUrl(mEbook);
                if (!TextUtils.isEmpty(imgUrl)) {
                    mImgBookCover.setVisibility(View.GONE);
                    mImgBookCoverFromNet.setVisibility(View.VISIBLE);
                    mImgBookCoverFromNet.setDefaultImageResId(R.drawable.ebook_list_item_no_cover);
                    mImgBookCoverFromNet.setErrorImageResId(R.drawable.ebook_list_item_no_cover);
                    mImgBookCoverFromNet.setImageUrl(imgUrl, VolleySingleton.getInstance(getActivity()).getImageLoader());
                } else {
                    mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
                }
            } else {
                mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
            }
        }

        //生成二维码图片
        if (mEbook != null) {
            /*String twoDimCodeString = mEbook.getName()
                    + "---01---" + mEbook.getBookId()
                    + "---" + getString(R.string.ebook_content_download_library)
                    + "---" + mNavInfo.getApiSchoolIdPar()
                    + "---" + mNavInfo.getApiUrl();*/
            Create2DCodeStrategy strategy = new Create2DCodeStrategy(mNavInfo, mEbook);
            String twoDimCodeString = strategy.operation();

            int width2DCode = getResources().getInteger(R.integer.two_dim_code_width);
            int height2DCode = getResources().getInteger(R.integer.two_dim_code_height);

            //先通过学校编号获取二维码，如果获取不到再进行生成
            int schoolId = mNavInfo.getApiSchoolIdPar();
            String resName = "qrcode" + schoolId;
            String packageName = getActivity().getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packageName);
            try {
                Drawable drawable = getResources().getDrawable(resId);
                if (drawable != null) {
                    mImg2DCodeGetApk.setImageDrawable(drawable);
                }
            } catch(Exception ex) {
                String schoolUrl = NavigationInfoDao.getInstance().queryNotClosedBySortId(1).getApiUrl();
                int lastSep = schoolUrl.lastIndexOf("/");
                String schoolUrlPrefix = schoolUrl.substring(0, lastSep);
                String getApk2DCString = schoolUrlPrefix + "/pad_client.apk";

                Bitmap bmp2DCodeGetApk = RuntimeUtility.Create2DCode(getApk2DCString, width2DCode, height2DCode);
                mImg2DCodeGetApk.setImageBitmap(bmp2DCodeGetApk);
            }

            if (!TextUtils.isEmpty(twoDimCodeString)) {
                AppLogger.e("twoDimCodeString-->" + twoDimCodeString);
                Bitmap bmp2DCodeGetEbook = RuntimeUtility.Create2DCode(twoDimCodeString, width2DCode, height2DCode);
                mImg2DCodeGetEbook.setImageBitmap(bmp2DCodeGetEbook);
            }
        }

        //返回按钮事件
        mImgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ListView list = getListView();
        if (list != null) {
            list.setDivider(null);
        }
        if (mNavInfo.getRunningMode() == 0) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            loadFromNetwork();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNavInfo != null) {
            mNavInfo = null;
        }
    }

    public void clear() {
        getFragmentManager()
                .beginTransaction()
                .remove(EbookContentFragment.this)
                .commit();
    }

    @Override
    public Loader<List<EbookContent>> onCreateLoader(int id, Bundle args) {
        return new EbookContentLoader(getActivity(), mEbook, mNavInfo);
    }

    @Override
    public void onLoadFinished(Loader<List<EbookContent>> loader, List<EbookContent> data) {
        setupViews(data);
    }

    @Override
    public void onLoaderReset(Loader<List<EbookContent>> loader) {
        loader.forceLoad();
    }

    private void setupViews(List<EbookContent> data) {
        if (data != null) {
            mEbookContents = data;
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }else {
            //mAdapter = new EbookContentAdapter(getActivity(), mEbook, mEbookContents);
            EbookContentAdapterStrategy strategy = new EbookContentAdapterStrategy(
                    mNavInfo, getActivity(), mEbook, mEbookContents);
            mAdapter = strategy.operation();
            if (mAdapter != null) {
                setListAdapter(mAdapter);
                getListView().setFastScrollEnabled(true);
            }
        }
        dismissProgressDialog();
    }

    private void loadFromNetwork() {
        CacheEbookContentStrategy contentStrategy = new CacheEbookContentStrategy(
                mEbook, mNavInfo,
                new OnResponseListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (getActivity() != null) {
                            getLoaderManager().initLoader(0, null, EbookContentFragment.this);
                        }
                    }

                    @Override
                    public void onError(Exception error) {
                        error.printStackTrace();
                    }
                });
        contentStrategy.operation();
    }
}
