package com.lza.pad.core.db.loader;

import android.content.Context;

import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.core.db.model.NavigationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/9/14.
 */
public class GlobalPreferenceLoader extends AbstractLoader<List<Map<String, String>>> {

    public GlobalPreferenceLoader(Context context) {
        super(context);
    }

    @Override
    public List<Map<String, String>> loadInBackground() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        NavigationInfo nav = NavigationInfoDao.getInstance().queryNotClosedBySortId(1);
        if (nav != null) {
            setData("接口地址", nav.getApiUrl(), NavigationInfo._API_URL, data);
            setData("城市编号", nav.getApiCityCode(), NavigationInfo._API_CITY_CODE, data);
            setData("学校编号", nav.getApiSchoolIdPar(), NavigationInfo._API_SCHOOL_ID, data);
            setData("设备编号", nav.getApiDeviceCode(), NavigationInfo._API_DEVICE_CODE, data);
            setData("封面图片比例", nav.getImgScaling(), NavigationInfo._API_IMG_SCALING, data);
            setData("书架垂直偏移量", nav.getVerticalOffset(), NavigationInfo._API_VERTICAL_OFFSET, data);
            setData("运行模式", nav.getRunningMode(), NavigationInfo._API_RUNNING_MODE, data);

            /*Map<String, String> map = new HashMap<String, String>();
            map.put("key", "接口地址");
            map.put("value", nav.getApiUrl());
            map.put("col", NavigationInfo._API_URL);
            data.add(map);
            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("key", "城市编号");
            map2.put("value", String.valueOf(nav.getApiCityCode()));
            map2.put("col", NavigationInfo._API_CITY_CODE);
            data.add(map2);
            Map<String, String> map3 = new HashMap<String, String>();
            map3.put("key", "学校编号");
            map3.put("value", String.valueOf(nav.getApiSchoolIdPar()));
            map3.put("col", NavigationInfo._API_SCHOOL_ID);
            data.add(map3);
            Map<String, String> map4 = new HashMap<String, String>();
            map4.put("key", "设备编号");
            map4.put("value", String.valueOf(nav.getApiDeviceCode()));
            map4.put("col", NavigationInfo._API_DEVICE_CODE);
            data.add(map4);
            Map<String, String> map5 = new HashMap<String, String>();
            map4.put("key", "封面图片比例");
            int imgScaling = (int) (nav.getImgScaling() * 100);
            map4.put("value", String.valueOf(imgScaling));
            map4.put("col", NavigationInfo._);
            data.add(map4);*/
        }
        return data;
    }

    private void setData(String keyValue, Object valueValue,
                         String colValue, List<Map<String, String>> data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", keyValue);
        map.put("value", String.valueOf(valueValue));
        map.put("col", colValue);
        data.add(map);
    }
}
