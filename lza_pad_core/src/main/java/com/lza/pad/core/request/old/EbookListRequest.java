package com.lza.pad.core.request.old;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;

/**
 * 电子图书相关的URL请求
 *
 * @author xiads
 * @Date 14-9-12.
 */
@Deprecated
public class EbookListRequest extends AbstractRequest<EbookRequest> {

    private String mRequestUrl;
    private GsonRequest<EbookRequest> mRequest;

    public EbookListRequest(OnResponseListener<EbookRequest> listener, NavigationInfo ni) {
        super(listener);
        mRequestUrl = ApiUrlFactory.createApiUrl(ni);
        AppLogger.e("mRequestUrl-->" + mRequestUrl);
        mRequest = new GsonRequest<EbookRequest>(Request.Method.GET, mRequestUrl,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mListener.onError(volleyError);
                    }
                },
                EbookRequest.class, null,
                new Response.Listener<EbookRequest>() {
                    @Override
                    public void onResponse(EbookRequest request) {
                        mListener.onSuccess(request);
                    }
                });
    }

    public synchronized void send() {
        if (mRequest != null && !TextUtils.isEmpty(mRequestUrl)) {
            VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(mRequest);
        }
    }
}
