package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.EbookContent;
import com.lza.pad.core.db.model.HotBookContent;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class HotbookToEbookContentAdapter implements DataAdapter<HotBookContent, EbookContent> {

    @Override
    public EbookContent apdater(HotBookContent oldData) {
        EbookContent ebookContent = new EbookContent();
        ebookContent.setTitle(oldData.getTitle());
        ebookContent.setAuthor(oldData.getAuthor());
        ebookContent.setPubisher(oldData.getPubisher());
        ebookContent.setCnt1(oldData.getCnt1());
        ebookContent.setCnt2(oldData.getCnt2());
        ebookContent.setMarc_no(oldData.getMarc_no());

        return ebookContent;
    }
}
