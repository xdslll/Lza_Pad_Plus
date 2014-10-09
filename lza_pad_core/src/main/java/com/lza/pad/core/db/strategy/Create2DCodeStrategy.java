package com.lza.pad.core.db.strategy;

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
                twoDimCodeString = mEbook.getTitle()
                        + "---04---" + mEbook.getUrl()
                        + "---下载移动图书馆"
                        + "---" + mNav.getApiSchoolIdPar()
                        + "---" + mNav.getApiUrl();
            } else if (control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
                twoDimCodeString = mEbook.getTitle()
                        + "---03---" + mEbook.getUrl()
                        + "---下载移动图书馆"
                        + "---" + mNav.getApiSchoolIdPar()
                        + "---" + mNav.getApiUrl();
            }
        }
        return twoDimCodeString;
    }
}
