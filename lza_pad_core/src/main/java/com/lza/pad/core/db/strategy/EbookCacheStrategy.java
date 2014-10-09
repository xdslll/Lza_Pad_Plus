package com.lza.pad.core.db.strategy;

import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.HotBookDao;
import com.lza.pad.core.db.dao.JournalsDao;
import com.lza.pad.core.db.dao.NewsDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class EbookCacheStrategy<Void> extends BaseStrategy {

    private Ebook mEbook = null;

    public EbookCacheStrategy(NavigationInfo nav, Ebook ebook) {
        super(nav);
        mEbook = ebook;
    }

    @Override
    public Void operation() {
        if (mNav != null && mEbook != null) {
            String control = mNav.getApiControlPar();
            if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                    || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
                EbookDao.getInstance().createOrUpdateEbook(mEbook, mNav);
            } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
                JournalsDao.getInstance().createOrUpdateJounarls(mEbook, mNav);
            } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)) {
                HotBookDao.getInstance().createOrUpdateHotBook(mEbook, mNav);
            } else if (control.equals(REQUEST_CONTROL_TYPE_LIB_NEWS)) {
                NewsDao.getInstance().createOrUpdateNews(mEbook);
            } else if (control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
                HotBookDao.getInstance().createOrUpdateHotBook(mEbook, mNav);
            }
        }
        return null;
    }
}
