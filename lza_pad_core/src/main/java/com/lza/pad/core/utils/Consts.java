package com.lza.pad.core.utils;

import android.app.Activity;

/**
 * 常量参数
 *
 * @author Sam
 * @Date 14-9-12
 */
public interface Consts {

    public static final String KEY_CACHE_UNKNOWN_COVER = "unknown_cover";

    public static final String REQUEST_CONTROL_TYPE_EBOOK = "ebook";
    public static final String REQUEST_CONTROL_TYPE_EBOOK_JC = "ebook_jc";
    public static final String REQUEST_CONTROL_TYPE_QK_MESSAGE = "qk_message";
    public static final String REQUEST_CONTROL_TYPE_NEW_BOOK = "schoolNewBook";
    public static final String REQUEST_CONTROL_TYPE_HOT_BOOK = "schoolHotBook";
    public static final String REQUEST_CONTROL_TYPE_LIB_NEWS = "News";

    public static final String EBOOK_ACTION_LIST = "getMessage";
    public static final String EBOOK_ACTION_CONTENT = "getMrMessage";
    public static final String JOURNALS_ACTION_LIST = "getMessage";
    public static final String JOURNALS_ACTION_CONTENT = "getMessage_1_0";
    public static final String NEW_BOOK_ACTION_LIST = "getMessageList";
    public static final String NEW_BOOK_ACTION_CONTENT = "user_check";
    public static final String HOT_BOOK_ACTION_LIST = "getMessageList";
    public static final String HOT_BOOK_ACTION_CONTENT = "user_check";
    public static final String HOT_BOOK_CONTROL_CONTENT = "book_review";
    public static final String LIB_NEWS_ACTION_LIST = "getMessage";

    public static final String KEY_NAVIGATION_INFO = "navigation_info";
    public static final String KEY_EBOOK_INFO = "ebook_info";
    public static final String KEY_EBOOK_REQUEST_INFO = "ebook_request_info";

    public static final String RECEIVER_DOWNLOAD_TEXT = "com.lza.pad.module.download.text";
    public static final String KEY_DOWNLOAD_TEXT = "download_text";

    public static final int TASK_INIT = 0x001;
    public static final int TASK_BEGIN = 0x002;
    public static final int TASK_NEXT = 0x003;
    public static final int TASK_FINISH = 0x004;
    public static final int TASK_OVER = 0x005;

    public static final int TASK_EBOOK_INIT = 0x006;
    public static final int TASK_EBOOK_BEGIN = 0x007;
    public static final int TASK_EBOOK_NEXT = 0x008;
    public static final int TASK_EBOOK_FINISH = 0x009;
    public static final int TASK_EBOOK_OVER = 0x010;

    public static final int TASK_OVER_WITH_NO_DATA = 0x011;

    public static final String REQUEST_TAG = "REQUEST_TAG";

    public static final String SP_NAME = "lza_pad_plus_pref";
    public static final int SP_MODE = Activity.MODE_PRIVATE;

    /**
     * 自动关闭进度条对话框的时间
     */
    public final int DISMISS_PROGRESS_DIALOG_DELAY = 20 * 1000;

    public interface Weather {

        public static final String WEATHER_API_ADDRESS = "http://www.weather.com.cn/";
        public static final String WEATHER_API_REAL_TIME_AUTHORITY = "data/cityinfo/";

        public static final String WEATHER_ICON_UNDEFINED = "weather_icon/day/undefined.png";
        public static final String WEATHER_ICON_DIR_DAY = "weather_icon/day/";
        public static final String WEATHER_ICON_DIR_NIGHT = "weather_icon/night/";
        public static final String WEATHER_TEMPERATURE_SYMBOL = "℃";
    }

    public interface Request {

        public static final String BASE_API_ADDRESS = "http://219.219.114.83/pad_2.0/controller.php";
        public static final String BASE_CACHE_DIR = "/lza/pad";
        public static final String IMG_CACHE_DIR = "/img";
    }

}
