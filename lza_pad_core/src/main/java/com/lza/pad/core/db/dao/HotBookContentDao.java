package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.model.HotBookContent;

import java.sql.SQLException;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-20.
 */
public class HotBookContentDao extends BaseDao<HotBookContent, Integer> {

    private static HotBookContentDao sHotbookContentDao;

    public HotBookContentDao() {
        super(HotBookContent.class);
    }

    public static HotBookContentDao getInstance() {
        if (sHotbookContentDao == null) {
            sHotbookContentDao = new HotBookContentDao();
        }
        return sHotbookContentDao;
    }

    /*public HotBookContent queryByHotbookMarcNo(String url) {
        createQueryAndWhere();
        try {
            mWhere.eq(HotBookContent._MARC_NO, url);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public HotBookContent queryByHotbookMarcNo(String url) {
        createQueryAndWhere();
        try {
            mWhere.eq(HotBookContent._MARC_NO, url);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewData(HotBookContent data) {
        String url = data.getMarc_no();
        HotBookContent oldData = queryByHotbookMarcNo(url);
        try {
            if (oldData != null) {
                mDao.update(data);
            }else {
                mDao.create(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
