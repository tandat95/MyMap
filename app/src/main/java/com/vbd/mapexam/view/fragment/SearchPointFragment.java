package com.vbd.mapexam.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vbd.mapexam.R;


/**
 * Created by tandat on 11/4/2017.
 */

public class SearchPointFragment extends Fragment{


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.stub_suggess_search, container, false);


        return view;
    }
}
