package com.lza.pad.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.ui.adapter.utils.ImageUtilities;
import com.lza.pad.ui.drawable.FastBitmapDrawable;
import com.lza.pad.ui.fragment.EbookShelvesFragment;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
public class EbookAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Ebook> mEbooks;
    private final Bitmap mDefaultCoverBitmap;
    private final FastBitmapDrawable mDefaultCover;
    private final int W;
    private final int H;

    public EbookAdapter(Context context, List<Ebook> ebooks, int w, int h) {
        this.mContext = context;
        this.mEbooks = ebooks;
        this.mInflater = LayoutInflater.from(mContext);
        //BitmapFactory.Options opt = new BitmapFactory.Options();
        //opt.inSampleSize = 1;
        mDefaultCoverBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.unknown_cover);
        Matrix matrix = new Matrix();
        matrix.postScale(2.0f,2.0f);
        Bitmap temp = Bitmap.createBitmap(mDefaultCoverBitmap, 0, 0,
                mDefaultCoverBitmap.getWidth(), mDefaultCoverBitmap.getHeight(), matrix, true);
        this.mDefaultCover = new FastBitmapDrawable(temp);
        W = w;
        H = h;
    }

    public FastBitmapDrawable getDefaultCover() {
        return mDefaultCover;
    }

    @Override
    public int getCount() {
        return mEbooks.size();
    }

    @Override
    public Ebook getItem(int position) {
        return mEbooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookViewHolder holder = null;
        Ebook ebook = mEbooks.get(position);

        if (convertView == null) {
            holder = new BookViewHolder();
            convertView = mInflater.inflate(R.layout.shelf_book, null);
            convertView.setTag(holder);
        } else {
            holder = (BookViewHolder) convertView.getTag();
        }

        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.title.setLayoutParams(new AbsListView.LayoutParams(W / 4, H / 4));
        holder.title.setPadding(0, 0, 0, 30);
        //holder.title.setText(mEbooks.get(position).getName());


        //Drawable drawable = BitmapDrawable.createFromPath(ebook.getImgPath());

        String file = ebook.getImgPath();
        Bitmap bitmap = EbookShelvesFragment.getCache(file);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
        if (drawable != null) {
        /*if (drawable != null) {
            Bitmap bitmap = drawable.get
            Matrix matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            BitmapDrawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);*//**//*
            drawable = zoomDrawable(drawable,
                    (int) (drawable.getIntrinsicWidth() * 2.5),
                    (int) (drawable.getIntrinsicHeight() * 2.5));*/

            holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
        } else {
            holder.title.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                    ImageUtilities.getCachedCover(String.valueOf(position), mDefaultCover));
        }

        return convertView;
    }

    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth();   // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把 drawable 内容画到画布中
        return bitmap;
    }

    static Drawable zoomDrawable(Drawable drawable, int w, int h)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float)w / width);   // 计算缩放比例
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回
    }

}
