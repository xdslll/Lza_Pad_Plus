package com.lza.pad.ui.activity.main;

import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.lza.pad.R;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.ui.activity.base.AbstractActivity;
import com.lza.pad.ui.fragment.FooterNavigationFragment;
import com.lza.pad.ui.fragment.HeaderFragment;

/**
 * 主界面，通过包含一个顶部标题栏和一个底部导航栏，中间部分根据用户需求进行替换
 *
 * @author Sam
 * @Date 14-9-11
 */
public class HomeActivity extends AbstractActivity {

    public static final String HEADER_FRAGMENT_TAG = "header";
    public static final String CONTAINER_FRAGMENT_TAG = "container";
    public static final String FOOTER_FRAGMENT_TAG = "footer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_header, new HeaderFragment(), HEADER_FRAGMENT_TAG);
        ft.replace(R.id.home_footer, new FooterNavigationFragment(), FOOTER_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondPressed = System.currentTimeMillis();
            if (secondPressed - mFirstBackPressed > MAX_DELAY) {
                ToastUtilsSimplify.show("再按一次退出程序");
                mFirstBackPressed = secondPressed;
            } else {
                System.exit(0);
            }
        }
        return true;
    }

    private int MAX_DELAY = 1000;
    private long mFirstBackPressed = 0;
}
