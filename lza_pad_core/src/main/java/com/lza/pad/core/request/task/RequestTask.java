package com.lza.pad.core.request.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.dao.EbookRequestDao;
import com.lza.pad.core.db.dao.NewsDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.EbookUrlRequest;

import java.util.ArrayList;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class RequestTask extends AsyncTask<Void, Void, Void> implements Consts {

    private NavigationInfo mNav;
    private Handler mHandler;

    public RequestTask(NavigationInfo nav, Handler handler) {
        this.mNav = nav;
        this.mHandler = handler;
    }

    @Override
    protected Void doInBackground(Void... params) {

        EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(mNav,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },
                new Response.Listener<EbookRequest>() {
                    @Override
                    public void onResponse(EbookRequest response) {
                        if (response != null) {
                            //将请求数据保存到数据库
                            EbookRequestDao.getInstance().createNewData(response);

                            if (mNav.getApiControlPar().equals(REQUEST_CONTROL_TYPE_LIB_NEWS)) {
                                ArrayList<Ebook> ebooks = (ArrayList<Ebook>) response.getContents();
                                for (Ebook ebook : ebooks) {
                                   NewsDao.getInstance().createOrUpdateNews(ebook);
                                }
                                if (mHandler != null) {
                                    mHandler.sendEmptyMessage(TASK_OVER);
                                }
                            }else {
                                String endTag = response.getEndTag();
                                if (!TextUtils.isEmpty(endTag) && endTag.equals(EbookRequest.END_TAG_NOT_FINISH)) {
                                    ArrayList<Ebook> ebooks = (ArrayList<Ebook>) response.getContents();
                                    Message msg = new Message();
                                    msg.what = TASK_FINISH;
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList(KEY_EBOOK_INFO, ebooks);
                                    bundle.putParcelable(KEY_EBOOK_REQUEST_INFO, response);
                                    msg.setData(bundle);
                                    if (mHandler != null) {
                                        mHandler.sendMessage(msg);
                                    }
                                } else {
                                    if (mHandler != null) {
                                        mHandler.sendEmptyMessage(TASK_OVER);
                                    }
                                }
                            }
                        }else {
                            mHandler.sendEmptyMessage(TASK_OVER);
                        }
                    }
                },
                EbookRequest.class);
        request.getRequest().setTag(REQUEST_TAG);
        request.send();
        return null;
    }
}
