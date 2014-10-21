package com.lza.pad.core.db.strategy;

import com.lza.pad.core.db.adapter.HotbookToEbookContentAdapter;
import com.lza.pad.core.db.adapter.JournalsToEbookContentAdapter;
import com.lza.pad.core.db.dao.EbookContentDao;
import com.lza.pad.core.db.dao.HotBookContentDao;
import com.lza.pad.core.db.dao.JournalsContentDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.HotBookContent;
import com.lza.pad.core.db.model.JournalsContent;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.RuntimeUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class EbookContentStrategy extends BaseStrategy<List<EbookContent>> {

    private Ebook mEbook;
    public EbookContentStrategy(NavigationInfo nav, Ebook ebook) {
        super(nav);
        mEbook = ebook;
    }

    @Override
    public List<EbookContent> operation() {
        String control = mNav.getApiControlPar();
        if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
            if (mEbook != null) {
                int bookId= mEbook.getBookId();
                return EbookContentDao.getInstance().queryContentByBookId(bookId);
            }
        } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
            if (mEbook != null) {
                //获取期刊id
                int qkId = mEbook.getId();
                List<EbookContent> newContents = new ArrayList<EbookContent>();
                JournalsToEbookContentAdapter adapter = new JournalsToEbookContentAdapter();
                List<JournalsContent> orgContents = JournalsContentDao.getInstance().queryContentByQkId(qkId);
                for (JournalsContent content : orgContents) {
                    newContents.add(adapter.apdater(content));
                }
                return newContents;
            }
        } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)
                || control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)
                || control.equals(HOT_BOOK_CONTROL_CONTENT)) {
            if (mEbook != null) {
                //String marcNo = mEbook.getUrl();
                String marcNo = RuntimeUtility.getMarNoFromEbook(mEbook);
                        List<EbookContent> newContents = new ArrayList<EbookContent>();
                HotBookContent orgContent = HotBookContentDao.getInstance().queryByHotbookMarcNo(marcNo);
                if (orgContent != null) {
                    HotbookToEbookContentAdapter adapter = new HotbookToEbookContentAdapter();
                    EbookContent ebookContent = adapter.apdater(orgContent);
                    newContents.add(ebookContent);
                }
                return newContents;
            }
        }
        return null;
    }


}
