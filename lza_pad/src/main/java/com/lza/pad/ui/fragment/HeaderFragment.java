package com.lza.pad.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.url.ApiUrlFactory;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.lib.support.network.GsonRequest;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.ui.fragment.preference.BasePreferenceActivity;
import com.lza.pad.ui.widget.DigitalClock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 界面标题栏
 *
 * @author Sam
 * @Date 14-9-12
 */
public class HeaderFragment extends Fragment implements OnResponseListener<EbookRequest> {

    private DigitalClock mClock;
    //private WeatherPanel mWeatherPanel;
    private TextView mTxtDate;

    private ImageView mImgWeather;
    private TextView mTxtWeatherTemp, mTxtWeatherInfo, mTxtWeatherCity;

    private NavigationInfo mNav;
    private static final int GET_PREFERENCE = 0x001;
    private Context mContext;
    //弹出Menu的密码
    private static final String MENU_PASSWORD = "njlza";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNav = NavigationInfoDao.getInstance().queryNotClosedBySortId(1);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.header, container, false);
        mClock = (DigitalClock) view.findViewById(R.id.header_digital_clock);
        //mWeatherPanel = (WeatherPanel) view.findViewById(R.id.header_weather_panel);
        mTxtDate = (TextView) view.findViewById(R.id.header_date);
        mClock.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //输入管理员密码
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                final View view = layoutInflater.inflate(R.layout.authenticate_panel, null);
                new AlertDialog.Builder(mContext)
                        .setTitle(R.string.admin_password_title)
                        .setView(view)
                        .setPositiveButton(R.string.admin_password_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText edtPassowrd = (EditText) view.findViewById(R.id.admin_password);
                                String password = edtPassowrd.getText().toString();
                                if (TextUtils.isEmpty(password)) {
                                    Toast.makeText(mContext, R.string.admin_password_empty, Toast.LENGTH_SHORT).show();
                                } else if (password.equals(MENU_PASSWORD)) {
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), BasePreferenceActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(mContext, R.string.admin_password_error, Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.admin_password_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        //天气相关的控件
        mImgWeather = (ImageView) view.findViewById(R.id.weather_img);
        mTxtWeatherTemp = (TextView) view.findViewById(R.id.weather_temperature);
        mTxtWeatherInfo = (TextView) view.findViewById(R.id.weather_info);
        mTxtWeatherCity = (TextView) view.findViewById(R.id.weather_city);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SherlockActivity.RESULT_OK) {
            if (requestCode == GET_PREFERENCE) {
                ToastUtilsSimplify.show("GOT IT!");
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mClock != null)
            mClock.calibrationTime();
        /*if (mWeatherPanel != null)
            mWeatherPanel.sendUpdateWeatherRequest();*/
        if (mTxtDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
            String dateStr = sdf.format(new Date());
            mTxtDate.setText(dateStr);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNav != null) {
            String weatherApiUrl = ApiUrlFactory.createWeatherApiUrl(getActivity(), mNav);
            AppLogger.e("weatherApiUrl --> " + weatherApiUrl);
            GsonRequest request = new GsonRequest<WeatherInfo>(
                    Request.Method.GET,
                    weatherApiUrl,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            volleyError.printStackTrace();
                        }
                    },
                    WeatherInfo.class,
                    null,
                    new Response.Listener<WeatherInfo>() {
                        @Override
                        public void onResponse(WeatherInfo weatherInfo) {
                            if (weatherInfo != null) {
                                CityWeather cityWeather = weatherInfo.getWeatherinfo();
                                if (cityWeather != null) {
                                    mTxtWeatherTemp.setText(cityWeather.getDaystr1());
                                    mTxtWeatherInfo.setText(cityWeather.getWeather1());
                                    mTxtWeatherCity.setText(cityWeather.getTemp1());
                                    setImage(cityWeather.getImg1());
                                }
                            }
                        }
                    });
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(request);
        }
    }

    // 天气预报根据，返回的编号，显示本地的图片
    private void setImage(int imageNumber) {
        int[] imags = new int[] {
                R.drawable.weather_sun,
                R.drawable.weather_cloudy,
                R.drawable.weather_overcast,
                R.drawable.weather_shower,
                R.drawable.weather_threadstorme,
                R.drawable.weather_threadstorme,
                R.drawable.weather_snow,
                R.drawable.weather_light_rain,
                R.drawable.weather_moderate_rain,
                R.drawable.weather_heavy_rain,
                R.drawable.weather_torrential_rain,
                R.drawable.weather_torrential_rain,
                R.drawable.weather_torrential_rain,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_fog,
                R.drawable.weather_shower,
                R.drawable.weather_sand_storm,
                R.drawable.weather_light_rain,
                R.drawable.weather_moderate_rain,
                R.drawable.weather_heavy_rain,
                R.drawable.weather_torrential_rain,
                R.drawable.weather_torrential_rain,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_snow,
                R.drawable.weather_sand_storm,
                R.drawable.weather_sand_storm,
                R.drawable.weather_sand_storm };
        mImgWeather.setImageResource(imags[imageNumber]);

    }

    @Override
    public void onSuccess(EbookRequest ebookRequest) {

    }

    @Override
    public void onError(Exception error) {

    }
}

