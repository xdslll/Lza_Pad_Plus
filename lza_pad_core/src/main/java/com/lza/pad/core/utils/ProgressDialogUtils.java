package com.lza.pad.core.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 封装了ProgressDialog的工具类，使得ProgressDialog的调用和界面逻辑解耦
 *
 * @author Sam
 * @Date 2014-8-17
 */
public class ProgressDialogUtils {

    private static final int SHOW_DIALOG = 0x0001;
    private static final int CANCEL_DIALOG = 0x0002;
    private static final int UPDATE_PROGRESS = 0x0003;
    private static int sDelayTime = 20 * 1000;
    private static String sDelayMessage = "请求超时!";
    private static Context sContext = null;

    private static ProgressDialog sProgressDialog = null;

    /**
     * 内部Handler，主要完成以下任务：
     * 1.展示ProgressDialog，并设定自动关闭时间
     * 2.关闭ProgressDialog
     * 3.更新进度条
     */
    private static Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_DIALOG:
                    sProgressDialog.show();
                    //到达指定时间后，后如果未下载完成则自动关闭进度条
                    postDelayed(sDelayTask, sDelayTime);
                    break;
                case CANCEL_DIALOG:
                    sProgressDialog.cancel();
                    break;
                case UPDATE_PROGRESS:
                    int progressValue = (Integer) msg.obj;
                    sProgressDialog.setProgress(progressValue);
                default:
            }
        }
    };

    /**
     * 自动关闭ProgressDialog的任务
     */
    private static Runnable sDelayTask = new Runnable() {
        @Override
        public void run() {
            if(sContext != null) {
                if (sProgressDialog.isShowing()) {
                    sHandler.sendEmptyMessage(CANCEL_DIALOG);
                    Toast.makeText(sContext, sDelayMessage, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 静态方法，用于创建ProgressDialog
     *
     * @param context 显示ProgressDialog的上下文
     * @param message ProgressDialog的提示文字
     * @param styleId ProgressDialog的样式
     * @return
     */
    public static ProgressDialog createProgressDialog(Context context, String message, int styleId) {
        sContext = context;
        sProgressDialog = new ProgressDialog(sContext);
        sProgressDialog.setProgressStyle(styleId);
        sProgressDialog.setIndeterminate(false);
        sProgressDialog.setCancelable(false);
        sProgressDialog.setCanceledOnTouchOutside(false);
        sProgressDialog.setMessage(message);
        return sProgressDialog;
    }

    /**
     * 创建带进度条的ProgressDialog
     *
     * @param context 显示ProgressDialog的上下文
     * @param message ProgressDialog的提示文字
     * @return
     */
    public static ProgressDialog createHorizontalProgressDialog(Context context, String message) {
       return createProgressDialog(context, message, ProgressDialog.STYLE_HORIZONTAL);
    }

    /**
     * 设置自动关闭ProgressDialog的延迟时间
     *
     * @param delayTime 延迟时间，单位：毫秒
     */
    public static void setDelayTime(int delayTime) {
        sDelayTime = delayTime;
    }

    /**
     * 设置自动关闭ProgressDialog时显示的文本
     * @param message 显示文本
     */
    public static void setDelayMessage(String message) {
        sDelayMessage = message;
    }

    /**
     * 显示ProgressDialog
     */
    public static void showDialog() {
        if (!isShowing()){
            sHandler.sendEmptyMessage(SHOW_DIALOG);
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public static void dismissDialog() {
        if (isShowing()) {
            sHandler.sendEmptyMessage(CANCEL_DIALOG);
        }
    }

    /**
     * 更新进度条
     *
     * @param progress 进度值
     */
    public static void updateProgress(int progress) {
        if (isShowing()) {
            Message msg = Message.obtain(sHandler, UPDATE_PROGRESS, progress);
            msg.sendToTarget();
        }
    }

    /**
     * 判断ProgressDialog是否显示
     *
     * @return
     */
    public static boolean isShowing() {
        return sProgressDialog != null && sProgressDialog.isShowing();
    }
}
