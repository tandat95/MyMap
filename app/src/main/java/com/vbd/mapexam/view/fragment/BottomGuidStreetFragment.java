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
import android.widget.ImageButton;

import com.vbd.mapexam.R;
import com.vbd.mapexam.adapter.RecyclerViewGuideStreetAdapter;
import com.vbd.mapexam.listener.BtnSaveStreetOnclickCallback;

import static com.vbd.mapexam.view.activity.MainActivity.btnShow;

/**
 * Created by sonu on 30/08/16.
 */

@SuppressLint("ValidFragment")
public class BottomGuidStreetFragment extends BottomSheetDialogFragment {

    RecyclerViewGuideStreetAdapter adapter;
    BtnSaveStreetOnclickCallback btnSaveStreetOnclickCallback;

    public BottomGuidStreetFragment(RecyclerViewGuideStreetAdapter adapter, BtnSaveStreetOnclickCallback btnSaveStreetOnclickCallback) {
        this.adapter = adapter;
        this.btnSaveStreetOnclickCallback = btnSaveStreetOnclickCallback;
    }

    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                btnShow.setTag("guid_street");
                btnShow.setVisibility(View.VISIBLE);
                btnShow.setText("Xem chỉ dẫn");

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

        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_guide_street, null);
        dialog.setContentView(contentView);

        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView_guideStreet);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        final ImageButton btnSaveStreet = (ImageButton) contentView.findViewById(R.id.button_saveStreet);
        btnSaveStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveStreetOnclickCallback.onBtnSaveStreetClick();
            }
        });
    }

}
