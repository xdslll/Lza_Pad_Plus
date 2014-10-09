package com.lza.pad.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;
import com.lza.pad.core.db.model.EbookContent;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-19.
 */
public class HotBookContentAdapter extends BaseAdapter {

    private int[] mTag = new int[] {
            R.string.ebook_content_tag_abstract,
            //R.string.ebook_content_tag_directory,
            R.string.ebook_content_tag_library
    };

    private Context mContext;
    private Ebook mEbook;
    private List<EbookContent> mEbookContents;
    private EbookContent mEbookContent = null;

    public HotBookContentAdapter(Context context, Ebook ebook, List<EbookContent> ebookContents) {
        this.mContext = context;
        this.mEbook = ebook;
        this.mEbookContents = ebookContents;
        if (mEbookContents != null && mEbookContents.size() > 0) {
            this.mEbookContent = mEbookContents.get(0);
        }
    }

    @Override
    public int getCount() {
        return mTag.length;
    }

    @Override
    public Object getItem(int position) {
        return mTag[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hotbook_content_item, null);
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.ebook_content_item_layout);
            holder.mTitleBar = (LinearLayout) convertView.findViewById(R.id.ebook_content_item_title_layout);
            holder.mTitleButton = (ImageView) convertView.findViewById(R.id.ebook_content_item_btn);
            holder.mTitleText = (TextView) convertView.findViewById(R.id.ebook_content_item_title);
            holder.mWebView = (WebView) convertView.findViewById(R.id.ebook_content_item_webview);
            holder.mContentText = (TextView) convertView.findViewById(R.id.ebook_content_item_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        /*holder.mTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mContentText.getVisibility() == View.VISIBLE) {
                    holder.mContentText.setVisibility(View.GONE);
                    holder.mTitleButton.setImageResource(R.drawable.ebook_content_collapse_btn);
                }else {
                    holder.mContentText.setVisibility(View.VISIBLE);
                    holder.mTitleButton.setImageResource(R.drawable.ebook_content_expand_btn);
                }
            }
        });*/

        holder.mTitleText.setText(mTag[position]);
        if (mEbookContent != null) {
            switch (mTag[position]) {
                case R.string.ebook_content_tag_abstract:
                    holder.mContentText.setVisibility(View.GONE);
                    holder.mWebView.setVisibility(View.VISIBLE);
                    String cnt1 = mEbookContent.getCnt1();
                    if (!TextUtils.isEmpty(cnt1)) {
                        holder.mWebView.loadDataWithBaseURL
                                (null, cnt1, "text/html", "utf-8", null);
                    }
                    break;
                case R.string.ebook_content_tag_directory:
                    holder.mContentText.setVisibility(View.VISIBLE);
                    holder.mWebView.setVisibility(View.GONE);
                    break;
                case R.string.ebook_content_tag_library:
                    holder.mContentText.setVisibility(View.GONE);
                    holder.mWebView.setVisibility(View.VISIBLE);
                    String cnt = mEbookContent.getCnt2();
                    if (!TextUtils.isEmpty(cnt)) {
                        holder.mWebView.loadDataWithBaseURL
                                (null, cnt, "text/html", "utf-8", null);
                    }
                    break;
                case R.string.ebook_content_tag_comment:
                    holder.mContentText.setVisibility(View.VISIBLE);
                    holder.mWebView.setVisibility(View.GONE);
                    break;
            }
        }

        return convertView;
    }

    private StringBuilder createContents() {
        if (mEbookContents != null) {
            StringBuilder builder = new StringBuilder();
            for (EbookContent content : mEbookContents) {
                builder.append(content.getEntitle() + "\n");
            }
            return builder;
        }
        return null;
    }

    public static class ViewHolder {
        LinearLayout mLayout, mTitleBar;
        ImageView mTitleButton;
        TextView mTitleText, mContentText;
        WebView mWebView;
    }
}
