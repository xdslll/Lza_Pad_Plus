package com.lza.pad.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.lza.pad.core.utils.Consts;
import com.lza.pad.lib.support.debug.AppLogger;

import java.util.ArrayList;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class CacheDataService extends Service implements Consts {

    public static final int REGISTER_CLIENT = 0x001;
    public static final int UNREGISTER_CLIENT = 0x002;
    public static final int REQUEST_GET_VALUE = 0x003;
    public static final int REQUEST_GET_NAVIGATION = 0x004;

    private class IncomeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_CLIENT:
                    AppLogger.e("Receive request[REGISTER_CLIENT] from client!");
                    Messenger addClient = msg.replyTo;
                    mClients.add(addClient);
                    break;
                case UNREGISTER_CLIENT:
                    AppLogger.e("Receive request[UNREGISTER_CLIENT] from client!");
                    Messenger removeClient = msg.replyTo;
                    mClients.remove(removeClient);
                    break;
                /*case REQUEST_GET_VALUE:
                    AppLogger.e("Receive request[REQUEST_GET_VALUE] from client!clients number="
                            + mClients.size() + ",hashCode=" + msg.arg1);
                    Message sendMessage = Message.obtain(null, ModulePreferenceItem.RESPONSE_SET_VALUE, hashCode(), 0);
                    int N = mClients.size();
                    for (int i = N - 1; i >= 0; i--) {
                        try {
                            AppLogger.e("Send Message to Client...");
                            mClients.get(i).send(sendMessage);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            mClients.remove(i);
                        }
                    }
                    break;*/
                case REQUEST_GET_NAVIGATION:
                    AppLogger.e("开始获取Navigation");
                    //Params par = (Params) msg.obj;
                    //AppLogger.e(par.getId()+","+par.getKey()+","+par.getValue());

                    break;
                default:
            }
        }
    };
    private ArrayList<Messenger> mClients = new ArrayList<Messenger>();
    private Messenger mServer = new Messenger(new IncomeHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mServer.getBinder();
    }
}
