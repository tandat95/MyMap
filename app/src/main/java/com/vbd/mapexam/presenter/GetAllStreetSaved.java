package com.vbd.mapexam.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.GetAllStreetSavedCallback;
import com.vbd.mapexam.model.StreetSaved;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by tandat on 11/24/2017.
 */

public class GetAllStreetSaved extends AsyncTask<Void, Void, String> {
    SharedPreferences userInfoPf;
    Context context;
    GetAllStreetSavedCallback getAllStreetSavedCallback;
    ProgressDialog pd;
    public GetAllStreetSaved(SharedPreferences userInfoPf,Context context, GetAllStreetSavedCallback getAllStreetSavedCallback) {
        this.userInfoPf = userInfoPf;
        this.context = context;
        this.getAllStreetSavedCallback = getAllStreetSavedCallback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String tokenAccess = userInfoPf.getString("access_token", "");
        String url = Config.GET_ALL_STREET_SAVED_URL;

        try {
            ServiceControl.clearHeader();
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
            InputStream inputStream = ServiceControl.doPost(url, "");
            String pointObj = streamToString(inputStream);
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
        if (s != null) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                ArrayList<StreetSaved> arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    StreetSaved streetSaved = new Gson().fromJson(jsonObject.toString(), StreetSaved.class);
                    arrayList.add(streetSaved);
                }
                getAllStreetSavedCallback.onGetStreetFinish(arrayList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            getAllStreetSavedCallback.onGetStreetFinish(null);
        }

    }
    private String streamToString(InputStream inputStream) {
        StringBuilder content = new StringBuilder();
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
