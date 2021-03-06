package com.lza.pad.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lza.pad.R;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.RuntimeUtility;

import java.io.File;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/11/14.
 */
public class HelpFragment extends AbstractFragment implements Consts {

    private final String FILE_NAME = "help.jpg";
    private final String FILE_PREFIX = "help";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        ImageView helpImg = new ImageView(getActivity());
        helpImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        helpImg.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //先加载本地的图片，如果加载不到，则加载学校编号的图片，如果还加载不到就加载默认图片
        File imgFileDir = RuntimeUtility.createImgCacheDir(mNavInfo);
        if (!imgFileDir.exists()) {
            imgFileDir.mkdirs();
        }
        File imgFile = new File(imgFileDir, FILE_NAME);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        if (bitmap != null) {
            helpImg.setImageBitmap(bitmap);
        } else {
            int schoolId = mNavInfo.getApiSchoolIdPar();
            String resName = FILE_PREFIX + schoolId;
            String packageName = getActivity().getPackageName();
            int resId = getResources().getIdentifier(resName, "drawable", packageName);
            try {
                Drawable drawable = getResources().getDrawable(resId);
                if (drawable != null) {
                    helpImg.setImageDrawable(drawable);
                }
            } catch(Exception ex) {
                helpImg.setImageResource(R.drawable.help);
            }
        }
        linearLayout.setBackgroundResource(R.drawable.ebook_list_bg);
        linearLayout.addView(helpImg);
        return linearLayout;
    }
}
