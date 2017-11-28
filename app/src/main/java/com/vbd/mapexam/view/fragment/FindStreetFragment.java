package com.vbd.mapexam.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.vbd.mapexam.R;

import static com.vbd.mapexam.view.activity.MainActivity.CODE_SEARCH;
import static com.vbd.mapexam.view.activity.MainActivity.backToMapFragment;
import static com.vbd.mapexam.view.activity.MainActivity.fm;
import static com.vbd.mapexam.view.activity.MainActivity.getSearchPointFragment;

/**
 * Created by tandat on 11/4/2017.
 */

public class FindStreetFragment extends Fragment {

    EditText edtSearchA, edtSearchB;
    ImageButton btnFindStreetBack;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.stub_find_street, container, false);

        edtSearchA = (EditText) view.findViewById(R.id.editext_searchA);
        edtSearchB = (EditText) view.findViewById(R.id.editext_searchB);
        btnFindStreetBack = (ImageButton) view.findViewById(R.id.button_findStreetBack);
        edtSearchA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE_SEARCH = 1;
                getSearchPointFragment();
            }
        });
        edtSearchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODE_SEARCH = 2;
                getSearchPointFragment();
            }
        });
        btnFindStreetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.popBackStack();
                backToMapFragment();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = getArguments().getString("location_name");

            if (CODE_SEARCH == 1) {
                edtSearchA.setText(name);

            } else if (CODE_SEARCH == 2) {
                edtSearchB.setText(name);

            }
        }

    }
}
