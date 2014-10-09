package com.lza.pad.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lza.pad.ui.activity.main.SplashActivity;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-25.
 */
public class RunOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context, SplashActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startIntent);
    }
}
