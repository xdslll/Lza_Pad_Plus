package com.lza.pad.core.db.dao;

import android.text.TextUtils;

import com.lza.pad.core.db.adapter.HotbookAdapter;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.HotBook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.debug.AppLogger;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-20.
 */
public class HotBookDao extends BaseDao<HotBook, Integer> {

    private static HotBookDao sHotBookDao;

    public HotBookDao() {
        super(HotBook.class);
    }

    public static HotBookDao getInstance() {
        if (sHotBookDao == null) {
            sHotBookDao = new HotBookDao();
        }
        return sHotBookDao;
    }

    public long countOfByType(NavigationInfo ni) {
        return countOfByType(ni.getApiControlPar());
    }

    public long countOfByType(String type) {
        createQueryAndWhere();
        try {
            mWhere.eq(HotBook._TYPE, type);
            return countOfCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<HotBook> queryOnePage(NavigationInfo ni) {
        createQueryAndWhere();
        long pagesize = ni.getApiPageSizePar();
        long page = ni.getApiPagePar();
        long offset = (page - 1) * pagesize;
        String type = ni.getApiControlPar();
        try {
            mWhere.eq(HotBook._TYPE, type);
            mQuery.offset(offset).limit(pagesize)
                    .orderBy(HotBook._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HotBook queryById(int id) {
        createQueryAndWhere();
        try {
            mWhere.eq(HotBook._ID, id);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createOrUpdateHotBook(Ebook data, NavigationInfo navInfo) {
        if (data != null) {

            HotbookAdapter adapter = new HotbookAdapter();
            HotBook hotBook = adapter.apdater(data);

            //获取图片缓存的目录并清空
            File imgCacheDir = RuntimeUtility.createImgCacheDirAndClear(navInfo);
            AppLogger.e("imgCacheDir-->" + imgCacheDir.getAbsolutePath());

            //更新时间
            long date = new Date().getTime();
            hotBook.setUpdateDate(date);
            //更新次数
            HotBook oldData = queryById(hotBook.getId());
            if (oldData != null) {
                int updateCount = oldData.getUpdateCount();
                updateCount++;
                hotBook.setUpdateCount(updateCount);
            }
            //获取图片Url
            String imgUrl = hotBook.getSmallImg();
            if (!TextUtils.isEmpty(imgUrl)) {
                //获取图片名称
                String imgCacheFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1).trim();
                File imgCacheFile = new File(imgCacheDir, imgCacheFileName);
                AppLogger.e("imgCacheFile-->" + imgCacheFile);

                //更新图片路径
                hotBook.setImgPath(imgCacheFile.getAbsolutePath());
                /**
                 * 注意：关键步骤
                 */
                data.setImgPath(imgCacheFile.getAbsolutePath());
            }
            //设置类型
            String type = navInfo.getApiControlPar();
            hotBook.setType(type);

            try {
                if (oldData != null) {
                    /*if (hotBook.getId() == oldData.getId()) {
                        return mDao.update(hotBook);
                    }*/
                    if (hotBook.equals(oldData)) {
                        return mDao.update(hotBook);
                    }
                }
                return mDao.create(hotBook);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
