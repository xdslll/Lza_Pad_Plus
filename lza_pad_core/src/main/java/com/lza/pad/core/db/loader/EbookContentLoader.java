package com.lza.pad.core.db.loader;

import android.content.Context;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.EbookContentStrategy;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-18.
 */
public class EbookContentLoader extends AbstractLoader<List<EbookContent>> {

    private Ebook mEbook;
    private NavigationInfo mNav;

    public EbookContentLoader(Context context, Ebook ebook, NavigationInfo nav) {
        super(context);
        this.mEbook = ebook;
        this.mNav = nav;
    }

    @Override
    public List<EbookContent> loadInBackground() {

        /*if (mEbook != null) {
            int bookId= mEbook.getBookId();
            return EbookContentDao.getInstance().queryContentByBookId(bookId);
        }*/
        EbookContentStrategy strategy = new EbookContentStrategy(mNav, mEbook);
        List<EbookContent> contents = strategy.operation();
        return contents;
    }
}
