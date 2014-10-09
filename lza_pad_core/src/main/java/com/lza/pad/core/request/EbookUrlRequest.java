package com.lza.pad.core.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class EbookUrlRequest<T> {

    private GsonRequest<T> mRequest;
    private String mUrl;

    public EbookUrlRequest(NavigationInfo nav,
                           Response.ErrorListener errListener,
                           Response.Listener<T> repListener,
                           Class<T> clazz) {
        createRequest(nav, errListener, repListener, clazz);
    }

    private void createRequest(NavigationInfo nav,
                               Response.ErrorListener errListener,
                               Response.Listener<T> repListener,
                               Class<T> clazz) {
        mUrl = ApiUrlFactory.createApiUrl(nav);
        mRequest = new GsonRequest<T>(Request.Method.GET,
                mUrl, errListener, clazz, null, repListener);
        AppLogger.e("Request Url-->" + mUrl);
    }

    public GsonRequest<T> getRequest() {
        return mRequest;
    }

    public void send() {
        VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(mRequest);
    }
}
