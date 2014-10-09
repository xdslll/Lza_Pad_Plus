package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.model.JournalsContent;

import java.sql.SQLException;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class JournalsContentDao extends BaseDao<JournalsContent, Integer> {

    private static JournalsContentDao sJournalsContentDao;

    public JournalsContentDao() {
        super(JournalsContent.class);
    }

    public static JournalsContentDao getInstance() {
        if (sJournalsContentDao == null) {
            sJournalsContentDao = new JournalsContentDao();
        }
        return sJournalsContentDao;
    }

    public List<JournalsContent> queryContentByQkId(int qkId) {
        createQueryAndWhere();
        try {
            mWhere.eq(JournalsContent._QK_ID, qkId);
            mQuery.orderBy(JournalsContent._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JournalsContent queryByQkIdAndId(int qkId, int id) {
        createQueryAndWhere();
        try {
            mWhere.eq(JournalsContent._QK_ID, qkId)
                    .and().eq(JournalsContent._ID, id);
            return queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createNewData(JournalsContent data) {
        int qkId = data.getQkId();
        int id = data.getId();
        JournalsContent oldData = queryByQkIdAndId(qkId, id);
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
