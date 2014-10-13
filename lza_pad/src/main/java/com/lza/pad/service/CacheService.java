package com.lza.pad.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.lib.support.debug.AppLogger;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/13/14.
 */
public class CacheService extends Service {

    public class CacheData extends ICacheData.Stub {

        private Service mService;

        public CacheData(Service service) {
            this.mService = service;
        }

        @Override
        public List<Ebook> requestEbook(NavigationInfo nav) throws RemoteException {
            EbookRequestService request = new EbookRequestService(mService.getApplication());
            request.execute();
            AppLogger.e(nav.getName() + " --> AIDL GET IT!!!!!!!!!!!!!!!!!!!!!!");
            NavigationInfo otherNav = NavigationInfoDao.getInstance().queryNotClosedBySortId(5);
            AppLogger.e(otherNav.getName() + " --> AIDL GET IT!!!!!!!!!!!!!!!!!!!!!!");

            return null;
        }
    }

    private CacheData mBinder = new CacheData(this);

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;

    }
}
