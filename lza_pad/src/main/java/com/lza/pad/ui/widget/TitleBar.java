package com.lza.pad.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.ToastUtilsSimplify;
import com.lza.pad.lib.support.utils.UniversalUtility;

/**
 * 标题栏控件
 *
 * @author xiads
 * @Date 14-9-11.
 */
public class TitleBar extends FrameLayout {

    Context mContext;
    TextView mTxtTitle, mTxtSubject;

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ctr_title_bar, this);

        mTxtTitle = (TextView) findViewById(R.id.title_bar_title);
        mTxtSubject = (TextView) findViewById(R.id.title_bar_subject);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.title_bar);
        String strTitle = typedArray.getString(R.styleable.title_bar_titleText);
        boolean isTitleVisble = typedArray.getBoolean(R.styleable.title_bar_titleVisibility, false);
        boolean isSubjectVisible = typedArray.getBoolean(R.styleable.title_bar_subjectVisiblity, false);
        setTitle(strTitle);
        UniversalUtility.setViewVisbility(mTxtTitle, isTitleVisble);
        UniversalUtility.setViewVisbility(mTxtSubject, isSubjectVisible);
        typedArray.recycle();

        setOnSujectClickListener();
    }

    /**
     * 根据导航栏数据,判断是否显示标题栏
     *
     * @param ni
     */
    public void handleTitleBar(NavigationInfo ni) {
        int hasTitleBar = ni.getHasTitleBar();
        setTitleBarVisibility(hasTitleBar);
        if (hasTitleBar == 1) {
            int hasTitleButton = ni.getHasTitleButton();
            String title = ni.getName();
            setTitleButtonVisibility(hasTitleButton);
            setTitle(title);
        }
    }

    public void setTitle(String strTitle) {
        if (!TextUtils.isEmpty(strTitle) && mTxtTitle != null)
            mTxtTitle.setText(strTitle);
    }

    public void setOnSujectClickListener(OnClickListener listener) {
        mTxtSubject.setOnClickListener(listener);
    }

    public void setTitleBarVisibility(int isVisble) {
        UniversalUtility.setViewVisibility(this, isVisble);
    }

    public void setTitleButtonVisibility(int isVisble) {
        UniversalUtility.setViewVisibility(mTxtSubject, isVisble);
    }

    public void setOnSujectClickListener() {
        mTxtSubject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("学科")
                        .setItems(new String[]{"文学", "数学", "物理", "计算机"},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToastUtilsSimplify.show("item" + which);
                                }
                            })
                        .show();
            }
        });
    }
}
