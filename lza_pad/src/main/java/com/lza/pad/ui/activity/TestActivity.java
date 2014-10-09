package com.lza.pad.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lza.pad.lib.support.debug.AppLogger;
import com.lza.pad.ui.service.ApiInfo;
import com.lza.pad.ui.service.Navigation;
import com.lza.pad.ui.service.Params;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-16.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLogger.e("onCreate");
        Bundle bundle = getIntent().getExtras();
        Navigation nav = bundle.getParcelable("nav");
        int id = nav.getId();
        String name = nav.getName();
        ApiInfo apiInfo = nav.getApiInfo();
        List<Params> params = nav.getParams();
        AppLogger.e("I Got it!" + id + "," + name);
        AppLogger.e("I Got it!" + apiInfo.getId() + "," + apiInfo.getUrl());
        AppLogger.e("I Got it!" + params.get(0).getId() + "," + params.get(0).getKey() + "," + params.get(0).getValue());
        AppLogger.e("I Got it!" + params.get(1).getId() + "," + params.get(1).getKey() + "," + params.get(1).getValue());

    }
}
