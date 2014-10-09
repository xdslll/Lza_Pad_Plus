package com.lza.pad.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.lza.pad.R;
import com.lza.pad.core.db.model.EbookRequest;
import com.lza.pad.core.request.OnResponseListener;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.ui.fragment.preference.BasePreferenceActivity;
import com.lza.pad.ui.widget.DigitalClock;
import com.lza.pad.ui.widget.WeatherPanel;

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
    private WeatherPanel mWeatherPanel;
    private TextView mTxtDate;

    private static final int GET_PREFERENCE = 0x001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.header, container, false);
        mClock = (DigitalClock) view.findViewById(R.id.header_digital_clock);
        mWeatherPanel = (WeatherPanel) view.findViewById(R.id.header_weather_panel);
        mTxtDate = (TextView) view.findViewById(R.id.header_date);
        mClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(getActivity(), MainPreferenceActivity.class);
                //intent.setClass(getActivity(), GlobalPreferenceActivity.class);
                intent.setClass(getActivity(), BasePreferenceActivity.class);
                //startActivityForResult(intent, GET_PREFERENCE);
                startActivity(intent);
            }
        });
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
        if (mWeatherPanel != null)
            mWeatherPanel.sendUpdateWeatherRequest();
        if (mTxtDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 E");
            String dateStr = sdf.format(new Date());
            mTxtDate.setText(dateStr);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSuccess(EbookRequest ebookRequest) {

    }

    @Override
    public void onError(Exception error) {

    }
}
