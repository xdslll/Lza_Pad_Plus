package com.lza.pad.core.request.task;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.network.VolleySingleton;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class AbstractStringRequestTask implements Consts {

    private NavigationInfo mNav;

    private OnStringRequestResponse mOnRequestResponse = null;

    public void registerListener(OnStringRequestResponse onRequestResponse) {
        this.mOnRequestResponse = onRequestResponse;
    }

    public interface OnStringRequestResponse {
        void onSuccess(String data);
        void onError();
    }

    public AbstractStringRequestTask(NavigationInfo nav) {
        this.mNav = nav;
    }

    public void sendRequest() {
        String url = ApiUrlFactory.createApiUrl(mNav);
        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (mOnRequestResponse != null) {
                            mOnRequestResponse.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mOnRequestResponse != null) {
                            mOnRequestResponse.onError();
                        }
                    }
                });
        VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(request);
    }
}
