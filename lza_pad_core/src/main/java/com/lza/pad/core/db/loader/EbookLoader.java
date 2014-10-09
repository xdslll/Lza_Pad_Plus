package com.lza.pad.core.db.loader;

import android.content.Context;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.EbookQueryOnePageStrategy;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-18.
 */
public class EbookLoader extends AbstractLoader<List<Ebook>> {

    private NavigationInfo mNav;

    public EbookLoader(Context context, NavigationInfo nav) {
        super(context);
        mNav = nav;
    }

    public void setNavigationinfo(NavigationInfo nav) {
        this.mNav = nav;
    }

    @Override
    public List<Ebook> loadInBackground() {
        //List<Ebook> ebooks = EbookDao.getInstance().queryByType(mNav);
        EbookQueryOnePageStrategy strategy = new EbookQueryOnePageStrategy(mNav);
        return strategy.operation();
    }
}