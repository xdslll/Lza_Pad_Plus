package com.lza.pad.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lza.pad.R;

import org.apache.http.impl.cookie.DateParseException;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自制时钟控件,主要功能:
 * 1. 时钟的更新和显示
 * 2. 自动校准时间
 *
 * @author Sam
 * @Date 2014-9-8
 */
public class DigitalClock extends FrameLayout {

    TextView mTxtHour, mTxtMinute;
    Context mContext;

    public DigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctr_digital_clock, this);
        mTxtHour = (TextView) findViewById(R.id.digital_clock_hour);
        mTxtMinute = (TextView) findViewById(R.id.digital_clock_minute);
        updateTimePerMinute();
    }

    /**
     * 每分钟更新一次时间
     */
    public void updateTimePerMinute() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE_HOUR);
            }
        };
        //ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        //service.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
        //service.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);
        Timer timer = new Timer("timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mCurrentHour < 12 && mCurrentHour >= 0) {
                    mCurrentHour++;
                    Message msg = new Message();
                    msg.obj = mCurrentHour;
                    msg.what = UPDATE_HOUR;
                    mHandler.sendMessage(msg);
                } else {
                    mCurrentHour = 0;
                }
            }
        }, 10000);
    }

    /**
     * 更新时间
     *
     * @param hour
     * @param minute
     */
    public void updateTime(int hour, int minute) {
        StringBuilder hourBuilder = new StringBuilder();
        StringBuilder minuteBuilder = new StringBuilder();
        try {
            if (hour > 23 || hour < 0) {
                throw new DateParseException("小时不能小于0或大于24!");
            }
            if (minute > 60 || minute < 0) {
                throw new DateParseException("分钟不能小于0或大于60!");
            }
            if (hour < 10) {
                hourBuilder.append("0");
            }
            hourBuilder.append(String.valueOf(hour));
            if (minute < 10) {
                minuteBuilder.append("0");
            }
            minuteBuilder.append(String.valueOf(minute));
            mTxtHour.setText(hourBuilder.toString());
            mTxtMinute.setText(minuteBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calibrationTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        updateTime(hour, minute);
    }

    private static final int UPDATE_HOUR = 0x001;
    private int mCurrentHour = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_HOUR:
                    /*int hour = (Integer) msg.obj;
                    Animation animation = AnimationUtils.loadAnimation(
                            mContext, R.anim.alphain);
                    mTxtHour.startAnimation(animation);
                    mTxtHour.setText(String.valueOf(hour));*/
                    calibrationTime();
                    break;
            }
        }
    };
    private Runnable mRunnalbe = new Runnable() {
        @Override
        public void run() {
            if (mCurrentHour < 12 && mCurrentHour >= 0) {
                mCurrentHour++;
                Message msg = new Message();
                msg.obj = mCurrentHour;
                msg.what = UPDATE_HOUR;
                mHandler.sendMessage(msg);
            } else {
                mCurrentHour = 0;
            }
        }
    };
    public void testTimeUpdate() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(mRunnalbe, 2, 2, TimeUnit.SECONDS);
    }

}
