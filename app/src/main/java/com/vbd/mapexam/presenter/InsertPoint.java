package com.vbd.mapexam.presenter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tandat on 11/23/2017.
 */

public class InsertPoint extends AsyncTask<String, Void, String> {

    String name;
    Double lat;
    Double lng;
    String info;
    String adress;
    SharedPreferences userInfoPf;

    public InsertPoint(String name, Double lat, Double lng, String info, String adress, SharedPreferences userInfoPf) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.info = info;
        this.adress = adress;
        this.userInfoPf = userInfoPf;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = Config.INSERT_POINT_URL;
        try {
            url = url.replace("{NamePoint}",URLEncoder.encode(name, "UTF-8"));
            url = url.replace("{Lat}",URLEncoder.encode(lat.toString(), "UTF-8") );
            url = url.replace("{Lg}",URLEncoder.encode(lng.toString(), "UTF-8"));
            url = url.replace("{Inform}",URLEncoder.encode(info, "UTF-8") );
            url = url.replace("{Address}", URLEncoder.encode(adress, "UTF-8"));
            String tokenAccess = userInfoPf.getString("access_token", "");
            ServiceControl.clearHeader();
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
            Log.e("url", url);
            return ServiceControl.convertStreamToString(ServiceControl.doPost(url,""));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
