package com.lza.pad.core.db.strategy;

import android.text.TextUtils;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-23.
 */
public class Create2DCodeStrategy extends BaseStrategy<String> {

    private Ebook mEbook = null;

    public Create2DCodeStrategy(NavigationInfo nav, Ebook ebook) {
        super(nav);
        mEbook = ebook;
    }

    @Override
    public String operation() {
        String twoDimCodeString = null;
        if (mNav != null && mEbook != null) {
            String control = mNav.getApiControlPar();
            if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                    || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
                 twoDimCodeString = mEbook.getName()
                        + "---01---" + mEbook.getBookId()
                        + "---下载移动图书馆"
                        + "---" + mNav.getApiSchoolIdPar()
                        + "---" + mNav.getApiUrl();
            } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
                twoDimCodeString = mEbook.getTitle_c()
                        + "---02---" + mEbook.getId()
                        + "---下载移动图书馆"
                        + "---" + mNav.getApiSchoolIdPar()
                        + "---" + mNav.getApiUrl();
            } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)) {
                String url = mNav.getApiUrl();
                String marcNo = mEbook.getUrl();
                if (!TextUtils.isEmpty(url)) {
                    if (!TextUtils.isEmpty(marcNo)) {
                        twoDimCodeString = mEbook.getTitle()
                                + "---04---" + mEbook.getUrl()
                                + "---下载移动图书馆"
                                + "---" + mNav.getApiSchoolIdPar()
                                + "---" + mNav.getApiUrl();
                    } else {
                        marcNo = mEbook.getMarc_no();
                        if (!TextUtils.isEmpty(marcNo)) {
                            twoDimCodeString = mEbook.getTitle()
                                    + "---05---" + marcNo
                                    + "---下载移动图书馆"
                                    + "---" + mNav.getApiSchoolIdPar()
                                    + "---" + url;
                        }
                    }
                }
            } else if (control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
                twoDimCodeString = mEbook.getTitle()
                        + "---03---" + mEbook.getUrl()
                        + "---下载移动图书馆"
                        + "---" + mNav.getApiSchoolIdPar()
                        + "---" + mNav.getApiUrl();
            } else if (control.equals(REQUEST_CONTROL_TYPE_SEARCH)) {
                String url = mNav.getApiUrl();
                String marcNo = mEbook.getMarc_no();
                if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(marcNo)) {
                    twoDimCodeString = mEbook.getTitle()
                            + "---05---" + marcNo
                            + "---下载移动图书馆"
                            + "---" + mNav.getApiSchoolIdPar()
                            + "---" + url;
                }
            }
        }
        return twoDimCodeString;
    }
}
