package com.lza.pad.core.url;

import android.content.Context;

import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.model.ApiParams;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.network.BaseApiUrl;
import com.lza.pad.lib.support.utils.UniversalUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiads on 14-9-10.
 */
public class ApiUrlFactory implements Consts.Weather, Consts.Request, Consts {

    /**
     * API请求参数:版本名称
     */
    public static final String PAR_VERSION_NAME = "version";

    /**
     * API请求参数:设备ID
     */
    public static final String PAR_DEVICE_ID = "devcie";

    /**
     * API请求参数:网络类型
     */
    public static final String PAR_NETWORK_TYPE = "net";

    /**
     * API请求参数:请求的模块
     */
    public static final String PAR_CONTROL = "control";

    /**
     * API请求参数:对应的操作
     */
    public static final String PAR_ACTION = "action";

    /**
     * API请求参数:不详
     */
    public static final String PAR_SESSION_TAG = "sessionTag";

    /**
     * API请求参数:不详
     */
    public static final String PAR_K = "k";

    /**
     * API请求参数:每页的数据量
     */
    public static final String PAR_PAGE_SIZE = "pagesize";

    /**
     * API请求参数:请求页码
     */
    public static final String PAR_PAGE = "page";

    /**
     * API请求参数:学科
     */
    public static final String PAR_XK = "xk";

    /**
     * API请求参数:电子书ID
     */
    public static final String PAR_BOOK_ID = "bookId";

    /**
     * API请求参数:学校ID
     */
    public static final String PAR_SCHOOL_ID = "schoolId";

    /**
     * API请求参数:期刊ID
     */
    public static final String PAR_QK_ID = "qkId";

    /**
     * API请求参数:热门推荐ACT
     */
    public static final String PAR_ACT = "act";

    /**
     * API请求参数:热门推荐MARC_NO
     */
    public static final String PAR_MARC_NO = "marc_no";

    /**
     * API请求参数:新闻类型
     */
    public static final String PAR_TYPE = "type";

    public static final String PAR_RETURN_NUM = "returnNum";

    public static final String PAR_LAST_ID = "lastId";

    public static final String PAR_GET_TAG = "getTag";

    public static final String PAR_RETURN_TYPE = "returnType";

    public static final String PAR_CITY_ID = "cityId";

    public static final String PAR_SEARCH_TYPE = "searchtype";

    public static final String PAR_SOURCE_TYPE = "sourceType";

    /**
     * 获取天气API,已废弃,将替换为学校提供的API,本方法仅供测试
     *
     * @param cityCode
     * @return
     */
    @Deprecated
    public static String createWeatherApiUrl(String cityCode) {
        String WEATHER_API_PATH = cityCode + ".html";
        BaseApiUrl apiUrl = new BaseApiUrl(
                WEATHER_API_ADDRESS,
                WEATHER_API_REAL_TIME_AUTHORITY + WEATHER_API_PATH,
                null);
        return apiUrl.getUrl();
    }

    public static String createWeatherApiUrl(Context context, NavigationInfo nav) {
        String url = nav.getApiUrl();
        Map<String, String> pars = new HashMap<String, String>();
        pars.put(PAR_CONTROL, "padMessage");
        pars.put(PAR_ACTION, "getWeatherMessage");
        pars.put(PAR_RETURN_TYPE, "html");
        pars.put(PAR_CITY_ID, String.valueOf(nav.getApiCityCode()));
        pars.put(PAR_SCHOOL_ID, String.valueOf(nav.getApiSchoolIdPar()));

        appendDeviceInfo(context, pars);
        BaseApiUrl apiUrl = new BaseApiUrl(url, pars);
        return apiUrl.getUrl();
    }

    public static void appendDeviceInfo(Context context, Map<String, String> pars) {
        String versionName = UniversalUtility.getVersionName(context);
        String deviceId = UniversalUtility.getDeviceId(context);
        String networkType = UniversalUtility.getNetworkType(context);
        pars.put(PAR_VERSION_NAME, versionName);
        pars.put(PAR_DEVICE_ID, deviceId);
        pars.put(PAR_NETWORK_TYPE, networkType);
    }
    /**
     * 获取电子图书的接口地址
     *
     * @return
     */
    public static String createApiUrl(Map<String, String> params) {
        BaseApiUrl apiUrl = new BaseApiUrl(BASE_API_ADDRESS, params);
        return apiUrl.getUrl();
    }

    public static String createApiUrl(String url, Map<String, String> params) {
        BaseApiUrl apiUrl = new BaseApiUrl(url, params);
        return apiUrl.getUrl();
    }

   public static String createApiUrl(NavigationInfo ni) {
        Map<String, String> params = new UrlParamsBuilder(ni).build();
        String url = ni.getApiUrl();
        BaseApiUrl apiUrl = new BaseApiUrl(url, params);
        return apiUrl.getUrl();
    }

    /*public static String createApiUrl(NavigationInfo ni, String type) {
        List<ApiParams> apiPars = NavigationInfoDao.getInstance().queryApiParamsByIdAndType(ni, type);
        Map<String, String> params = new UrlParamsBuilder(apiPars).build();

        String url = ni.getApiUrl();
        BaseApiUrl apiUrl = new BaseApiUrl(url, params);
        return apiUrl.getUrl();
    }*/