class WeatherInfo {
    private CityWeather weatherinfo;

    public CityWeather getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(CityWeather weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
class CityWeather {
    private String daystr1;
    private String city;
    private String city_en;
    private String date_y;
    private String date;
    private String week;
    private String fchh;
    private String cityid;
    private String temp1;
    private String temp2;
    private String temp3;
    private String temp4;
    private String temp5;
    private String temp6;
    private String tempF1;
    private String tempF2;
    private String tempF3;
    private String tempF4;
    private String tempF5;
    private String tempF6;
    private String weather1;
    private String weather2;
    private String weather3;
    private String weather4;
    private String weather5;
    private String weather6;
    private int img1;
    private int img2;
    private int img3;
    private int img4;
    private int img5;
    private int img6;
    private int img7;
    private int img8;
    private int img9;
    private int img10;
    private int img11;
    private int img12;
    private String img_single;
    private String img_title1;
    private String img_title2;
    private String img_title3;
    private String img_title4;
    private String img_title5;
    private String img_title6;
    private String img_title7;
    private String img_title8;
    private String img_title9;
    private String img_title10;
    private String img_title11;
    private String img_title12;
    private String img_title_single;
    private String wind1;
    private String wind2;
    private String wind3;
    private String wind4;
    private String wind5;
    private String wind6;
    private String fx1;
    private String fx2;
    private String fl1;
    private String fl2;
    private String fl3;
    private String fl4;
    private String fl5;
    private String fl6;
    private String index;
    private String index_d;
    private String index48;
    private String index48_d;
    private String index_uv;
    private String index48_uv;
    private String index_xc;
    private String index_tr;
    private String index_co;
    private String st1;
    private String st2;
    private String st3;
    private String st4;
    private String st5;
    private String st6;
    private String index_cl;
    private String index_ls;
    private String index_ag;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_en() {
        return city_en;
    }

    public void setCity_en(String cityEn) {
        city_en = cityEn;
    }

    public String getDate_y() {
        return date_y;
    }

    public void setDate_y(String dateY) {
        date_y = dateY;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFchh() {
        return fchh;
    }

    public void setFchh(String fchh) {
        this.fchh = fchh;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
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

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }

    public String getTemp6() {
        return temp6;
    }

    public void setTemp6(String temp6) {
        this.temp6 = temp6;
    }

    public String getTempF1() {
        return tempF1;
    }

    public void setTempF1(String tempF1) {
        this.tempF1 = tempF1;
    }

    public String getTempF2() {
        return tempF2;
    }

    public void setTempF2(String tempF2) {
        this.tempF2 = tempF2;
    }

    public String getTempF3() {
        return tempF3;
    }

    public void setTempF3(String tempF3) {
        this.tempF3 = tempF3;
    }

    public String getTempF4() {
        return tempF4;
    }

    public void setTempF4(String tempF4) {
        this.tempF4 = tempF4;
    }

    public String getTempF5() {
        return tempF5;
    }

    public void setTempF5(String tempF5) {
        this.tempF5 = tempF5;
    }

    public String getTempF6() {
        return tempF6;
    }

    public void setTempF6(String tempF6) {
        this.tempF6 = tempF6;
    }

    public String getWeather1() {
        return weather1;
    }

    public void setWeather1(String weather1) {
        this.weather1 = weather1;
    }

    public String getWeather2() {
        return weather2;
    }

    public void setWeather2(String weather2) {
        this.weather2 = weather2;
    }

    public String getWeather3() {
        return weather3;
    }

    public void setWeather3(String weather3) {
        this.weather3 = weather3;
    }

    public String getWeather4() {
        return weather4;
    }

    public void setWeather4(String weather4) {
        this.weather4 = weather4;
    }

    public String getWeather5() {
        return weather5;
    }

    public void setWeather5(String weather5) {
        this.weather5 = weather5;
    }

    public String getWeather6() {
        return weather6;
    }

    public void setWeather6(String weather6) {
        this.weather6 = weather6;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getImg3() {
        return img3;
    }

    public void setImg3(int img3) {
        this.img3 = img3;
    }

    public int getImg4() {
        return img4;
    }

    public void setImg4(int img4) {
        this.img4 = img4;
    }

    public int getImg5() {
        return img5;
    }

    public void setImg5(int img5) {
        this.img5 = img5;
    }

    public int getImg6() {
        return img6;
    }

    public void setImg6(int img6) {
        this.img6 = img6;
    }

    public int getImg7() {
        return img7;
    }

    public void setImg7(int img7) {
        this.img7 = img7;
    }

    public int getImg8() {
        return img8;
    }

    public void setImg8(int img8) {
        this.img8 = img8;
    }

    public int getImg9() {
        return img9;
    }

    public void setImg9(int img9) {
        this.img9 = img9;
    }

    public int getImg10() {
        return img10;
    }

    public void setImg10(int img10) {
        this.img10 = img10;
    }

    public int getImg11() {
        return img11;
    }

    public void setImg11(int img11) {
        this.img11 = img11;
    }

    public int getImg12() {
        return img12;
    }

    public void setImg12(int img12) {
        this.img12 = img12;
    }

    public String getImg_single() {
        return img_single;
    }

    public void setImg_single(String imgSingle) {
        img_single = imgSingle;
    }

    public String getImg_title1() {
        return img_title1;
    }

    public void setImg_title1(String imgTitle1) {
        img_title1 = imgTitle1;
    }

    public String getImg_title2() {
        return img_title2;
    }

    public void setImg_title2(String imgTitle2) {
        img_title2 = imgTitle2;
    }

    public String getImg_title3() {
        return img_title3;
    }

    public void setImg_title3(String imgTitle3) {
        img_title3 = imgTitle3;
    }

    public String getImg_title4() {
        return img_title4;
    }

    public void setImg_title4(String imgTitle4) {
        img_title4 = imgTitle4;
    }

    public String getImg_title5() {
        return img_title5;
    }

    public void setImg_title5(String imgTitle5) {
        img_title5 = imgTitle5;
    }

    public String getImg_title6() {
        return img_title6;
    }

    public void setImg_title6(String imgTitle6) {
        img_title6 = imgTitle6;
    }

    public String getImg_title7() {
        return img_title7;
    }

    public void setImg_title7(String imgTitle7) {
        img_title7 = imgTitle7;
    }

    public String getImg_title8() {
        return img_title8;
    }

    public void setImg_title8(String imgTitle8) {
        img_title8 = imgTitle8;
    }

    public String getImg_title9() {
        return img_title9;
    }

    public void setImg_title9(String imgTitle9) {
        img_title9 = imgTitle9;
    }

    public String getImg_title10() {
        return img_title10;
    }

    public void setImg_title10(String imgTitle10) {
        img_title10 = imgTitle10;
    }

    public String getImg_title11() {
        return img_title11;
    }

    public void setImg_title11(String imgTitle11) {
        img_title11 = imgTitle11;
    }

    public String getImg_title12() {
        return img_title12;
    }

    public void setImg_title12(String imgTitle12) {
        img_title12 = imgTitle12;
    }

    public String getImg_title_single() {
        return img_title_single;
    }

    public void setImg_title_single(String imgTitleSingle) {
        img_title_single = imgTitleSingle;
    }

    public String getWind1() {
        return wind1;
    }

    public void setWind1(String wind1) {
        this.wind1 = wind1;
    }

    public String getWind2() {
        return wind2;
    }

    public void setWind2(String wind2) {
        this.wind2 = wind2;
    }

    public String getWind3() {
        return wind3;
    }

    public void setWind3(String wind3) {
        this.wind3 = wind3;
    }

    public String getWind4() {
        return wind4;
    }

    public void setWind4(String wind4) {
        this.wind4 = wind4;
    }

    public String getWind5() {
        return wind5;
    }

    public void setWind5(String wind5) {
        this.wind5 = wind5;
    }

    public String getWind6() {
        return wind6;
    }

    public void setWind6(String wind6) {
        this.wind6 = wind6;
    }

    public String getFx1() {
        return fx1;
    }

    public void setFx1(String fx1) {
        this.fx1 = fx1;
    }

    public String getFx2() {
        return fx2;
    }

    public void setFx2(String fx2) {
        this.fx2 = fx2;
    }

    public String getFl1() {
        return fl1;
    }

    public void setFl1(String fl1) {
        this.fl1 = fl1;
    }

    public String getFl2() {
        return fl2;
    }

    public void setFl2(String fl2) {
        this.fl2 = fl2;
    }

    public String getFl3() {
        return fl3;
    }

    public void setFl3(String fl3) {
        this.fl3 = fl3;
    }

    public String getFl4() {
        return fl4;
    }

    public void setFl4(String fl4) {
        this.fl4 = fl4;
    }

    public String getFl5() {
        return fl5;
    }

    public void setFl5(String fl5) {
        this.fl5 = fl5;
    }

    public String getFl6() {
        return fl6;
    }

    public void setFl6(String fl6) {
        this.fl6 = fl6;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex_d() {
        return index_d;
    }

    public void setIndex_d(String indexD) {
        index_d = indexD;
    }

    public String getIndex48() {
        return index48;
    }

    public void setIndex48(String index48) {
        this.index48 = index48;
    }

    public String getIndex48_d() {
        return index48_d;
    }

    public void setIndex48_d(String index48D) {
        index48_d = index48D;
    }

    public String getIndex_uv() {
        return index_uv;
    }

    public void setIndex_uv(String indexUv) {
        index_uv = indexUv;
    }

    public String getIndex48_uv() {
        return index48_uv;
    }

    public void setIndex48_uv(String index48Uv) {
        index48_uv = index48Uv;
    }

    public String getIndex_xc() {
        return index_xc;
    }

    public void setIndex_xc(String indexXc) {
        index_xc = indexXc;
    }

    public String getIndex_tr() {
        return index_tr;
    }

    public void setIndex_tr(String indexTr) {
        index_tr = indexTr;
    }

    public String getIndex_co() {
        return index_co;
    }

    public void setIndex_co(String indexCo) {
        index_co = indexCo;
    }

    public String getSt1() {
        return st1;
    }

    public void setSt1(String st1) {
        this.st1 = st1;
    }

    public String getSt2() {
        return st2;
    }

    public void setSt2(String st2) {
        this.st2 = st2;
    }

    public String getSt3() {
        return st3;
    }

    public void setSt3(String st3) {
        this.st3 = st3;
    }

    public String getSt4() {
        return st4;
    }

    public void setSt4(String st4) {
        this.st4 = st4;
    }

    public String getSt5() {
        return st5;
    }

    public void setSt5(String st5) {
        this.st5 = st5;
    }

    public String getSt6() {
        return st6;
    }

    public void setSt6(String st6) {
        this.st6 = st6;
    }

    public String getIndex_cl() {
        return index_cl;
    }

    public void setIndex_cl(String indexCl) {
        index_cl = indexCl;
    }

    public String getIndex_ls() {
        return index_ls;
    }

    public void setIndex_ls(String indexLs) {
        index_ls = indexLs;
    }

    public String getIndex_ag() {
        return index_ag;
    }

    public void setIndex_ag(String indexAg) {
        index_ag = indexAg;
    }

    public String getDaystr1() {
        return daystr1;
    }

    public void setDaystr1(String daystr1) {
        this.daystr1 = daystr1;
    }
}
