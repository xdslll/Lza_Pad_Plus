package com.lza.pad.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lza.pad.R;

/**
 * Say something about this class
 *
 * @author xiads
 * @Date 14-9-14.
 */
public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ebook_list, container, false);
        return view;
    }
}
