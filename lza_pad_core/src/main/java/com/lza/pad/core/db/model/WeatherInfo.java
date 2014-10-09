package com.lza.pad.core.db.model;

/**
 * 获取天气预报后返回的数据
 *
 * @author Sam
 * @Date 2014-9-10
 */
public class WeatherInfo {

    private WeatherInfoDetail weatherinfo;

    public WeatherInfoDetail getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(WeatherInfoDetail weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
