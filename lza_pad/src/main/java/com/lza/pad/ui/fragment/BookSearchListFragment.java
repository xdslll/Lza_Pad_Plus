package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.lza.pad.R;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 10/15/14.
 */
public class BookSearchListFragment extends AbstractListFragment {

    private ImageButton mImgBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_search_list, container, false);

        mImgBack = (ImageButton) view.findViewById(R.id.ebook_search_list_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(BookSearchListFragment.this).commit();
            }
        });

        setListAdapter(new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, new String[]{"A", "B", "C"}
        ));
        return view;
    }
}
