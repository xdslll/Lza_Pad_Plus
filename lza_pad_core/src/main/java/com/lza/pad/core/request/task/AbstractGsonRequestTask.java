package com.lza.pad.core.request.task;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.EbookUrlRequest;
import com.lza.pad.core.utils.Consts;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class AbstractGsonRequestTask implements Consts {

    private NavigationInfo mNav;

    private OnRequestResponse mOnRequestResponse = null;

    public void registerListener(OnRequestResponse onRequestResponse) {
        this.mOnRequestResponse = onRequestResponse;
    }

    public interface OnRequestResponse {
        void onSuccess(EbookRequest data);
        void onError();
    }

    public AbstractGsonRequestTask(NavigationInfo nav) {
        this.mNav = nav;
    }

    public void sendRequest() {
        EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(mNav,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mOnRequestResponse != null) {
                            mOnRequestResponse.onError();
                        }
                    }
                },
                new Response.Listener<EbookRequest>() {
                    @Override
                    public void onResponse(EbookRequest response) {
                        if (mOnRequestResponse != null) {
                            mOnRequestResponse.onSuccess(response);
                        }
                    }
                },
                EbookRequest.class);
        request.send();
    }
}
