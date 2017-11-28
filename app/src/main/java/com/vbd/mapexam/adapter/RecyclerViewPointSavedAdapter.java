package com.vbd.mapexam.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vbd.mapexam.R;
import com.vbd.mapexam.listener.BtnEditOnclickCallBack;
import com.vbd.mapexam.listener.DeletePointCallback;
import com.vbd.mapexam.model.PointSaved;
import com.vbd.mapexam.presenter.DeletePoint;
import com.vietbando.vietbandosdk.annotations.Icon;
import com.vietbando.vietbandosdk.annotations.IconFactory;
import com.vietbando.vietbandosdk.annotations.Marker;
import com.vietbando.vietbandosdk.annotations.MarkerOptions;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;

import static com.vbd.mapexam.view.activity.MainActivity.bottomPointSaved;
import static com.vbd.mapexam.view.activity.MainActivity.btnShow;
import static com.vbd.mapexam.view.fragment.VbdMapFragment.map;

public class RecyclerViewPointSavedAdapter extends RecyclerView.Adapter<RecyclerViewPointSavedAdapter.RecyclerViewHolder> {

    private ArrayList<PointSaved> data = new ArrayList<>();
    SharedPreferences userInfoPf;
    Context context;
    BtnEditOnclickCallBack btnEditOnclickCallBack;

    public RecyclerViewPointSavedAdapter(ArrayList<PointSaved> data, SharedPreferences sharedPreferences,
                                         Context context,BtnEditOnclickCallBack btnEditOnclickCallBack) {
        this.data = data;
        this.userInfoPf = sharedPreferences;
        this.context = context;
        this.btnEditOnclickCallBack = btnEditOnclickCallBack;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.point_saved_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.txtLocationName.setText(data.get(position).getNamePoint());
        holder.txtAdress.setText(data.get(position).getAddress());

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double lat = data.get(position).getLat();
                Double lng = data.get(position).getLg();

                map.clear();
                Icon icon = IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_favorite);
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(data.get(position).getNamePoint())
                        .snippet(data.get(position).getAddress())
                        .icon(icon));

                map.easeCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12));
                bottomPointSaved.dismiss();
                btnShow.setVisibility(View.VISIBLE);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeletePoint(data.get(position).getId(), userInfoPf, new DeletePointCallback() {
                    @Override
                    public void onDeleteFinish(Boolean isSuccess) {
                        if (isSuccess) {
                            data.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, data.size());
                        }else {
                            Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_LONG).show();
                        }
                    }
                }).execute();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditOnclickCallBack.onBtnEditClick(data.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocationName;
        TextView txtAdress;
        ImageButton btnDelete;
        ImageButton btnEdit;
        LinearLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtLocationName = (TextView) itemView.findViewById(R.id.textViewLocationName);
            txtAdress = (TextView) itemView.findViewById(R.id.textViewLocationAdress);
            line = (LinearLayout) itemView.findViewById(R.id.line);
            btnDelete = (ImageButton) itemView.findViewById(R.id.button_deletePointSaved);
            btnEdit = (ImageButton) itemView.findViewById(R.id.button_editPoint);
        }
    }

}
