package com.vbd.mapexam.presenter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.InsertStreetCallback;
import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;

/**
 * Created by tandat on 11/25/2017.
 */

public class InsertStreet extends AsyncTask<Void, Void, Boolean> {



    private String pathName;
    private LatLng listPoint[];
    private String listNamePoint[];
    private SharedPreferences userInfoPf;
    private InsertStreetCallback insertStreetCallback;


    public InsertStreet(String name, LatLng[] listPoint, String[] listNamePoint, SharedPreferences userInfoPf, InsertStreetCallback insertStreetCallback) {
        this.pathName = name;
        this.listPoint = listPoint;
        this.listNamePoint = listNamePoint;
        this.userInfoPf = userInfoPf;
        this.insertStreetCallback = insertStreetCallback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        String url = Config.INSERT_STREET_URL;
        ArrayList<String> path = new ArrayList<>();
        for (int i =0; i<listPoint.length;i++){
            String lat ="'"+ listPoint[i].getLatitude()+"'";
            String lng ="'"+ listPoint[i].getLongitude()+"'";
            String pointName ="'"+ listNamePoint[i]+"'";
            path.add(pointName);
            path.add(lat);
            path.add(lng);

        }
        String param = path.toString();
        Log.e("sss", param);
        ServiceControl.clearHeader();
        String tokenAccess = userInfoPf.getString("access_token", "");
        ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
        Log.e("token", tokenAccess);

        try {
            ServiceControl.doPost(url.replace("{name}", pathName), param);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        insertStreetCallback.onInsertFinish(aBoolean);
    }
}
