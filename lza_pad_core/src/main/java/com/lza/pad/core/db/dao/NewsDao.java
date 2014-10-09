package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.adapter.NewsAdapter;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.News;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsDao extends BaseDao<News, Integer> {

    private static NewsDao sNewsDao;

    public NewsDao() {
        super(News.class);
    }

    public static NewsDao getInstance() {
        if (sNewsDao == null) {
            sNewsDao = new NewsDao();
        }
        return sNewsDao;
    }

    public List<News> queryForAllOrderById() {
        createQueryBuilder();
        mQuery.orderBy(News._ID, false);
        return queryForCondition();
    }

    public List<News> queryByType(int type) {
        createQueryAndWhere();
        try {
            mWhere.eq(News._TYPE, String.valueOf(type));
            mQuery.orderBy(News._ID, false);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public News queryById(int id) {
        createQueryAndWhere();
        try {
            mWhere.eq(News._ID, id);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createOrUpdateNews(Ebook data) {
        if (data != null) {

            NewsAdapter adapter = new NewsAdapter();
            News news = adapter.apdater(data);

            //获取图片缓存的目录并清空
            //File imgCacheDir = RuntimeUtility.createImgCacheDirAndClear(navInfo);
            //AppLogger.e("imgCacheDir-->" + imgCacheDir.getAbsolutePath());

            //更新时间
            long date = new Date().getTime();
            news.setUpdateDate(date);
            //更新次数
            News oldData = queryById(news.getId());
            if (oldData != null) {
                int updateCount = oldData.getUpdateCount();
                updateCount++;
                news.setUpdateCount(updateCount);
            }
            //获取图片Url
            /*String imgUrl = news.getSmallImg();
            if (!TextUtils.isEmpty(imgUrl)) {
                //获取图片名称
                String imgCacheFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1).trim();
                File imgCacheFile = new File(imgCacheDir, imgCacheFileName);
                AppLogger.e("imgCacheFile-->" + imgCacheFile);

                //更新图片路径
                news.setImgPath(imgCacheFile.getAbsolutePath());
                data.setImgPath(imgCacheFile.getAbsolutePath());
            }*/
            //设置类型
            //String type = navInfo.getApiControlPar();
            //news.setType(type);

            try {
                if (oldData != null) {
                    if (news.getId() == oldData.getId()) {
                        return mDao.update(news);
                    }
                }
                return mDao.create(news);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
