package com.lza.pad.core.db.adapter;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.News;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsAdapter implements DataAdapter<Ebook, News> {

    @Override
    public News apdater(Ebook oldData) {
        News news = new News();

        news.setId(oldData.getId());
        news.setAuthor(oldData.getAuthor());
        news.setPubdate(oldData.getPubdate());
        news.setType(oldData.getType());
        news.setUrl(oldData.getUrl());
        news.setSchool_id(oldData.getSchool_id());
        news.setContent(oldData.getContent());
        news.setImg1(oldData.getImg1());
        news.setImg2(oldData.getImg2());
        news.setImg3(oldData.getImg3());
        news.setTitle(oldData.getTitle());

        return news;
    }
}
