package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.adapter.SubjectTypeAdapter;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.SubjectType;

import java.sql.SQLException;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class SubjectTypeDao extends BaseDao<SubjectType, Integer> {

    private static SubjectTypeDao sNewsDao;

    public SubjectTypeDao() {
        super(SubjectType.class);
    }

    public static SubjectTypeDao getInstance() {
        if (sNewsDao == null) {
            sNewsDao = new SubjectTypeDao();
        }
        return sNewsDao;
    }

    public List<SubjectType> queryForAllOrderById() {
        createQueryBuilder();
        mQuery.orderBy(SubjectType._PX, true);
        return queryForCondition();
    }

    public List<SubjectType> queryForNotClosed() {
        createQueryAndWhere();
        try {
            mWhere.eq(SubjectType._IF_SHOW, SubjectType.IS_SHOW);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mQuery.orderBy(SubjectType._PX, true);
        return queryForCondition();
    }

    public SubjectType queryById(int id) {
        createQueryAndWhere();
        try {
            mWhere.eq(SubjectType._ID, id);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createOrUpdateNews(Ebook data) {
        if (data != null) {
            SubjectTypeAdapter adapter = new SubjectTypeAdapter();
            SubjectType newData = adapter.apdater(data);

            if (newData != null) {
                //更新次数
                SubjectType oldData = queryById(Integer.parseInt(newData.getId()));
                try {
                    if (oldData != null) {
                        if (newData.equals(oldData)) {
                            return mDao.update(newData);
                        }
                    }
                    //默认显示学科
                    newData.setIfShow(1);
                    return mDao.create(newData);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public void createOrUpdateNews(List<Ebook> datas) {
        if (datas != null && datas.size() > 0) {
            for (Ebook data : datas) {
                createOrUpdateNews(data);
            }
        }
    }
}
