package com.lza.pad.core.db.strategy;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lza.pad.core.db.adapter.JournalsContentAdapter;
import com.lza.pad.core.db.dao.EbookContentDao;
import com.lza.pad.core.db.dao.HotBookContentDao;
import com.lza.pad.core.db.dao.JournalsContentDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.HotBookContent;
import com.lza.pad.core.db.model.JournalsContent;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.EbookUrlRequest;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.VolleySingleton;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class CacheEbookContentStrategy extends BaseStrategy<Void> {

    private Ebook mEbook;
    private NavigationInfo mNav;
    private OnResponseListener<Void> mListener;
    public CacheEbookContentStrategy(Ebook ebook, NavigationInfo nav, OnResponseListener<Void> listener) {
        super(nav);
        mEbook = ebook;
        mNav = nav;
        mListener = listener;
    }

    @Override
    public Void operation() {
        String control = mNav.getApiControlPar();
        if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
            cacheEbookContent();
        } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
            cacheJournalsContent();
        } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)) {
            cacheHotBookContent();
        } else if (control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
            cacheHotBookContent();
        } else if (control.equals(REQUEST_CONTROL_TYPE_SEARCH)) {
            cacheHotBookContent();
        } else{
            if (mListener != null) {
                mListener.onError(new IllegalStateException());
            }
        }

        return null;
    }

    /**
     * 缓存热门推荐目录
     */
    private void cacheHotBookContent() {
        if (mNav != null && mEbook != null) {
            //final String marcNo = mEbook.getUrl();
            final String marcNo = RuntimeUtility.getMarNoFromEbook(mEbook);
            mNav.setApiControlPar(HOT_BOOK_CONTROL_CONTENT);
            mNav.setApiActionPar(HOT_BOOK_ACTION_CONTENT);
            mNav.setApiMarcNoPar(marcNo);
            mNav.setApiActPar("1");

            String url = ApiUrlFactory.createApiUrl(mNav);
            AppLogger.e("url --> " + url);

            Response.Listener<String> successListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    int syntaxErrorIndex = s.lastIndexOf("{}");
                    if (syntaxErrorIndex != -1) {
                        s = s.substring(0, syntaxErrorIndex);
                    }
                    Gson gson = new Gson();
                    EbookRequest ebookRequest = gson.fromJson(s, EbookRequest.class);
                    if (ebookRequest != null) {
                        List<HotBookContent> contents1 = ebookRequest.getContents1();
                        if (contents1 != null && contents1.size() > 0) {
                            for (int j = 0; j < contents1.size(); j++) {
                                HotBookContent hotBookContent = contents1.get(j);
                                hotBookContent.setMarc_no(marcNo);
                                HotBookContentDao.getInstance().createNewData(hotBookContent);
                            }
                        }
                    }
                    if (mListener != null) {
                        mListener.onSuccess(null);
                    }
                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    if (mListener != null) {
                        mListener.onError(volleyError);
                    }
                }
            };

            StringRequest request = new StringRequest(
                    com.android.volley.Request.Method.GET, url, successListener, errorListener);
            request.setTag(REQUEST_TAG);
            VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(request);

            /*EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(
                    mNav,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mListener != null) {
                                mListener.onError(error);
                            }
                        }
                    },
                    new Response.Listener<EbookRequest>() {
                        @Override
                        public void onResponse(EbookRequest response) {
                            if (response != null) {
                                List<HotBookContent> contents1 = response.getContents1();
                                if (contents1 != null && contents1.size() > 0) {
                                    for (int j = 0; j < contents1.size(); j++) {
                                        HotBookContent hotBookContent = contents1.get(j);
                                        HotBookContentDao.getInstance().createNewData(hotBookContent);
                                    }
                                }
                            }
                            if (mListener != null) {
                                mListener.onSuccess(null);
                            }
                        }
                    },
                    EbookRequest.class);
            request.getRequest().setTag(REQUEST_TAG);
            request.send();*/
        }
    }

    /**
     * 缓存期刊目录
     */
    private void cacheJournalsContent() {
        if (mNav != null && mEbook != null) {
            mNav.setApiActionPar(JOURNALS_ACTION_CONTENT);
            mNav.setApiPageSizePar(1000);
            mNav.setApiPagePar(1);
            mNav.setApiBookId(mEbook.getId());
            mEbook.setBookId(mEbook.getId());

            EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(
                    mNav,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mListener != null) {
                                mListener.onError(error);
                            }
                        }
                    },
                    new Response.Listener<EbookRequest>() {
                        @Override
                        public void onResponse(EbookRequest response) {
                            if (response != null) {
                                List<Ebook> contents = response.getContents();
                                if (contents != null && contents.size() > 0) {
                                    for (int j = 0; j < contents.size(); j++) {
                                        Ebook ebook = contents.get(j);
                                        if (ebook != null) {
                                            ebook.setBookId(mEbook.getBookId());
                                            JournalsContentAdapter adapter = new JournalsContentAdapter();
                                            JournalsContent content = adapter.apdater(ebook);
                                            JournalsContentDao.getInstance().createNewData(content);
                                        }
                                    }
                                }
                            }
                            if (mListener != null) {
                                mListener.onSuccess(null);
                            }
                        }
                    },
                    EbookRequest.class);
            request.getRequest().setTag(REQUEST_TAG);
            request.send();
        }
    }

    /**
     * 缓存电子图书目录
     */
    private void cacheEbookContent() {
        if (mNav != null && mEbook != null) {
            //初始化请求参数
            mNav.setApiActionPar(EBOOK_ACTION_CONTENT);
            mNav.setApiPageSizePar(1000);
            mNav.setApiPagePar(1);
            mNav.setApiBookId(mEbook.getBookId());
            EbookUrlRequest<EbookRequest> request = new EbookUrlRequest<EbookRequest>(
                    mNav,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (mListener != null) {
                                mListener.onError(error);
                            }
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
                                            //EbookContentDao.getInstance().createNewData(content);
                                            EbookContentDao.getInstance().createOrUpdateEbookContent(content);
                                        }
                                    }
                                }
                            }
                            if (mListener != null) {
                                mListener.onSuccess(null);
                            }
                        }
                    },
                    EbookRequest.class);
            request.getRequest().setTag(REQUEST_TAG);
            request.send();
        }
    }
}
