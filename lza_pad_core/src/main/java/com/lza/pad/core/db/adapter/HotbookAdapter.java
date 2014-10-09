package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.HotBook;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-20.
 */
public class HotbookAdapter implements DataAdapter<Ebook,HotBook> {

    @Override
    public HotBook apdater(Ebook oldData) {
        HotBook hotBook = new HotBook();

        hotBook.setId(oldData.getId());
        hotBook.setTitle(oldData.getTitle());
        hotBook.setAuthor(oldData.getAuthor());
        hotBook.setAuthorC(oldData.getAuthorC());
        hotBook.setBigImg(oldData.getBigImg());
        hotBook.setSmallImg(oldData.getSmallImg());
        hotBook.setSchool_id(oldData.getSchool_id());
        hotBook.setContent(oldData.getContent());
        hotBook.setIsbn(oldData.getIsbn());
        hotBook.setMr(oldData.getMr());
        hotBook.setPubdate(oldData.getPubdate());
        hotBook.setPublisher(oldData.getPublisher());
        hotBook.setUrl(oldData.getUrl());
        hotBook.setXk(oldData.getXk());
        hotBook.setImgPath(oldData.getImgPath());

        return hotBook;
    }
}
