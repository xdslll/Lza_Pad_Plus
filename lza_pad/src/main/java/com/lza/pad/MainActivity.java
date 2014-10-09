package com.lza.pad;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.BaseApiUrl;
import com.lza.pad.ui.activity.base.AbstractActivity;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initViewAndListener();
    }

    private void initViewAndListener() {
        createTestButton();
        createUpdateService();
        createPushService();
        createReceivePush();
    }

    private void createReceivePush() {
        findViewById(R.id.btn_receive_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void createPushService() {
        findViewById(R.id.btn_open_push_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动Push服务
                PushAgent mPushAgent = PushAgent.getInstance(MainActivity.this);
                mPushAgent.enable();
                mPushAgent.onAppStart();
                //获取设备token
                String device_token = UmengRegistrar.getRegistrationId(MainActivity.this);
                AppLogger.e("device_token=" + device_token);
            }
        });
    }

    private void createUpdateService() {
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UmengUpdateAgent.setUpdateCheckConfig(true);
                UmengUpdateAgent.setUpdateOnlyWifi(false);
                UmengUpdateListener listener = new UmengUpdateListener() {
                    /**
                     *
                     * @param statusCode 0表示有更新，1表示无更新，2表示非wifi状态，3表示请求超时
                     * @param updateResponse
                     */
                    @Override
                    public void onUpdateReturned(int statusCode, UpdateResponse updateResponse) {
                        AppLogger.d("statusCode=" + statusCode);
                        if (updateResponse != null) {
                            AppLogger.d("updateResponse.version=" + updateResponse.version);
                        }
                        switch (statusCode) {
                            case 0:
                                ToastUtilsSimplify.show("检查到更新!");
                                break;
                            case 1:
                                ToastUtilsSimplify.show("没有检查到更新!");
                                break;
                            case 2:
                                ToastUtilsSimplify.show("非wifi状态!");
                                break;
                            case 3:
                                ToastUtilsSimplify.show("请求超时!");
                                break;
                        }
                    }
                };
                UmengUpdateAgent.setUpdateListener(listener);
                UmengUpdateAgent.update(MainActivity.this);

                //AppLogger.d(getDeviceInfo(MainActivity.this));
            }
        });
    }

    private void createTestButton() {
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String protocol = "http";
                String authority = "115.28.242.196";
                String path = "eb1/controller.php";
                Map<String, String> params = new HashMap<String, String>();
                params.put("version", "1.0");
                params.put("device", "00000000");
                params.put("control", "ebook");
                params.put("action", "getMessage");
                BaseApiUrl baseApiUrl = new BaseApiUrl(protocol, authority, path, params);
                AppLogger.d(baseApiUrl.getUrl());
                AppLogger.d(baseApiUrl.getParamValue("control"));
                AppLogger.d("version_code=" + BuildConfig.VERSION_CODE );
                AppLogger.d("version_name=" + BuildConfig.VERSION_NAME );
            }
        });
    }

    /**
     * 获取测试设备的信息
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
