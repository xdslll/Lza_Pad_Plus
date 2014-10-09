package com.lza.pad.ui.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.db.model.WeatherInfoDetail;
import com.lza.pad.core.request.WeatherRequest;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.utils.UniversalUtility;

import java.io.IOException;

/**
 * Created by xiads on 14-9-8.
 */
public class WeatherPanel extends FrameLayout
        implements Consts.Weather, WeatherRequest.OnGetWeatherDetail {

    private ImageView mImgWeather;
    private TextView mTxtWeatherTemp, mTxtWeatherInfo, mTxtWeatherCity;
    private AssetManager mAssetManager;
    private WeatherRequest mWeatherRequest;
    private String mCityCode;

    public WeatherPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctr_weather_panel, this);

        mImgWeather = (ImageView) findViewById(R.id.weather_img);
        mTxtWeatherTemp = (TextView) findViewById(R.id.weather_temperature);
        mTxtWeatherInfo = (TextView) findViewById(R.id.weather_info);
        mTxtWeatherCity = (TextView) findViewById(R.id.weather_city);

        mAssetManager = context.getAssets();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mAssetManager.open(WEATHER_ICON_UNDEFINED));
            mImgWeather.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取城市属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.weather);
        mCityCode = array.getString(R.styleable.weather_city);
        array.recycle();
    }

    /**
     * 更新天气
     */
    public void sendUpdateWeatherRequest() {
        mWeatherRequest = WeatherRequest.getInstance(mCityCode, this);
        mWeatherRequest.send();
    }

    public void updateWeatherUi(WeatherInfoDetail weatherDetail) {
        String assetsIconName = getWeatherIconPath(weatherDetail);
        String weatherInfo = weatherDetail.getWeather();
        String maxTemperature = weatherDetail.getTemp1();
        if (!TextUtils.isEmpty(maxTemperature))
            maxTemperature = maxTemperature.split(WEATHER_TEMPERATURE_SYMBOL)[0];
        String minTemperature = weatherDetail.getTemp2();
        String cityName = weatherDetail.getCity();
        String updateTime = weatherDetail.getPtime();
        StringBuilder infoBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(cityName))
            infoBuilder.append(cityName);
        if (!TextUtils.isEmpty(updateTime))
            infoBuilder.append("\t").append(updateTime).append("更新");

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mAssetManager.open(assetsIconName));
            if (mImgWeather != null)
                mImgWeather.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mTxtWeatherInfo != null && !TextUtils.isEmpty(weatherInfo))
            mTxtWeatherInfo.setText(weatherInfo);
        if (mTxtWeatherTemp != null && !TextUtils.isEmpty(maxTemperature) && !TextUtils.isEmpty(minTemperature))
            mTxtWeatherTemp.setText(maxTemperature + "~" + minTemperature);
        if (mTxtWeatherCity != null)
            mTxtWeatherCity.setText(infoBuilder.toString());
    }

    /**
     * 获取天气成功后在界面上显示
     *
     * @param weatherDetail
     */
    @Override
    public void onGetWeatherDetailSuccess(final WeatherInfoDetail weatherDetail) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                updateWeatherUi(weatherDetail);
            }
        });
    }
    Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onGetWeatherDetailFailed(VolleyError volleyError) {
        ToastUtilsSimplify.show("获取天气失败!失败原因:" + volleyError.getMessage());
    }

    /**
     * 获取天气图片的路径
     *
     * @param weatherDetail
     * @return
     */
    private String getWeatherIconPath(WeatherInfoDetail weatherDetail) {
        StringBuilder assetsIconBuilder = new StringBuilder();
        String orgIconName;
        //如果是白天，则访问day目录，如果是晚上，则访问night目录
        if (UniversalUtility.isDayOrNight()) {
            assetsIconBuilder.append(WEATHER_ICON_DIR_DAY);
            orgIconName = weatherDetail.getImg1();
        }else {
            assetsIconBuilder.append(WEATHER_ICON_DIR_NIGHT);
            orgIconName = weatherDetail.getImg2();
        }
        //截取第二个字符，获取天气图片的代号
        int iconCode = Integer.valueOf(orgIconName.substring(1, 2));
        //如果小于10，首尾添加0
        if (iconCode < 10)
            assetsIconBuilder.append("0");
        assetsIconBuilder.append(String.valueOf(iconCode)).append(".png");
        return assetsIconBuilder.toString();
    }
}
