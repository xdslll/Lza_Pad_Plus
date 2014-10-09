package com.lza.pad.core.db.dao;

import com.lza.pad.core.db.model.EbookRequest;

import java.util.Date;

/**
 * 数据库操作类(EbookRequest)
 *
 * @author xiads
 * @Date 14-9-12.
 */
public class EbookRequestDao extends BaseDao<EbookRequest, Integer> {

    private static EbookRequestDao sEbookRequestDao;

    public EbookRequestDao() {
        super(EbookRequest.class);
    }

    public static EbookRequestDao getInstance() {
        if (sEbookRequestDao == null) {
            sEbookRequestDao = new EbookRequestDao();
        }
        return sEbookRequestDao;
    }

    @Override
    public void createNewData(EbookRequest data) {
        long dateTime = new Date().getTime();
        data.setDate(dateTime);
        super.createNewData(data);
    }
}
