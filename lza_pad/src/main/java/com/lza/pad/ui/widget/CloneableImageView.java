package com.lza.pad.ui.widget;

import android.content.Context;
import android.widget.ImageView;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-13.
 */
public class CloneableImageView extends ImageView implements Cloneable {

    public CloneableImageView(Context context) {
        super(context);
    }

    @Override
    protected CloneableImageView clone() {
        CloneableImageView img = null;
        try {
            img = (CloneableImageView) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return img;
    }
}
