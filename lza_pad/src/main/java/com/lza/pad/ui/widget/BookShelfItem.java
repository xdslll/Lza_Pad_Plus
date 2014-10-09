package com.lza.pad.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.utils.ResourceConsts;

import java.util.List;

/**
 * 表示一层书架
 *
 * @author xiads
 * @Date 14-9-12.
 */
public class BookShelfItem extends LinearLayout implements ResourceConsts.ImageResource {

    /**
     * 记录书架控件的尺寸
     */
    private BookSize mSize;

    /**
     * 点击每本书的事件
     */
    private OnBookClickListener mListener;



    public BookShelfItem(Context context, final BookSize size,
                         OnBookClickListener listener, List<Ebook> ebooks) {
        super(context);

        mSize = size;
        setOnBookClickListener(listener);

        //设置书架的整体样式
        setLayoutParams(new ViewGroup.LayoutParams(mSize.itemWidth, mSize.itemHeight));
        setBackgroundResource(IMG_BOOK_SHELF_BG);
        setGravity(Gravity.BOTTOM);
        setOrientation(HORIZONTAL);

        for (int itemId = 0; itemId < mSize.columnNumber; itemId++) {

            ImageView bookImageView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    mSize.imgWidth, mSize.imgHeight);
            params.setMargins(mSize.imgHorizontalSpacing,
                    0, 0, mSize.imgBottomSpacing);

            bookImageView.setLayoutParams(params);
            //bookImageView.setImageResource(IMG_BOOK_LOADING);
            bookImageView.setBackgroundResource(IMG_BOOK_BG);

            //获取当前图片的坐标
            final int rowIndex = mSize.rowIndex;
            final int colIndex = itemId;
            bookImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onBookClick(v, rowIndex, colIndex);
                }
            });

            //更换图片
            int startEbookIndex = rowIndex * mSize.columnNumber;
            int currentEbookIndex = startEbookIndex + colIndex;
            if (ebooks.size() <= currentEbookIndex) {
                break;
            }
            Ebook ebook = ebooks.get(currentEbookIndex);
            //String imgPath = ebook.getImgPath();
            String imgPath = RuntimeUtility.getEbookImageFilePath(ebook);
            //Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), imgPath);
            if (drawable != null && drawable.getBitmap() != null) {
                //bookImageView.setImageBitmap(bitmap);
                bookImageView.setImageDrawable(drawable);
            } else {
                bookImageView.setImageResource(IMG_BOOK_NO_COVER);
                //bookImageView.setImageResource(IMG_BOOK_LOADING);
            }
            addView(bookImageView);
        }
    }

    private final int UPDATE_IMAGE_VIEW = 0x001;
    private final String KEY_EBOOK = "ebook";
    private final String KEY_IMG_INDEX = "img_index";

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_IMAGE_VIEW:
                    Bundle arg = msg.getData();
                    if (arg != null) {
                        Ebook ebook = arg.getParcelable(KEY_EBOOK);
                        String imgPath = ebook.getImgPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                        int index = arg.getInt(KEY_IMG_INDEX);
                        ImageView imageView = getItem(index);
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 释放内存
     */
    public void clear() {
        int colNumber = mSize.columnNumber;
        for (int i = 0; i < colNumber; i++) {
            ImageView imageView = getItem(i);
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = drawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    drawable = null;
                }
            }
            imageView = null;
        }
        removeAllViews();
    }

    public void updateImageView(List<Ebook> ebooks, int rowIndex) {

        int startIndex = rowIndex * mSize.columnNumber;
        int endIndex = startIndex + mSize.columnNumber;

        for (int index = startIndex; index < endIndex; index++) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_EBOOK, ebooks.get(index));
            bundle.putInt(KEY_IMG_INDEX, index % 4);
            msg.setData(bundle);
            msg.what = UPDATE_IMAGE_VIEW;
            mHandler.sendMessage(msg);
        }
    }

    public ImageView getItem(int index) {
        if (getChildCount() > index) {
            return (ImageView) getChildAt(index);
        }
        return null;
    }

    public interface OnBookClickListener {
        public void onBookClick(View v, int rowIndex, int colIndex);
    }

    public void setOnBookClickListener(OnBookClickListener listener) {
        this.mListener = listener;
    }
}
