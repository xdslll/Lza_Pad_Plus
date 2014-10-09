package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.News;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsToEbookAdapter implements DataAdapter<News, Ebook> {

    @Override
    public Ebook apdater(News oldData) {
        Ebook ebook = new Ebook();

        ebook.setId(oldData.getId());
        ebook.setAuthor(oldData.getAuthor());
        ebook.setPubdate(oldData.getPubdate());
        ebook.setType(oldData.getType());
        ebook.setUrl(oldData.getUrl());
        ebook.setSchool_id(oldData.getSchool_id());
        ebook.setContent(oldData.getContent());
        ebook.setImg1(oldData.getImg1());
        ebook.setImg2(oldData.getImg2());
        ebook.setImg3(oldData.getImg3());

        return ebook;
    }
}
