package com.vbd.mapexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vbd.mapexam.R;
import com.vbd.mapexam.listener.DeleteStreetCallback;
import com.vbd.mapexam.listener.StreetSavedItemClickCallback;
import com.vbd.mapexam.model.StreetSaved;
import com.vbd.mapexam.presenter.DeleteStreet;
import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewStreetSavedAdapter extends RecyclerView.Adapter<RecyclerViewStreetSavedAdapter.RecyclerViewHolder> {

    private ArrayList<StreetSaved> data = new ArrayList<>();
    Context context;
    SharedPreferences userInfoPf;
    StreetSavedItemClickCallback streetSavedItemClickCallback;

    public RecyclerViewStreetSavedAdapter(ArrayList<StreetSaved> data, Context context,  SharedPreferences sharedPreferences, StreetSavedItemClickCallback streetSavedItemClickCallback) {
        this.data = data;
        this.context = context;
        this.userInfoPf = sharedPreferences;
        this.streetSavedItemClickCallback = streetSavedItemClickCallback;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.street_saved_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.txtStreetName.setText(data.get(position).getNamePath());
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("StaticFieldLeak")
                AsyncTask<Void, Void, ArrayList<LatLng>> task = new AsyncTask<Void, Void, ArrayList<LatLng>>() {
                    @Override
                    protected ArrayList<LatLng> doInBackground(Void... voids) {
                        List<StreetSaved.PointPath> pointPath = data.get(position).getPointPath();
                        ArrayList<LatLng> listPoint = new ArrayList<>();
                        for (int i = 0; i < pointPath.size(); i++) {
                            Double lat = pointPath.get(i).getLat();
                            Double lng = pointPath.get(i).getLg();
                            LatLng latLng = new LatLng(lat, lng);
                            listPoint.add(latLng);
                        }
                        return listPoint;
                    }

                    @Override
                    protected void onPostExecute(ArrayList<LatLng> latLngs) {
                        super.onPostExecute(latLngs);
                        streetSavedItemClickCallback.onItemClick(latLngs);
                    }
                };
                task.execute();
//                map.clear();
//                map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
//                map.easeCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12));
//                bottomSheetPointSaved.dismiss();
//                btnShow.setVisibility(View.VISIBLE);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteStreet(data.get(position).getId(), userInfoPf, new DeleteStreetCallback() {
                    @Override
                    public void onDeleteFinish(Boolean success) {
                        if (success) {
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
//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //btnEditOnclickCallBack.onBtnEditClick(data.get(position).getId());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtStreetName;
        ImageButton btnDelete;
        ImageButton btnEdit;
        LinearLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtStreetName = (TextView) itemView.findViewById(R.id.textView_streetSavedName);
            line = (LinearLayout) itemView.findViewById(R.id.line_streetSaved);
            btnDelete = (ImageButton) itemView.findViewById(R.id.button_deleteStreetSaved);
            //btnEdit = (ImageButton) itemView.findViewById(R.id.button_editStreet);
        }
    }

}
