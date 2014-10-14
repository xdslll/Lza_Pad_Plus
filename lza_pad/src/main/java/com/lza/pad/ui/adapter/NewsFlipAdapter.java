package com.lza.pad.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.NavigationInfo;
import com.lza.pad.core.db.model.News;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-21.
 */
public class NewsFlipAdapter extends BaseAdapter {

    private Context mContext;
    private List<News> mNews;
    private NavigationInfo mNav;

    public NewsFlipAdapter(Context context, NavigationInfo nav, List<News> news) {
        this.mContext = context;
        this.mNav = nav;
        this.mNews = news;
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public Object getItem(int position) {
        return mNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = mNews.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.news_item, null);
            viewHolder.mTxtType = (TextView) convertView.findViewById(R.id.news_item_type);
            viewHolder.mTxtPubDate = (TextView) convertView.findViewById(R.id.news_item_publish_date);
            //viewHolder.mWebView = (WebView) convertView.findViewById(R.id.news_item_content);
            //viewHolder.mTxtContent = (TextView) convertView.findViewById(R.id.news_item_content);
            viewHolder.mWebView = (WebView) convertView.findViewById(R.id.news_item_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTxtType.setText(mNav.getName());
        if (news != null) {
            //viewHolder.mTxtPubDate.setText(news.getPubdate());
            viewHolder.mWebView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);

            //Spanned spanned = Html.fromHtml(news.getContent());
            //viewHolder.mTxtContent.setText(spanned);
        }
        return convertView;
    }

    public static class ViewHolder {
        TextView mTxtPubDate, mTxtType;//, mTxtContent;
        WebView mWebView;
    }
}
