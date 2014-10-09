package com.lza.pad.core.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.core.db.model.WeatherInfo;
import com.lza.pad.core.db.model.WeatherInfoDetail;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.GlobalContext;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;

/**
 * Created by xiads on 14-9-10.
 */
public class WeatherRequest {

    private GsonRequest mRequest;
    private String mRequestUrl;
    private static WeatherRequest sWeatherRequest;
    private static String sCityCode;

    public interface OnGetWeatherDetail {
        public void onGetWeatherDetailSuccess(WeatherInfoDetail weatherDetail);
        public void onGetWeatherDetailFailed(VolleyError volleyError);
    }

    private OnGetWeatherDetail mWeatherDetailListener;

    public void setWeatherDetailListener(OnGetWeatherDetail weatherDetailListener) {
        this.mWeatherDetailListener = weatherDetailListener;
    }

    public WeatherRequest(String cityCode, OnGetWeatherDetail listener) {
        sCityCode = cityCode;
        mRequestUrl = ApiUrlFactory.createWeatherApiUrl(sCityCode);
        setWeatherDetailListener(listener);
        mRequest = new GsonRequest<WeatherInfo>(Request.Method.GET,
                mRequestUrl,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mWeatherDetailListener.onGetWeatherDetailFailed(volleyError);
                    }
                },
                WeatherInfo.class,
                null,
                new Response.Listener<WeatherInfo>() {
                    @Override
                    public void onResponse(WeatherInfo weatherInfo) {
                        WeatherInfoDetail weatherInfoDetail = weatherInfo.getWeatherinfo();
                        AppLogger.d("Get weather info-->" + weatherInfoDetail.toString());
                        mWeatherDetailListener.onGetWeatherDetailSuccess(weatherInfoDetail);
                    }
                });
    }

    public static WeatherRequest getInstance(String cityCode, OnGetWeatherDetail listener) {
        if (sCityCode == null || !cityCode.equals(sCityCode)) {
            sWeatherRequest = new WeatherRequest(cityCode, listener);
        }
        return sWeatherRequest;
    }

    public static WeatherRequest getInstance() {
        return sWeatherRequest;
    }

    public void send() {
        AppLogger.d("Send to url-->" + mRequest.getUrl());
        VolleySingleton.getInstance(GlobalContext.getInstance()).addToRequestQueue(mRequest);
    }

}
