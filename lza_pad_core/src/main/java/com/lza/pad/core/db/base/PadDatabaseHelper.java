package com.lza.pad.core.db.base;

import android.content.Context;

import com.lza.pad.core.R;
import com.lza.pad.lib.support.db.AbstractDatabaseHelper;

/**
 * Created by xiads on 14-9-7.
 */
public class PadDatabaseHelper extends AbstractDatabaseHelper {

    public PadDatabaseHelper(Context c) {
        super(c, DatabaseInfo.DB_NAME,
                    DatabaseInfo.DB_VERSION, R.raw.ormlite_config_v1);
    }
}
