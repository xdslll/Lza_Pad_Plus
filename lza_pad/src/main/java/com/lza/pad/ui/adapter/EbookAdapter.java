package com.lza.pad.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.NetworkImageView;
import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.utils.Consts;
import com.lza.pad.core.utils.RuntimeUtility;
import com.lza.pad.lib.support.network.VolleySingleton;
import com.lza.pad.ui.drawable.FastBitmapDrawable;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
public class EbookAdapter extends BaseAdapter implements Consts {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Ebook> mEbooks;
    private NavigationInfo mNavInfo;
    private final Bitmap mDefaultCoverBitmap;
    private final FastBitmapDrawable mDefaultCover;
    private final int W;
    private final int H;
    private final int mRowNumber, mColNumber, mItemMaxW, mItemMaxH;
    private final float mImgScaling;

    public EbookAdapter(Context context, List<Ebook> ebooks, NavigationInfo navInfo, int w, int h) {
        this.mContext = context;
        this.mEbooks = ebooks;
        this.mNavInfo = navInfo;
        this.mInflater = LayoutInflater.from(mContext);

        this.W = w;
        this.H = h;
        this.mRowNumber = navInfo.getDataRowNumber();
        this.mColNumber = navInfo.getDataColumnNumber();
        this.mItemMaxW = W / mColNumber;
        this.mItemMaxH = H / mRowNumber;
        this.mImgScaling = navInfo.getImgScaling();
        setUnknowCover(context, mItemMaxW, mItemMaxH, mImgScaling);
        this.mDefaultCoverBitmap = getCache(KEY_CACHE_UNKNOWN_COVER);
        this.mDefaultCover = new FastBitmapDrawable(mDefaultCoverBitmap);
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
            if (mNavInfo.getRunningMode() == 0) {
                convertView = mInflater.inflate(R.layout.shelf_book_db, null);
            } else {
                convertView = mInflater.inflate(R.layout.shelf_book_net, null);
            }
            convertView.setTag(holder);
        } else {
            holder = (BookViewHolder) convertView.getTag();
        }

        /*int maxW = W / mNavInfo.getDataColumnNumber();
        int maxH = H / mNavInfo.getDataRowNumber();*/
        //int width = (int) (maxW * mImgScaling);
        //int height = (int) (maxH * mImgScaling);
        holder.imgView = (ImageView) convertView.findViewById(R.id.title);

        if (mNavInfo.getRunningMode() == 0) {
            holder.imgView.setLayoutParams(new AbsListView.LayoutParams(mItemMaxW, mItemMaxH));
            holder.imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            String filePath = RuntimeUtility.getEbookImageFilePath(ebook);
            Bitmap bitmap = null;
            if (filePath != null) {
                //bitmap = BitmapFactory.decodeFile(filePath);
                setCache(filePath, mItemMaxW, mItemMaxH, mImgScaling);
                bitmap = getCache(filePath);
                if (bitmap != null) {
                    holder.imgView.setImageBitmap(bitmap);
                } else {
                    holder.imgView.setImageDrawable(mDefaultCover);
                }
            } else {
                holder.imgView.setImageDrawable(mDefaultCover);
            }
        } else {
            holder.container = (LinearLayout) convertView.findViewById(R.id.shelf_container);
            holder.container.setLayoutParams(new AbsListView.LayoutParams(mItemMaxW, mItemMaxH));
            /*int width = (int) (mItemMaxW * mImgScaling);
            int height = (int) (mItemMaxH * mImgScaling);*/

            double bW = mItemMaxW * mImgScaling;
            double bH = (double) mItemMaxH / mItemMaxW * bW;

            if (bH > mItemMaxH * mImgScaling) {
                bH = mItemMaxH * mImgScaling;
                bW = (double) mItemMaxW / mItemMaxH * bH;
            }

            String url = RuntimeUtility.getEbookImageUrl(ebook);
            //AppLogger.e("url --> " + url);
            if (TextUtils.isEmpty(url)) {
                holder.imgView2 = (ImageView) convertView.findViewById(R.id.title2);
                holder.imgView2.setLayoutParams(new LinearLayout.LayoutParams((int) bW, (int) bH));
                holder.imgView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.imgView.setVisibility(View.GONE);
                holder.imgView2.setVisibility(View.VISIBLE);
                holder.imgView2.setImageResource(R.drawable.ebook_list_item_no_cover);
            } else {
                holder.imgView.setLayoutParams(new LinearLayout.LayoutParams((int) bW, (int) bH));
                holder.imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ((NetworkImageView) holder.imgView).setDefaultImageResId(R.drawable.ebook_list_item_no_cover);
                ((NetworkImageView) holder.imgView).setErrorImageResId(R.drawable.ebook_list_item_no_cover);
                ((NetworkImageView) holder.imgView).setImageUrl(url, VolleySingleton.getInstance(mContext).getImageLoader());
            }
        }
        return convertView;
    }

    /**
     * 生成图片缓存
     *
     * @param filePath 文件路径
     * @param maxW 单元格宽度
     * @param maxH 单元格高度
     */
    public static void setCache(String filePath, int maxW, int maxH, float imgScaling) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDither = false;
        opt.inPurgeable = true;
        opt.inTempStorage = new byte[24 * 1024];
        //opt.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opt);
        bitmap = zoomInOrOutBitmap(bitmap, maxW, maxH, imgScaling);
        if (filePath != null && bitmap != null) {
            setCache(filePath, bitmap);
        }
    }

    public static Bitmap zoomInOrOutBitmap(Bitmap bitmap, int maxW, int maxH, float imgScaling) {
        //计算缩小或放大比例
        if (bitmap != null) {
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();

            double newBmpW = maxW * imgScaling;
            double newBmpH = (double) bmpH / bmpW * newBmpW;

            if (newBmpH > maxH * imgScaling) {
                newBmpH = maxH * imgScaling;
                newBmpW = (double) bmpW / bmpH * newBmpH;
            }
            if (bmpW > 0 && bmpH >0) {
                float sx = (float) newBmpW / bmpW;
                float sy = (float) newBmpH / bmpH;
                Matrix matrix = new Matrix();
                matrix.postScale(sx, sy);
                return Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
            }
        }
        return null;
    }

    public static void setUnknowCover(Context context, int maxW, int maxH, float imgScaling) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDither = false;
        opt.inPurgeable = true;
        opt.inTempStorage = new byte[24 * 1024];
        //opt.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ebook_list_item_no_cover, opt);
        bitmap = zoomInOrOutBitmap(bitmap, maxW, maxH, imgScaling);
        if (bitmap != null) {
            setCache(KEY_CACHE_UNKNOWN_COVER, bitmap);
        }
    }

    public static void setCache(String key, Bitmap value) {
        sCache.put(key, value);
    }
    public static Bitmap getCache(String key) {
        return sCache.get(key);
    }

    public static LruCache<String, Bitmap> sCache = new LruCache<String, Bitmap>(100);
}
