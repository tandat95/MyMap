package com.vbd.mapexam.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.GetAllPointSavedCallback;
import com.vbd.mapexam.model.PointSaved;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by tandat on 11/17/2017.
 */

public class GetAllPointSaved extends AsyncTask<String, Void, String> {

    SharedPreferences userInfoPf;
    GetAllPointSavedCallback getAllPointSavedCallback;
    ProgressDialog pd;
    Context context;
    public GetAllPointSaved(SharedPreferences userInfoPf, Context context, GetAllPointSavedCallback getAllPointSavedCallback) {
        this.userInfoPf = userInfoPf;
        this.getAllPointSavedCallback = getAllPointSavedCallback;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String tokenAccess = userInfoPf.getString("access_token", "");
        String url = Config.FIND_ALL_POINT_URL;
//        ServiceControl.addHeader("Content-Type", "application/json");

        try {
            ServiceControl.clearHeader();
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
            InputStream inputStream = ServiceControl.doPost(url, "");
            String pointObj = ServiceControl.convertStreamToString(inputStream);
            return pointObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setTitle("Đang tải dữ liệu...");
        pd.setMessage("Vui lòng chờ...!");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        ArrayList<PointSaved> pointSavedList = null;
        if (s != null) {
            pointSavedList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    PointSaved pointSavedObj = new Gson().fromJson(jsonObject.toString(), PointSaved.class);
                    pointSavedList.add(pointSavedObj);
                    pointSavedObj = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getAllPointSavedCallback.onGetAllPointSavedFinish(pointSavedList);
    }
}
