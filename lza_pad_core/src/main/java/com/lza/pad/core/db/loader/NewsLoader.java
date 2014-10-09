package com.lza.pad.core.db.loader;

import android.content.Context;

import com.lza.pad.core.db.dao.NewsDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.News;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsLoader extends AbstractLoader<List<News>> {

    private NavigationInfo mNav;

    public NewsLoader(Context context, NavigationInfo nav) {
        super(context);
        this.mNav = nav;
    }

    @Override
    public List<News> loadInBackground() {
        List<News> news = NewsDao.getInstance().queryByType(mNav.getApiTypePar());
        //List<News> news = NewsDao.getInstance().queryForAllOrderById();
        return news;
    }
}
