package com.lza.pad.core.db.base;


import com.lza.pad.core.R;
import com.lza.pad.core.utils.GlobalContext;

/**
 * Created by xiads on 14-9-1.
 */
public class DatabaseInfo {

    /**
     * 数据库名称
     */
    public static final String DB_NAME = GlobalContext.getInstance().getString(R.string.db_name);

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = GlobalContext.getInstance().getInteger(R.string.db_version);

}
