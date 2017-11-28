package com.vbd.mapexam.view.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vbd.mapexam.R;
import com.vbd.mapexam.adapter.RecyclerViewPointSavedAdapter;

import static com.vbd.mapexam.view.activity.MainActivity.btnShow;

/**
 * Created by sonu on 30/08/16.
 */

@SuppressLint("ValidFragment")
public class BottomPointSavedFragment extends BottomSheetDialogFragment {

    RecyclerViewPointSavedAdapter adapter;

    public BottomPointSavedFragment(RecyclerViewPointSavedAdapter adapter) {
        this.adapter = adapter;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                btnShow.setTag( "point_saved");
                btnShow.setText("Hiển thị danh sách");
                btnShow.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {




        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_point_saved, null);
        dialog.setContentView(contentView);

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView_pointSaved);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

}
