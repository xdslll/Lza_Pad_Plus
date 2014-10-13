package com.lza.pad.service;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.VolleySingleton;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/13/14.
 */
public class EbookRequestService extends AsyncTask<Void, Void, String> implements Consts {

    private Context mContext;
    public EbookRequestService(Context context) {
        this.mContext = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        StringRequest request = new StringRequest(
                "http://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppLogger.e(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
        return "OK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        AppLogger.e(s);
    }
}
