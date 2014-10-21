package com.lza.pad.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lza.pad.R;
import com.lza.pad.core.db.model.Ebook;

import java.util.List;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/16/14.
 */
public class EbookSearchAdapter extends BaseAdapter {

    private Context mContext;
    private List<Ebook> mEbooks;

    public EbookSearchAdapter(Context context, List<Ebook> ebooks) {
        this.mContext = context;
        this.mEbooks = ebooks;
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
        TextView txtTitle, txtPub;
        Ebook ebook = mEbooks.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.ebook_search_list_item, null);
        }

        txtTitle = (TextView) convertView.findViewById(R.id.ebook_search_list_item_title);
        txtPub = (TextView) convertView.findViewById(R.id.ebook_search_list_item_pub);
        txtTitle.setText((position + 1) + ".\t" + ebook.getTitle());
        txtPub.setText(ebook.getPublisher());
        return convertView;
    }
}
