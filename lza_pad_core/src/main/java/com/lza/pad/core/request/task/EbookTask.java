package com.lza.pad.core.request.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.CacheEbookContentStrategy;
import com.lza.pad.core.db.strategy.EbookCacheStrategy;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.lib.support.utils.UniversalUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-18.
 */
public class EbookTask extends AsyncTask<Void, Void, Void> implements Consts, OnResponseListener<Void> {

    private NavigationInfo mNav;
    private Handler mHandler;
    private Ebook mEbook;

    public EbookTask(Ebook ebook, NavigationInfo nav, Handler handler) {
        this.mEbook = ebook;
        this.mNav = nav;
        this.mHandler = handler;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if (mEbook != null && mNav != null) {
            EbookCacheStrategy cacheTask = new EbookCacheStrategy(mNav, mEbook);
            cacheTask.operation();
            cacheImage(mEbook);
        }
        return null;
    }

    private void cacheImage(final Ebook ebook) {
        String url = RuntimeUtility.getEbookImageUrl(ebook);
        if (!TextUtils.isEmpty(url)) {
            final ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            if (bitmap != null) {
                                //File imgFile = new File(ebook.getImgPath());
                                File imgFile = RuntimeUtility.getEbookImageFile(ebook);
                                if (imgFile != null) {
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

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        UniversalUtility.closeSilently(fos);
                                    }
                                }
                            }
                            //缓存图片结束后继续缓存目录
                            cacheEbookContent(ebook);
                        /*if (mHandler != null) {
                            mHandler.sendEmptyMessage(TASK_EBOOK_FINISH);
                        }*/
                        }
                    },
                    0, 0, null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(TASK_EBOOK_FINISH);
                            }
                        }
                    });
            request.setTag(REQUEST_TAG);
            VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(request);
        } else {
            if (mHandler != null) {
                mHandler.sendEmptyMessage(TASK_EBOOK_FINISH);
            }
        }
    }

    public void cacheEbookContent(Ebook ebook) {
        if (ebook != null) {
            NavigationInfo ni = mNav.clone();

            CacheEbookContentStrategy strategy = new CacheEbookContentStrategy(ebook, ni, this);
            strategy.operation();
        }
    }

    @Override
    public void onSuccess(Void aVoid) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(TASK_EBOOK_FINISH);
        }
    }

    @Override
    public void onError(Exception error) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(TASK_EBOOK_FINISH);
        }
    }
}
