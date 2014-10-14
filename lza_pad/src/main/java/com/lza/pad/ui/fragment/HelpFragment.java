package com.lza.pad.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    private final String mFileName = "help.jpg";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView helpImg = new ImageView(getActivity());
        helpImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        File imgFileDir = RuntimeUtility.createImgCacheDir(mNavInfo);
        if (!imgFileDir.exists()) {
            imgFileDir.mkdirs();
        }
        File imgFile = new File(imgFileDir, mFileName);
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        if (bitmap != null) {
            helpImg.setImageBitmap(bitmap);
        } else {
            helpImg.setBackgroundResource(R.drawable.help2);
        }
        return helpImg;
    }
}
