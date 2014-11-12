package com.lza.pad.ui.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.ui.activity.base.AbstractActivity;
import com.lza.pad.ui.fragment.FooterNavigationFragment;
import com.lza.pad.ui.fragment.HeaderFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 主界面，通过包含一个顶部标题栏和一个底部导航栏，中间部分根据用户需求进行替换
 *
 * @author Sam
 * @Date 14-9-11
 */
public class HomeActivity extends AbstractActivity {

    public static final String HEADER_FRAGMENT_TAG = "header";
    public static final String CONTAINER_FRAGMENT_TAG = "container";
    public static final String FOOTER_FRAGMENT_TAG = "footer";
    private Timer mTimer;
    public static final String HOME_ACTIVITY_RECEIVER = "com.lza.pad.RECEIVER";
    private HomeReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        mReceiver = new HomeReceiver();
        registerReceiver(mReceiver, new IntentFilter(HOME_ACTIVITY_RECEIVER));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mCurrentFreeTime = 0;
        return super.dispatchTouchEvent(ev);
    }

    private int mCurrentFreeTime = 0;
    private int mScreenSaverTime = 0;
    private int DEFAULT_SCREEN_SAVER_SHOW_TIME = 60;
    private static final int SHOW_SCREEN_SAVER = 0x001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_SCREEN_SAVER:
                    Intent intent = new Intent(HomeActivity.this, ScreenSaverActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentFreeTime = 0;

        try {
            mScreenSaverTime = NavigationInfoDao.getInstance().queryNotClosedBySortId(1).getScreenSaverTime();
        } catch (Exception ex) {
            mScreenSaverTime = 0;
        }
        if (mScreenSaverTime == 0) {
            mScreenSaverTime = DEFAULT_SCREEN_SAVER_SHOW_TIME;
        }

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mCurrentFreeTime += 5;
                AppLogger.e("Current Free Time --> " + mCurrentFreeTime);
                if (mCurrentFreeTime > mScreenSaverTime) {
                    AppLogger.e("显示屏保程序...");
                    mHandler.sendEmptyMessage(SHOW_SCREEN_SAVER);
                }
            }
        }, 0, 5000);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_header, new HeaderFragment(), HEADER_FRAGMENT_TAG);
        ft.replace(R.id.home_footer, new FooterNavigationFragment(), FOOTER_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        //Process.killProcess(Process.myPid());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondPressed = System.currentTimeMillis();
            if (secondPressed - mFirstBackPressed > MAX_DELAY) {
                ToastUtilsSimplify.show("再按一次退出程序");
                mFirstBackPressed = secondPressed;
            } else {
                System.exit(0);
            }
        }
        return true;
    }

    private int MAX_DELAY = 1000;
    private long mFirstBackPressed = 0;

    private class HomeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mCurrentFreeTime = 0;
        }
    }
}
