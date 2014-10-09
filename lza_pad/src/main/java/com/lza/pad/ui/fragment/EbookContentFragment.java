package com.lza.pad.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.lza.pad.R;
import com.lza.pad.core.db.strategy.Create2DCodeStrategy;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.loader.EbookContentLoader;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.debug.AppLogger;
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
    private ImageView mImgBookCover, mImg2DCode;

    private Ebook mEbook;
    private List<EbookContent> mEbookContents;
    public static final String EBOOK_CONTENT_TAG = "ebook_content_tag";

    private BaseAdapter mAdapter = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mArguments != null) {
            mEbook = mArguments.getParcelable(KEY_EBOOK_INFO);
        }
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

        mImg2DCode = (ImageView) view.findViewById(R.id.ebook_content_two_dimensional_code);
        mImgBookCover = (ImageView) view.findViewById(R.id.ebook_content_img_cover);

        //更新书的信息
        String bookName = mEbook.getName();
        String qkName = mEbook.getTitle_c();
        if (!TextUtils.isEmpty(bookName)) {
            mTxtBookName.setText(bookName.trim());
        }else if (!TextUtils.isEmpty(qkName)){
            mTxtBookName.setText(qkName.trim());
        }

        String bookAuthor = mEbook.getAuthor();
        String qkCompany = mEbook.getCompany();
        if (!TextUtils.isEmpty(bookAuthor)) {
            mTxtBookAuthor.setText(bookAuthor.trim());
        }else if (!TextUtils.isEmpty(qkCompany)) {
            mTxtBookAuthor.setText(qkCompany.trim());
        }

        String bookPress = mEbook.getPress();
        if (!TextUtils.isEmpty(bookPress)) {
            mTxtBookPress.setText(bookPress.trim());
        }

        String bookPubdate = mEbook.getPubdate();
        String qkPubdate = mEbook.getCreat_pubdate();
        if (!TextUtils.isEmpty(bookPubdate)) {
            mTxtBookPubdate.setText(bookPubdate.trim());
        }else if (!TextUtils.isEmpty(qkPubdate)) {
            mTxtBookPubdate.setText(qkPubdate);
        }

        String bookIsbn = mEbook.getIsbn();
        String qkIssn = mEbook.getIssn();
        if (!TextUtils.isEmpty(bookIsbn)) {
            mTxtBookIsbn.setText(bookIsbn.trim());
        }else if (!TextUtils.isEmpty(bookIsbn)) {
            mTxtBookIsbn.setText(qkIssn);
        }

        //更新封面
        if (mEbook != null) {
            //String imgPath = mEbook.getImgPath();
            String imgPath = RuntimeUtility.getEbookImageFilePath(mEbook);
            if (!TextUtils.isEmpty(imgPath)) {
                BitmapDrawable drawable = new BitmapDrawable(getResources(), imgPath);
                if (drawable != null) {
                    mImgBookCover.setImageDrawable(drawable);
                }else {
                    mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
                }
            }else {
                mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
            }
        }else {
            mImgBookCover.setImageResource(R.drawable.ebook_list_item_no_cover);
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

            if (!TextUtils.isEmpty(twoDimCodeString)) {

                AppLogger.e("twoDimCodeString-->" + twoDimCodeString);

                int width2DCode = getResources().getInteger(R.integer.two_dim_code_width);
                int height2DCode = getResources().getInteger(R.integer.two_dim_code_height);
                Bitmap bmp2DCode = RuntimeUtility.Create2DCode(twoDimCodeString, width2DCode, height2DCode);
                mImg2DCode.setImageBitmap(bmp2DCode);
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
        getLoaderManager().initLoader(0, null, this);
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
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<EbookContent>> loader) {

    }
}
