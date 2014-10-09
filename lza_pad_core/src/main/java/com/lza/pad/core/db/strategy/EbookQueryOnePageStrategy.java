package com.lza.pad.core.db.strategy;

import com.lza.pad.core.db.adapter.HotbookToEbookAdapter;
import com.lza.pad.core.db.adapter.JournalsToEbookAdapter;
import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.HotBookDao;
import com.lza.pad.core.db.dao.JournalsDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.HotBook;
import com.lza.pad.core.db.model.Journals;
import com.lza.pad.core.db.model.NavigationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class EbookQueryOnePageStrategy extends BaseStrategy<List<Ebook>> {

    List<Ebook> mEbooks = null;

    public EbookQueryOnePageStrategy(NavigationInfo nav) {
        super(nav);
    }

    @Override
    public List<Ebook> operation() {
        if (mNav != null) {
            String control = mNav.getApiControlPar();
            if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                    || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
                mEbooks = EbookDao.getInstance().queryOnePage(mNav);
            } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
                List<Journals> journals = JournalsDao.getInstance().queryOnePage(mNav);
                mEbooks = new ArrayList<Ebook>();
                JournalsToEbookAdapter ebookAdapter = new JournalsToEbookAdapter();
                for (Journals jrl : journals) {
                    mEbooks.add(ebookAdapter.apdater(jrl));
                }
            } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)
                    || control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
                List<HotBook> hotBooks = HotBookDao.getInstance().queryOnePage(mNav);
                mEbooks = new ArrayList<Ebook>();
                HotbookToEbookAdapter ebookAdapter = new HotbookToEbookAdapter();
                for (HotBook hotbook : hotBooks) {
                    mEbooks.add(ebookAdapter.apdater(hotbook));
                }
            }
        }
        return mEbooks;
    }
}
