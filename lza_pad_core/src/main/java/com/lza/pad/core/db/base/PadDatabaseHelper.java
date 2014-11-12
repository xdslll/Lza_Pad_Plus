package com.lza.pad.core.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lza.pad.core.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.SubjectType;
import com.lza.pad.lib.support.db.AbstractDatabaseHelper;

import java.sql.SQLException;

/**
 * Created by xiads on 14-9-7.
 */
public class PadDatabaseHelper extends AbstractDatabaseHelper {

    private Context mContext;
    public PadDatabaseHelper(Context c) {
        super(c, DatabaseInfo.DB_NAME,
                    DatabaseInfo.DB_VERSION, R.raw.ormlite_config_v1);
        mContext = c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            try {
                TableUtils.createTable(connectionSource, SubjectType.class);
                TableUtils.dropTable(connectionSource, NavigationInfo.class, true);
                TableUtils.createTable(connectionSource, NavigationInfo.class);
                NavigationInfoDao.getInstance().initNavigationData(mContext);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
