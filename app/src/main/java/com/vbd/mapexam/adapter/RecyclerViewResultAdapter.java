package com.vbd.mapexam.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vbd.mapexam.R;
import com.vbd.mapexam.listener.BtnToHereOnclickCallback;
import com.vbd.mapexam.listener.GuidStreetItemClickCallback;
import com.vbd.mapexam.model.SearchResultObj;
import com.vbd.mapexam.presenter.InsertPoint;
import com.vietbando.vietbandosdk.annotations.Icon;
import com.vietbando.vietbandosdk.annotations.IconFactory;
import com.vietbando.vietbandosdk.annotations.Marker;
import com.vietbando.vietbandosdk.annotations.MarkerOptions;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;

import static com.vbd.mapexam.view.activity.MainActivity.CODE_SEARCH;
import static com.vbd.mapexam.view.activity.MainActivity.bottomSearchResult;
import static com.vbd.mapexam.view.activity.MainActivity.btnShow;
import static com.vbd.mapexam.view.activity.MainActivity.fmStreet;
import static com.vbd.mapexam.view.activity.MainActivity.getFindStreetFragment;
import static com.vbd.mapexam.view.activity.MainActivity.listNamePoint;
import static com.vbd.mapexam.view.activity.MainActivity.listPoint;
import static com.vbd.mapexam.view.fragment.VbdMapFragment.map;

public class RecyclerViewResultAdapter extends RecyclerView.Adapter<RecyclerViewResultAdapter.RecyclerViewHolder> {

    private ArrayList<SearchResultObj> data = new ArrayList<>();
    Context context;
    SharedPreferences userInfoPf;
    GuidStreetItemClickCallback guidStreetItemClickCallback;
    BtnToHereOnclickCallback btnToHereOnclickCallback;


    public RecyclerViewResultAdapter(ArrayList<SearchResultObj> data, Context context, SharedPreferences userInfoPf, GuidStreetItemClickCallback guidStreetItemClickCallback,  BtnToHereOnclickCallback btnToHereOnclickCallback) {
        this.data = data;
        this.context = context;
        this.userInfoPf = userInfoPf;
        this.guidStreetItemClickCallback = guidStreetItemClickCallback;
        this.btnToHereOnclickCallback = btnToHereOnclickCallback;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final String name = data.get(position).getName();
        String adress = data.get(position).getStreet() + ", " + data.get(position).getWard() + ", "
                + data.get(position).getDistrict() + ", " + data.get(position).getProvince();
        while (adress.substring(0, 2).equals(", ")) {
            if (adress.length() > 2) {
                adress = adress.substring(2);
            } else {
                adress = "";
                break;
            }
        }

        holder.txtLocationName.setText(name);
        holder.txtAdress.setText(adress);

        final String finalAdress = adress;
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CODE_SEARCH == 0) {
                    Double lat = data.get(position).getLatitude();
                    Double lng = data.get(position).getLongitude();
                    Icon icon = IconFactory.getInstance(context).fromResource(R.drawable.ic_marker_search);
                    map.clear();
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(name)
                            .snippet(finalAdress)
                            .icon(icon));

                    map.easeCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12), 200);
                    btnShow.setVisibility(View.VISIBLE);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("location_name", name);
                    bundle.putDouble("latitude", data.get(position).getLatitude());
                    bundle.putDouble("longitude", data.get(position).getLongitude());
                    fmStreet.setArguments(bundle);
                    getFindStreetFragment(context);
                    Double lat = data.get(position).getLatitude();
                    Double lng = data.get(position).getLongitude();
                    LatLng latLng = new LatLng(lat, lng);
                    listPoint[CODE_SEARCH - 1] = latLng;
                    listNamePoint[CODE_SEARCH - 1] = name;
                    map.addMarker(new MarkerOptions()
                            .position(listPoint[CODE_SEARCH - 1])
                            .title(name)
                            .snippet(finalAdress));
                    map.easeCamera(CameraUpdateFactory.newLatLngZoom(listPoint[CODE_SEARCH - 1], 12));
                }

                bottomSearchResult.dismiss();
                guidStreetItemClickCallback.onItemClicked(position);
            }

        });


        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnFavorite.setVisibility(View.GONE);
                holder.btnUnfavorite.setVisibility(View.VISIBLE);
            }
        });

        holder.btnUnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = data.get(position).getName();
                Double lat = data.get(position).getLatitude();
                Double lng = data.get(position).getLongitude();
                String info = "";
                String adress = finalAdress;
                new InsertPoint(name, lat, lng, info, adress, userInfoPf).execute();
                holder.btnUnfavorite.setVisibility(View.GONE);
                holder.btnFavorite.setVisibility(View.VISIBLE);

            }
        });
        holder.btnFindStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(data.get(position).getLatitude(), data.get(position).getLongitude());
                String name = data.get(position).getName();
                btnToHereOnclickCallback.onBtnTohereClick(latLng, name);
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
        ImageButton btnFavorite, btnUnfavorite, btnFindStreet;
        LinearLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtLocationName = (TextView) itemView.findViewById(R.id.textViewLocationName);
            txtAdress = (TextView) itemView.findViewById(R.id.textViewLocationAdress);
            btnFavorite = (ImageButton) itemView.findViewById(R.id.button_favorite);
            btnUnfavorite = (ImageButton) itemView.findViewById(R.id.button_unfavorite);
            btnFindStreet = (ImageButton) itemView.findViewById(R.id.button_findStreetToHere);
            line = (LinearLayout) itemView.findViewById(R.id.line);
        }
    }

}
