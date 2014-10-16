package com.lza.pad.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
public class NewsPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<News> mNews;
    private NavigationInfo mNav;
    private List<View> mViews;

    public NewsPagerAdapter(Context context, NavigationInfo nav, List<News> news) {
        this.mContext = context;
        this.mNav = nav;
        this.mNews = news;
    }

    public NewsPagerAdapter(Context context, NavigationInfo nav, List<News> news, List<View> views) {
        this(context, nav, news);
        this.mViews = views;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        News news = mNews.get(position);
        WebView webView = (WebView) view.findViewById(R.id.news_item_content);
        TextView txtType = (TextView) view.findViewById(R.id.news_item_type);
        TextView txtPubDate = (TextView) view.findViewById(R.id.news_item_publish_date);
        TextView txtTitle = (TextView) view.findViewById(R.id.news_item_title);
        if (news != null && news.getContent() != null) {
            webView.loadDataWithBaseURL(null, news.getContent(), "text/html", "utf-8", null);
            txtPubDate.setText(news.getPubdate());
            txtType.setText(mNav.getName());
            String title = news.getTitle();
            if (!TextUtils.isEmpty(title)) {
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText(title);
            } else {
                txtTitle.setVisibility(View.GONE);
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
