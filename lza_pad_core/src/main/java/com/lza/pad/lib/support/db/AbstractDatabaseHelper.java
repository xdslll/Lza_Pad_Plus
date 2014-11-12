package com.lza.pad.lib.support.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.lza.pad.lib.support.debug.AppLogger;

/**
 * Created by xiads on 14-9-1.
 */
public abstract class AbstractDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;
    private int mResId;

    public AbstractDatabaseHelper(Context context, String db_name, int db_version) {
        super(context, db_name, null, db_version);
        mContext = context;
    }

    public AbstractDatabaseHelper(Context context, String db_name, int db_version, int config_res) {
        super(context, db_name, null, db_version, config_res);
        mContext = context;
        mResId = config_res;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        AppLogger.e("DatabaseHelper-->onCreate");
        DatabaseTools.createTables(mContext, mResId, connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        AppLogger.e("DatabaseHelper-->onUpgrade");
        /*if (newVersion > oldVersion) {
            DatabaseTools.dropTables(mContext, mResId, connectionSource);
        }*/
    }
}
