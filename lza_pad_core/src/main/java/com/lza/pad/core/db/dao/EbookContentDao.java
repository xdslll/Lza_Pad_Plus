package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.model.EbookContent;

import java.sql.SQLException;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-17.
 */
public class EbookContentDao extends BaseDao<EbookContent, Integer> {

    private static EbookContentDao sEbookContentDao;

    public EbookContentDao() {
        super(EbookContent.class);
    }

    public static EbookContentDao getInstance() {
        if (sEbookContentDao == null) {
            sEbookContentDao = new EbookContentDao();
        }
        return sEbookContentDao;
    }

    public List<EbookContent> queryContentByBookId(int bookId) {
        createQueryAndWhere();
        try {
            mWhere.eq(EbookContent._BOOK_ID, bookId);
            mQuery.orderBy(EbookContent._ID, true);
            return queryForCondition();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createOrUpdateEbookContent(EbookContent data) {
        createQueryAndWhere();
        try {
            mWhere.eq(EbookContent._ID, data.getId());
            EbookContent oldData = queryForFirst();
            if (oldData != null && oldData.equals(data)) {
                updateData(data);
            } else {
                createNewData(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
