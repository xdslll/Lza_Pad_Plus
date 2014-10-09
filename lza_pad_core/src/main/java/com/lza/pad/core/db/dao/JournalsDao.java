package com.lza.pad.core.db.dao;

import android.text.TextUtils;

import com.lza.pad.core.db.adapter.JournalsAdapter;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.Journals;
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
 * @Date 14-9-17.
 */
public class JournalsDao extends BaseDao<Journals, Integer> {

    private static JournalsDao sJournalsDao;

    public JournalsDao() {
        super(Journals.class);
    }

    public static JournalsDao getInstance() {
        if (sJournalsDao == null) {
            sJournalsDao = new JournalsDao();
        }
        return sJournalsDao;
    }

    public long countOfByType(NavigationInfo ni) {
        return countOfByType(ni.getApiControlPar());
    }

    public long countOfByType(String type) {
        createQueryAndWhere();
        try {
            mWhere.eq(Journals._TYPE, type);
            return countOfCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Journals> queryOnePage(NavigationInfo ni) {
        createQueryAndWhere();
        long pagesize = ni.getApiPageSizePar();
        long page = ni.getApiPagePar();
        long offset = (page - 1) * pagesize;
        String type = ni.getApiControlPar();
        try {
            mWhere.eq(Journals._TYPE, type);
            mQuery.offset(offset).limit(pagesize)
                    .orderBy(Journals._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Journals queryByQkId(int qkId) {
        createQueryAndWhere();
        try {
            mWhere.eq(Journals._ID, qkId);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createOrUpdateJounarls(Ebook data, NavigationInfo navInfo) {
        if (data != null) {

            JournalsAdapter adapter = new JournalsAdapter();
            Journals journals = adapter.apdater(data);

            //获取图片缓存的目录并清空
            File imgCacheDir = RuntimeUtility.createImgCacheDirAndClear(navInfo);
            AppLogger.e("imgCacheDir-->" + imgCacheDir.getAbsolutePath());

            //更新时间
            long date = new Date().getTime();
            data.setUpdateDate(date);

            //更新次数
            //Journals oldData = queryForId(journals.getId());
            Journals oldData = queryByQkId(journals.getId());
            if (oldData != null) {
                int updateCount = oldData.getUpdateCount();
                updateCount++;
                data.setUpdateCount(updateCount);
            }
            //获取图片Url
            String imgUrl = journals.getQkImg();
            if (!TextUtils.isEmpty(imgUrl)) {
                //获取图片名称
                String imgCacheFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1).trim();
                File imgCacheFile = new File(imgCacheDir, imgCacheFileName);
                AppLogger.e("imgCacheFile-->" + imgCacheFile);

                //更新图片路径
                journals.setQkImgPath(imgCacheFile.getAbsolutePath());
                /**
                 * 关键步骤
                 * 同时将路径回传给Ebook对象，便于Ebook对象进行传递调用
                 */
                data.setQkImgPath(imgCacheFile.getAbsolutePath());
            }
            //设置类型
            String type = navInfo.getApiControlPar();
            journals.setType(type);

            try {
                if (oldData != null) {
                    if (journals.getId() == oldData.getId()) {
                        return mDao.update(journals);
                    }
                }
                return mDao.create(journals);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
