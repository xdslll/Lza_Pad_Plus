package com.lza.pad.core.request;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
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
public class EbookImageCacheTask extends AsyncTask<Void, Integer, Boolean> {

    private boolean mIsFinished = false;
    private int mCurrentIndex = 0;
    private List<Ebook> mEbooks;
    private EbookRequestTask mRequest;

    public EbookImageCacheTask(List<Ebook> ebooks) {
        this.mEbooks = ebooks;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        AppLogger.e("OK~~~~~~~~~~~~~~~~~~~~~~~~~~");
        final int lastIndex = params.length;
        for (final Ebook ebook : mEbooks) {
            if (ebook != null) {
                String url = ebook.getImgUrl();
                ImageRequest request = new ImageRequest(url,
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
                                            mIsFinished = true;
                                        }else {
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
                VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(request);
            }
        }
        return null;
        /*for (;;) {
            while (mIsFinished) {
                return true;
            }
        }*/
    }
}
