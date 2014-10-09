package com.lza.pad.core.db.loader;

import android.content.Context;

import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class NavigationLoader extends AbstractLoader<List<NavigationInfo>> {

    public NavigationLoader(Context context) {
        super(context);
    }

    @Override
    public List<NavigationInfo> loadInBackground() {
        NavigationInfoDao infoDao = NavigationInfoDao.getInstance();
        //读取所有打开的模块
        List<NavigationInfo> info = infoDao.queryNotClosed();
        return info;
    }

}
