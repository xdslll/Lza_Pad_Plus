package com.lza.pad.core.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lza.pad.core.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.dao.EbookContentDao;
import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.EbookRequestDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.lib.support.utils.UniversalUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-15.
 */
public class EbookRequestTask extends AsyncTask<Void, Integer, Void> implements Consts {

    private NavigationInfo mNav;
    private Response.ErrorListener mErrListener;
    private Response.Listener<EbookRequest> mRepListener;
    private EbookUrlRequest<EbookRequest> mRequest;
    private Context mContext;
    private Intent mBroadIntent;

    public EbookRequestTask(Context c, NavigationInfo nav) {
        this.mContext = c;
        this.mNav = nav;
        mBroadIntent = new Intent();
        mBroadIntent.setAction(RECEIVER_DOWNLOAD_TEXT);
        getMaxPage();
    }

    /**
     * 请求是否发送
     */
    private Boolean mRequestHasSend = false;

    /**
     * 是否到达最后一页
     */
    private boolean mIsEnd = false;

    /**
     * 是否为第一次请求
     */
    private boolean mIsFirst = true;

    private int mMaxPage = 0;

    /**
     * 图片请求是否发送
     */
    private boolean mImageRequestHasSend = false;

    private final static String REQUEST_TAG = "ebook_request_tag";

    public void cancel() {
        RequestQueue queue = VolleySingleton.getInstance(mContext).getRequestQueue();
        queue.cancelAll(REQUEST_TAG);
    }

    private ProgressDialog mProgessDialog = null;
    private static final int SHOW_DIALOG = 0x001;
    private static final int DISMISS_DIALOG = 0x002;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_DIALOG:
                    /*mProgessDialog = new ProgressDialog(mContext);
                    mProgessDialog.setIndeterminate(true);
                    mProgessDialog.setMessage(mContext.getString(R.string.cache_start));
                    mProgessDialog.show();*/
                    ToastUtilsSimplify.show(R.string.cache_starting);
                    break;
                case DISMISS_DIALOG:
                    /*if (mProgessDialog != null && mProgessDialog.isShowing()) {
                        mProgessDialog.dismiss();
                    }
                    ToastUtilsSimplify.show(R.string.cache_success);*/
                    break;
                default:
            }
        }
    };

    private Handler mViewHandler;
    public void setHandler(Handler handler) {
        this.mViewHandler = handler;
    }

    public void getMaxPage() {
        mRepListener = new Response.Listener<EbookRequest>() {
            @Override
            public void onResponse(EbookRequest response) {
                mMaxPage = response.getYe();
            }
        };
        EbookUrlRequest request = new EbookUrlRequest(
                mNav, mErrListener, mRepListener, EbookRequest.class);
        request.send();
    }

    @Override
    protected Void doInBackground(Void... params) {

        for (;;) {
            if (mMaxPage <= 0) {
                continue;
            }else {
                break;
            }
        }

        mRepListener =  new Response.Listener<EbookRequest>() {
            @Override
            public void onResponse(EbookRequest response) {
                if (response != null) {
                    String state = response.getState();
                    if (!TextUtils.isEmpty(state) && state.equals(EbookRequest.STATE_OK)) {
                        //如果是列表则缓存图书和图片
                        cacheEbookAndImage(response);
                    }
                }
            }
        };
        mHandler.sendEmptyMessage(SHOW_DIALOG);
        for (int i = 0; i < mMaxPage; i++) {
            mNav.setApiPagePar(i + 1);
            mRequest = new EbookUrlRequest(mNav, mErrListener, mRepListener, EbookRequest.class);
            mRequest.send();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mHandler.sendEmptyMessage(DISMISS_DIALOG);
    }

    private int mCurrentIndex = 0;

    private void cacheEbookAndImage(EbookRequest response) {
        List<Ebook> ebooks = response.getContents();
        if (ebooks != null && ebooks.size() > 0) {
            //将请求数据保存到数据库
            EbookRequestDao requestDao = EbookRequestDao.getInstance();
            requestDao.createNewData(response);
            //缓存电子书
            EbookDao ebookDao = EbookDao.getInstance();
            ebookDao.createNewDatas(ebooks, mNav);
            //缓存图片
            cacheImage(ebooks);
            //cacheEbookContent(ebooks);
        }
    }

    private void cacheImage(List<Ebook> ebooks) {
        final int lastIndex = ebooks.size();
        for (int i = 0; i < ebooks.size() ; i++) {
            final Ebook ebook = ebooks.get(i);
            if (ebook != null) {
                String url = ebook.getImgUrl();
                final ImageRequest request = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                if (bitmap != null && ebook != null) {
                                    File imgFile = new File(ebook.getImgPath());
                                    if (imgFile.exists()) {
                                        imgFile.delete();
                                    }
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(imgFile);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                                        fos.flush();
                                        if (!bitmap.isRecycled())
                                            bitmap.recycle();
                                        if (mCurrentIndex + 1 >= lastIndex) {
                                            mRequestHasSend = false;
                                        } else {
                                            mCurrentIndex++;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        UniversalUtility.closeSilently(fos);
                                    }
                                }
                            }
                        },
                        0, 0, null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                            }
                        });
                VolleySingleton.getInstance(mContext).addToRequestQueue(request);
            }
        }
    }

    public void cacheEbookContent(List<Ebook> ebooks) {
        for (int i = 0; i < ebooks.size() ; i++) {
            final Ebook ebook = ebooks.get(i);
            if (ebook != null) {
                NavigationInfo ni = mNav.clone();
                ni.setApiActionPar(EBOOK_ACTION_CONTENT);
                ni.setApiPageSizePar(1000);
                ni.setApiPagePar(1);
                ni.setApiBookId(ebook.getBookId());

                EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(ni,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        },
                        new Response.Listener<EbookRequest>() {
                            @Override
                            public void onResponse(EbookRequest response) {
                                if (response != null) {
                                    List<Ebook> contents = response.getContents();
                                    if (contents != null && contents.size() > 0) {
                                        for (int j = 0; j < contents.size(); j++) {
                                            Ebook ebook1 = contents.get(j);
                                            if (ebook1 != null) {
                                                EbookContent content = new EbookContent();
                                                content.setId(ebook1.getId());
                                                content.setBookId(ebook1.getBookId());
                                                content.setName(ebook1.getName());
                                                content.setPage(ebook1.getPage());
                                                EbookContentDao.getInstance().createNewData(content);
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        EbookRequest.class);
                request.send();
            }
        }
    }
}
