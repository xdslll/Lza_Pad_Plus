package com.lza.pad.core.request.old;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.utils.GlobalContext;
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
 * @Date 14-9-12.
 */
@Deprecated
public class EbookImageRequest extends AbstractRequest<Bitmap> {

    private List<Ebook> mEbooks = null;
    private Ebook mEbook = null;

    public EbookImageRequest(List<Ebook> ebooks) {
        this.mEbooks = ebooks;
    }

    public EbookImageRequest(Ebook ebook) {
        this.mEbook = ebook;
    }

    public EbookImageRequest() {

    }

    public void send() {
        send(mEbook);
    }

    public void sendMutiple() {
        if (mEbooks != null) {
            for (Ebook ebook : mEbooks) {
                send(ebook);
            }
        }
    }

    public synchronized void send(final Ebook ebook) {
        if (ebook != null) {
            String url = ebook.getImgUrl();
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            if (bitmap != null) {
                                File imgFile = new File(ebook.getImgPath());
                                if (imgFile.exists()) {
                                    imgFile.delete();
                                }
                                FileOutputStream fos = null;
                                try {
                                    fos = new FileOutputStream(imgFile);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                                    fos.flush();
                                    mListener.onSuccess(bitmap);
                                    if (!bitmap.isRecycled())
                                        bitmap.recycle();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    mListener.onError(e);
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
                            mListener.onError(volleyError);
                        }
                    });
            VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(request);
        }
    }

}
