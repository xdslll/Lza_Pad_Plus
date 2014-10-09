package com.lza.pad.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class JournalsContentAdapter extends BaseAdapter {

    private int[] mTag = new int[] {
            R.string.ebook_content_tag_abstract,
            R.string.ebook_content_tag_directory
    };

    private Context mContext;
    private Ebook mEbook;
    private List<EbookContent> mEbookContents;

    public JournalsContentAdapter(Context context, Ebook ebook, List<EbookContent> ebookContents) {
        this.mContext = context;
        this.mEbook = ebook;
        this.mEbookContents = ebookContents;
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
            convertView = inflater.inflate(R.layout.ebook_content_item, null);
            holder.mLayout = (LinearLayout) convertView.findViewById(R.id.ebook_content_item_layout);
            holder.mTitleBar = (LinearLayout) convertView.findViewById(R.id.ebook_content_item_title_layout);
            holder.mTitleButton = (ImageView) convertView.findViewById(R.id.ebook_content_item_btn);
            holder.mTitleText = (TextView) convertView.findViewById(R.id.ebook_content_item_title);
            holder.mContentText = (TextView) convertView.findViewById(R.id.ebook_content_item_text);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTitleBar.setOnClickListener(new View.OnClickListener() {
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
        });

        holder.mTitleText.setText(mTag[position]);
        switch (mTag[position]) {
            case R.string.ebook_content_tag_abstract:
                if (mEbook != null) {
                    holder.mContentText.setText(mEbook.getAbstractStr());
                }
                break;
            case R.string.ebook_content_tag_directory:
                StringBuilder builder = createContents();
                if (builder != null) {
                    holder.mContentText.setText(builder);
                }
                break;
            case R.string.ebook_content_tag_library:
                holder.mContentText.setText("");
                break;
            case R.string.ebook_content_tag_comment:
                holder.mContentText.setText("");
                break;
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
    }
}
