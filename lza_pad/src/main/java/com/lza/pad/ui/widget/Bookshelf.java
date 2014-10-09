package com.lza.pad.ui.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.utils.ResourceConsts;

import java.util.List;

/**
 * 表示整个书架,有排和列构成,m排书架包含n列图书
 *
 * @author xiads
 * @Date 14-9-12.
 */
public class Bookshelf extends LinearLayout implements ResourceConsts.ImageResource {

    private BookSize mSize;

    public Bookshelf(Context context, BookSize size,
                     BookShelfItem.OnBookClickListener listener, List<Ebook> ebooks) {
        super(context);

        this.mSize = size;

        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(mSize.containerWidth, mSize.containerHeight));

        //计算一排书架的尺寸
        mSize.itemWidth = mSize.containerWidth;
        mSize.itemHeight = mSize.containerHeight / mSize.rowNumber;

        //计算书架图片的最佳尺寸
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(
                context.getResources(), IMG_BOOK_LOADING, opts);
        int orgWidth = opts.outWidth;
        int orgHeight = opts.outHeight;
        mSize.imgHeight = (int) (mSize.itemHeight * 0.75);
        mSize.imgWidth = mSize.imgHeight * orgWidth / orgHeight;

        //计算图片的边距
        mSize.imgHorizontalSpacing = (mSize.itemWidth -
                mSize.imgWidth * mSize.columnNumber) / (mSize.columnNumber + 1);
        mSize.imgBottomSpacing = (int) (mSize.itemHeight * 0.2);

        for (int rowIndex = 0; rowIndex < mSize.rowNumber; rowIndex++) {
            mSize.rowIndex = rowIndex;
            BookShelfItem item = new BookShelfItem(context, mSize, listener, ebooks);
            addView(item);
        }
    }

    /*public Bookshelf(Context context, Size size, BookShelfItem.OnBookClickListener listener) {
        //this(context, size.containerWidth, size.containerHeight, size.rowNumber, size.columnNumber, listener);
        super(context);
        this.mSize = size;

        setOrientation(VERTICAL);
        setLayoutParams(new ViewGroup.LayoutParams(mSize.containerWidth, mSize.containerHeight));

        //计算一排书架的尺寸
        mSize.itemWidth = mSize.containerWidth;
        mSize.itemHeight = mSize.containerHeight / mSize.rowNumber;

        //计算书架图片的最佳尺寸
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(
                context.getResources(), BookShelfItem.IMG_BOOK_LOADING, opts);
        int orgWidth = opts.outWidth;
        int orgHeight = opts.outHeight;
        mSize.imgHeight = (int) (mSize.itemHeight * 0.75);
        mSize.imgWidth = mSize.imgHeight * orgWidth / orgHeight;

        //计算图片的边距
        mSize.imgHorizontalSpacing = (mSize.itemWidth -
                mSize.imgWidth * mSize.columnNumber) / (mSize.columnNumber + 1);
        mSize.imgBottomSpacing = (int) (mSize.itemHeight * 0.2);

        for (int rowIndex = 0; rowIndex < mSize.rowNumber; rowIndex++) {
            mSize.rowIndex = rowIndex;
            BookShelfItem item = new BookShelfItem(context, mSize, listener);
            addView(item);
        }

    }*/

    public void updateImageView(List<Ebook> ebooks) {
        for (int index = 0; index < mSize.rowNumber; index++) {
            BookShelfItem item = getBookShelfItem(index);
            item.updateImageView(ebooks, index);
        }
    }

    public BookShelfItem getBookShelfItem(int rowNumber) {
        if (getChildCount() > rowNumber)
            return (BookShelfItem) getChildAt(rowNumber);
        return null;
    }

    public ImageView getItem(int rowNumber, int colNumber) {
        BookShelfItem item = getBookShelfItem(rowNumber);
        if (item != null) {
            return item.getItem(colNumber);
        }
        return null;
    }



}
