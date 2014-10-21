package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lza.pad.R;
import com.lza.pad.lib.support.utils.UniversalUtility;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/15/14.
 */
public class BookSearchFragment extends AbstractFragment {

    private EditText mEdtKeyword;
    private ImageButton mImgSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_search, container, false);
        mEdtKeyword = (EditText) view.findViewById(R.id.ebook_search_keyword);
        mImgSearch = (ImageButton) view.findViewById(R.id.ebook_search_search);
        mImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = mEdtKeyword.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    UniversalUtility.showDialog(getActivity(),
                            R.string.ebook_search_keyword_dialog_title,
                            R.string.ebook_search_keyword_dialog_message);
                } else {
                    mNavInfo.setSearchKeyWord(keyword);
                    mNavInfo.setSearchType(0);
                    mNavInfo.setSourceType(0);
                    mNavInfo.setApiControlPar(REQUEST_CONTROL_TYPE_SEARCH);
                    mNavInfo.setApiActionPar(HOT_BOOK_ACTION_CONTENT);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    BookSearchListFragment fragment = new BookSearchListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(KEY_NAVIGATION_INFO, mNavInfo);
                    fragment.setArguments(bundle);
                    ft.add(R.id.home_container, fragment, TAG_SEARCH_BOOK_LIST);
                    ft.commit();
                }
            }
        });
        return view;
    }
}
