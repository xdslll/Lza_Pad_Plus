package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.HotBook;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-20.
 */
public class HotbookToEbookAdapter implements DataAdapter<HotBook, Ebook> {

    @Override
    public Ebook apdater(HotBook oldData) {
        Ebook ebook = new Ebook();

        ebook.setId(oldData.getId());
        ebook.setTitle(oldData.getTitle());
        ebook.setAuthor(oldData.getAuthor());
        ebook.setAuthorC(oldData.getAuthorC());
        ebook.setBigImg(oldData.getBigImg());
        ebook.setSmallImg(oldData.getSmallImg());
        ebook.setSchool_id(oldData.getSchool_id());
        ebook.setContent(oldData.getContent());
        ebook.setIsbn(oldData.getIsbn());
        ebook.setMr(oldData.getMr());
        ebook.setPubdate(oldData.getPubdate());
        ebook.setPublisher(oldData.getPublisher());
        ebook.setUrl(oldData.getUrl());
        ebook.setXk(oldData.getXk());
        ebook.setImgPath(oldData.getImgPath());

        return ebook;
    }
}
