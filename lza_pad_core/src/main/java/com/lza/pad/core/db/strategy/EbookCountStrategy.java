package com.lza.pad.core.db.strategy;

import com.lza.pad.core.db.dao.EbookDao;
import com.lza.pad.core.db.dao.HotBookDao;
import com.lza.pad.core.db.dao.JournalsDao;
import com.lza.pad.core.db.model.NavigationInfo;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class EbookCountStrategy extends BaseStrategy<Long> {

    public EbookCountStrategy(NavigationInfo nav) {
        super(nav);
    }

    @Override
    public Long operation() {
        Long count = new Long(0);
        String control = mNav.getApiControlPar();
        if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
            count = EbookDao.getInstance().countOfByType(mNav);
        } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
            count = JournalsDao.getInstance().countOfByType(mNav);
        }else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)
                || control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)) {
            count = HotBookDao.getInstance().countOfByType(mNav);
        }
        return count;
    }
}
