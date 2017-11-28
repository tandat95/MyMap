package com.vbd.mapexam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vbd.mapexam.R;
import com.vbd.mapexam.listener.GuidStreetItemClickCallback;
import com.vbd.mapexam.model.ShortPathObj;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class RecyclerViewGuideStreetAdapter extends RecyclerView.Adapter<RecyclerViewGuideStreetAdapter.RecyclerViewHolder> {

    private List<ShortPathObj.Leg.Step> step;
    GuidStreetItemClickCallback guidStreetItemClickCallback;

    public RecyclerViewGuideStreetAdapter(List<ShortPathObj.Leg.Step> data, GuidStreetItemClickCallback guidStreetItemClickCallback) {
        this.step = data;
        this.guidStreetItemClickCallback = guidStreetItemClickCallback;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.guide_street_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        List<String> directionForm = Arrays.asList(" Đi thẳng ", " Rẽ trái vào ", " Rẽ phải vào ", " Đi tiếp ", " Qua ", " Rẽ vào ", " Đến ");


        final ShortPathObj.Leg.Step stepObj = step.get(position);
        String name = stepObj.getName();
        if (name.equals("")) {
            name = "Đường không tên";
        }
        String dir = stepObj.getDir();
        int start = stepObj.getStart();
        float len = stepObj.getLen();
        String unit = "m";
        if (len >= 500) {
            len = len / 1000;
            len = (float) ((double) Math.round(len * 100) / 100);
            unit = "km";
        }
        String direct = directionForm.get(parseInt(dir));
        String guide = name;
        String length = len + unit;
        holder.txtDirect.setText(direct);
        holder.txtGuige.setText(guide);
        holder.txtLength.setText(length);
        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start = step.get(position).getStart();
                guidStreetItemClickCallback.onItemClicked(start);
            }
        });
    }

    @Override
    public int getItemCount() {
        return step.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView txtDirect;
        TextView txtGuige;
        TextView txtLength;
        LinearLayout line;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtDirect = (TextView) itemView.findViewById(R.id.texView_direct);
            txtGuige = (TextView) itemView.findViewById(R.id.texView_guide);
            txtLength = (TextView) itemView.findViewById(R.id.texView_length);
            line = (LinearLayout) itemView.findViewById(R.id.line_guideItem);
        }
    }

}
