package com.lza.pad.ui.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.strategy.BaseStrategy;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class EbookContentAdapterStrategy extends BaseStrategy<BaseAdapter> {

    private Context mContext;
    private Ebook mEbook;
    private List<EbookContent> mEbookContents;
    public EbookContentAdapterStrategy(NavigationInfo nav,
           Context context, Ebook ebook, List<EbookContent> ebookContents) {
        super(nav);
        this.mContext = context;
        this.mEbook = ebook;
        this.mEbookContents = ebookContents;
    }

    @Override
    public BaseAdapter operation() {
        String control = mNav.getApiControlPar();
        if (control.equals(REQUEST_CONTROL_TYPE_EBOOK)
                || control.equals(REQUEST_CONTROL_TYPE_EBOOK_JC)) {
            return new EbookContentAdapter(mContext, mEbook, mEbookContents);
        } else if (control.equals(REQUEST_CONTROL_TYPE_QK_MESSAGE)) {
            return new JournalsContentAdapter(mContext, mEbook, mEbookContents);
        } else if (control.equals(REQUEST_CONTROL_TYPE_HOT_BOOK)
                || control.equals(REQUEST_CONTROL_TYPE_NEW_BOOK)
                || control.equals(HOT_BOOK_CONTROL_CONTENT)) {
            return new HotBookContentAdapter(mContext, mEbook, mEbookContents);
        }
        return null;
    }
}
