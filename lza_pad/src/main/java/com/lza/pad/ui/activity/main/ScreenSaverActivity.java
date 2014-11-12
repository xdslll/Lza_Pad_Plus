package com.lza.pad.ui.activity.main;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lza.pad.R;
import com.lza.pad.core.db.dao.NavigationInfoDao;
import com.lza.pad.ui.activity.base.AbstractActivity;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 11/10/14.
 */
public class ScreenSaverActivity extends AbstractActivity {

    private final String FILE_NAME = "help.jpg";
    private final String FILE_PREFIX = "help";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        ImageView helpImg = new ImageView(this);
        helpImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        helpImg.setScaleType(ImageView.ScaleType.FIT_XY);

        //先加载本地的图片，如果加载不到，则加载学校编号的图片，如果还加载不到就加载默认图片
        int schoolId = NavigationInfoDao.getInstance().queryNotClosedBySortId(1).getApiSchoolIdPar();
        String resName = FILE_PREFIX + schoolId;
        String packageName = this.getPackageName();
        int resId = getResources().getIdentifier(resName, "drawable", packageName);
        try {
            Drawable drawable = getResources().getDrawable(resId);
            if (drawable != null) {
                helpImg.setImageDrawable(drawable);
            }
        } catch(Exception ex) {
            helpImg.setImageResource(R.drawable.help);
        }

        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.addView(helpImg);
        helpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setContentView(linearLayout);
    }
}
