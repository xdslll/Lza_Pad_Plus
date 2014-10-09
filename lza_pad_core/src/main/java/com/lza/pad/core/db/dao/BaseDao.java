package com.lza.pad.core.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.lza.pad.core.db.base.PadDatabaseHelper;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-10.
 */
public abstract class BaseDao<T, ID> {

    protected Dao<T, ID> mDao;
    protected PadDatabaseHelper mDatabaseHelper;
    protected QueryBuilder<T, ID> mQuery = null;
    protected Where<T, ID> mWhere = null;
    protected UpdateBuilder<T, ID> mUpdate = null;

    public BaseDao(Class<T> classData) {
        mDatabaseHelper = GlobalContext.getDatabaseHelper();
        try {
            mDao = mDatabaseHelper.getDao(classData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<T, ID> getDao() {
        return mDao;
    }

    public void createNewDatas(Collection<T> datas) {
        if (datas != null) {
            for (T data : datas) {
                try {
                    mDao.createIfNotExists(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createNewData(T data) {
        if (mDao != null) {
            try {
                mDao.createIfNotExists(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int updateData(T data) {
        if (mDao != null) {
            try {
                return mDao.update(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public long count() {
        try {
            return mDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long countOfCondition() {
        if (mQuery != null) {
            try {
                mQuery.setCountOf(true);
                return countOfCondition(mQuery.prepare());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public long countOfCondition(PreparedQuery<T> prepare) {
        if (prepare != null) {
            AppLogger.e(prepare.toString());
            try {
                return mDao.countOf(prepare);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public T queryForId(ID id) {
        try {
            return mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> queryForAll() {
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> queryForCondition(QueryBuilder<T, ID> queryBuilder) {
        try {
            PreparedQuery<T> prepare = queryBuilder.prepare();
            return queryForCondition(prepare);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> queryForCondition(PreparedQuery<T> condition) {
        if (condition != null) {
            AppLogger.e(condition.toString());
            try {
                return mDao.query(condition);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<T> queryForCondition() {
        if (mQuery != null) {
            try {
                return queryForCondition(mQuery.prepare());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public T queryForFirst() {
        if (mQuery != null) {
            try {
                return mDao.queryForFirst(mQuery.prepare());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void clearByRaw(String table_name) {
        String sql = "DELETE FROM " + table_name;
        try {
            mDao.queryRawValue(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long clear() {
        List<T> datas = queryForAll();
        if (datas != null && datas.size() > 0) {
            try {
                return mDao.delete(datas);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int updateByBuidler() {
        try {
            if (mUpdate != null) {
                return mDao.update(mUpdate.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateByValue(String colName, Object value) {
        createUpdateBuidler();
        try {
            mUpdate.updateColumnValue(colName, value);
            return updateByBuidler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateById(String colName, String colValue, String idColName, ID id) {
        createUpdateBuidler();
        try {
            mUpdate.updateColumnValue(colName, colValue).where().eq(idColName, id);
            return updateByBuidler();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected void createQueryBuilder() {
        mQuery = mDao.queryBuilder();
    }

    protected void createQueryAndWhere() {
        createQueryBuilder();
        if (mQuery != null)
            mWhere = mQuery.where();
    }

    protected void createUpdateBuidler() {
        mUpdate = mDao.updateBuilder();
    }

}