    public static class UrlParamsBuilder {
        private Map<String, String> mParams = null;

        public UrlParamsBuilder(List<ApiParams> pars) {
            this();
            for (ApiParams par : pars) {
                mParams.put(par.getKey(), par.getValue());
            }
            this.appendDeviceInfo(GlobalContext.getInstance());
        }

        public UrlParamsBuilder(NavigationInfo ni) {
            this();
            this.setControl(ni.getApiControlPar());
            this.setAction(ni.getApiActionPar());
            this.setPageSize(String.valueOf(ni.getApiPageSizePar()));
            this.setPage(String.valueOf(ni.getApiPagePar()));
            this.setSchoolId(String.valueOf(ni.getApiSchoolIdPar()));
            this.setXk(ni.getApiXkPar());
            if (ni.getApiControlPar().equals(REQUEST_CONTROL_TYPE_LIB_NEWS)
                    && ni.getApiActionPar().equals(LIB_NEWS_ACTION_LIST)) {
                this.setType(String.valueOf(ni.getApiTypePar()));
                this.setReturnNum(ni.getApiReturnNum());
                this.setLastId(ni.getApiLastId());
                this.setGetTag(">");
            } else if (ni.getApiActionPar().equals(EBOOK_ACTION_CONTENT)) {
                this.setBookId(String.valueOf(ni.getApiBookId()));
            } else if (ni.getApiActionPar().equals(JOURNALS_ACTION_CONTENT)){
                this.setQkId(String.valueOf(ni.getApiBookId()));
            } else if (ni.getApiControlPar().equals(REQUEST_CONTROL_TYPE_SEARCH)) {
                this.setK(ni.getSearchKeyWord());
                this.setSearchType(String.valueOf(ni.getSearchType()));
                this.setSourceType(String.valueOf(ni.getSourceType()));
            } else if (ni.getApiActionPar().equals(HOT_BOOK_ACTION_CONTENT)) {
                this.setAct(ni.getApiActPar());
                this.setMarcNo(ni.getApiMarcNoPar());
            }

            this.appendDeviceInfo(GlobalContext.getInstance());
        }

        public UrlParamsBuilder() {
            this.mParams = new HashMap<String, String>();
        }

        public Map<String, String> build() {
            return mParams;
        }

        public UrlParamsBuilder setControl(String s) {
            mParams.put(PAR_CONTROL, s);
            return this;
        }

        public UrlParamsBuilder setAction(String s) {
            mParams.put(PAR_ACTION, s);
            return this;
        }

        public UrlParamsBuilder setSessionTag(String s) {
            mParams.put(PAR_SESSION_TAG, s);
            return this;
        }

        public UrlParamsBuilder setK(String s) {
            mParams.put(PAR_K, s);
            return this;
        }

        public UrlParamsBuilder setPageSize(String s) {
            mParams.put(PAR_PAGE_SIZE, s);
            return this;
        }

        public UrlParamsBuilder setPage(String s) {
            mParams.put(PAR_PAGE, s);
            return this;
        }

        public UrlParamsBuilder setXk(String s) {
            mParams.put(PAR_XK, s);
            return this;
        }

        public UrlParamsBuilder setBookId(String s) {
            mParams.put(PAR_BOOK_ID, s);
            return this;
        }

        public UrlParamsBuilder setSchoolId(String s) {
            mParams.put(PAR_SCHOOL_ID, s);
            return this;
        }

        public UrlParamsBuilder setQkId(String s) {
            mParams.put(PAR_QK_ID, s);
            return this;
        }

        public UrlParamsBuilder setAct(String s) {
            mParams.put(PAR_ACT, s);
            return this;
        }

        public UrlParamsBuilder setMarcNo(String s) {
            mParams.put(PAR_MARC_NO, s);
            return this;
        }

        public UrlParamsBuilder setType(String s) {
            mParams.put(PAR_TYPE, s);
            return this;
        }

        public UrlParamsBuilder setReturnNum(String s) {
            mParams.put(PAR_RETURN_NUM, s);
            return this;
        }

        public UrlParamsBuilder setLastId(String s) {
            mParams.put(PAR_LAST_ID, s);
            return this;
        }

        public UrlParamsBuilder setGetTag(String s) {
            mParams.put(PAR_GET_TAG, s);
            return this;
        }

        public UrlParamsBuilder setSearchType(String s) {
            mParams.put(PAR_SEARCH_TYPE, s);
            return this;
        }

        public UrlParamsBuilder setSourceType(String s) {
            mParams.put(PAR_SOURCE_TYPE, s);
            return this;
        }

        public UrlParamsBuilder appendDeviceInfo(Context context) {
            String versionName = UniversalUtility.getVersionName(context);
            String deviceId = UniversalUtility.getDeviceId(context);
            String networkType = UniversalUtility.getNetworkType(context);
            mParams.put(PAR_VERSION_NAME, versionName);
            mParams.put(PAR_DEVICE_ID, deviceId);
            mParams.put(PAR_NETWORK_TYPE, networkType);
            return this;
        }

    }

}
