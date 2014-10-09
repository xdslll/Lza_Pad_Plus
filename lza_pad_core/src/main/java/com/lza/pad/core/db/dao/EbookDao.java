package com.lza.pad.core.db.dao;

import android.text.TextUtils;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.debug.AppLogger;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 数据库操作类(EbookDao)
 *
 * @author xiads
 * @Date 14-9-12.
 */
public class EbookDao extends BaseDao<Ebook, Integer> {

    private static EbookDao sEbookDao;

    public EbookDao() {
        super(Ebook.class);
    }

    public static EbookDao getInstance() {
        if (sEbookDao == null) {
            sEbookDao = new EbookDao();
        }
        return sEbookDao;
    }

    public long countOfByType(NavigationInfo ni) {
        return countOfByType(ni.getApiControlPar());
    }

    public long countOfByType(String type) {
        createQueryAndWhere();
        whereByType(type);
        return countOfCondition();
    }

    public List<Ebook> queryByType(String type) {
        createQueryAndWhere();
        whereByType(type);
        orderById();
        return queryForCondition();
    }

    public List<Ebook> queryByType(NavigationInfo ni) {
        createQueryAndWhere();
        String type = ni.getApiControlPar();
        long pagesize = ni.getApiPageSizePar();
        long page = ni.getApiPagePar();
        long offset = (page - 1) * pagesize;
        try {
            mWhere.eq(Ebook._TYPE, type);
            mQuery.offset(offset).limit(pagesize)
                    .orderBy(Ebook._PX, true).orderBy(Ebook._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ebook> queryOnePage(NavigationInfo ni) {
        createQueryAndWhere();
        long pagesize = ni.getApiPageSizePar();
        long page = ni.getApiPagePar();
        long offset = (page - 1) * pagesize;
        String type = ni.getApiControlPar();
        try {
            mWhere.eq(Ebook._TYPE, type);
            mQuery.offset(offset).limit(pagesize)
                    .orderBy(Ebook._PX, true).orderBy(Ebook._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ebook> queryByTypeAndSize(NavigationInfo ni) {
        String type = ni.getApiControlPar();
        long pagesize = ni.getApiPageSizePar();
        long page = ni.getApiPagePar();
        long offset = (page - 1) * pagesize;
        createQueryAndWhere();
        try {
            mWhere.eq(Ebook._TYPE, type);
            mQuery.offset(offset).limit(pagesize).orderBy(Ebook._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void whereByType(String type) {
        try {
            mWhere.eq(Ebook._TYPE, type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void orderById() {
        mQuery.orderBy(Ebook._ID, true);
    }

    public Ebook queryByBookId(int bookId) {
        createQueryAndWhere();
        try {
            mWhere.eq(Ebook._BOOK_ID, bookId);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createOrUpdateEbook(Ebook data, NavigationInfo navInfo) {
        if (data != null) {

            //获取图片缓存的目录并清空
            File imgCacheDir = RuntimeUtility.createImgCacheDirAndClear(navInfo);
            AppLogger.e("imgCacheDir-->" + imgCacheDir.getAbsolutePath());

            //更新时间
            long date = new Date().getTime();
            data.setUpdateDate(date);
            //更新次数
            //Ebook oldData = queryForId(data.getId());
            Ebook oldData = queryByBookId(data.getBookId());
            if (oldData != null) {
                int updateCount = oldData.getUpdateCount();
                updateCount++;
                data.setUpdateCount(updateCount);
            }
            //获取图片Url
            String imgUrl = data.getImgUrl();
            if (!TextUtils.isEmpty(imgUrl)) {
                //获取图片名称
                String imgCacheFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1).trim();
                File imgCacheFile = new File(imgCacheDir, imgCacheFileName);
                AppLogger.e("imgCacheFile-->" + imgCacheFile);

                //更新图片路径
                data.setImgPath(imgCacheFile.getAbsolutePath());
            }
            //设置类型
            String type = navInfo.getApiControlPar();
            data.setType(type);

            try {
                if (oldData != null) {
                    if (data.getId() == oldData.getId()) {
                        return mDao.update(data);
                    }
                }
                return mDao.create(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void createNewDatas(Collection<Ebook> datas, NavigationInfo navInfo) {
        if (datas != null) {

            //获取图片缓存的目录并清空
            File imgCacheDir = RuntimeUtility.createImgCacheDirAndClear(navInfo);
            AppLogger.e("imgCacheDir-->" + imgCacheDir.getAbsolutePath());

            for (Ebook data : datas) {
                //更新时间
                long date = new Date().getTime();
                data.setUpdateDate(date);
                //更新次数
                Ebook oldData = queryForId(data.getId());
                if (oldData != null) {
                    int updateCount = oldData.getUpdateCount();
                    updateCount++;
                    data.setUpdateCount(updateCount);
                }
                //获取图片Url
                String imgUrl = data.getImgUrl();
                if (!TextUtils.isEmpty(imgUrl)) {
                    //获取图片名称
                    String imgCacheFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1).trim();
                    File imgCacheFile = new File(imgCacheDir, imgCacheFileName);
                    AppLogger.e("imgCacheFile-->" + imgCacheFile);

                    //更新图片路径
                    data.setImgPath(imgCacheFile.getAbsolutePath());
                }
                //设置类型
                String type = navInfo.getApiControlPar();
                //String type = NavigationInfoDao.getInstance().queryApiControlPar(navInfo, ApiParams.API_TYPE_LIST);
                data.setType(type);
                try {
                    mDao.createOrUpdate(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
