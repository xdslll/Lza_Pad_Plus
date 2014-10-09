package com.lza.pad.core.db.model;

/**
 * 获取天气预报后返回的数据
 *
 * @author Sam
 * @Date 2014-9-10
 */
public class WeatherInfoDetail {

    /**
     * 所在城市
     */
    private String city;

    /**
     * 城市id
     */
    private int cityid;

    /**
     * 最高温度
     */
    private String temp1;

    /**
     * 最低温度
     */
    private String temp2;

    /**
     * 天气情况
     */
    private String weather;

    /**
     * 天气相关的图片
     */
    private String img1;

    /**
     * 天气相关的图片
     */
    private String img2;

    /**
     * 采集时间
     */
    private String ptime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    @Override
    public String toString() {
        return "WeatherInfoDetail{" +
                "city='" + city + '\'' +
                ", cityid=" + cityid +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", weather='" + weather + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", ptime='" + ptime + '\'' +
                '}';
    }
}
