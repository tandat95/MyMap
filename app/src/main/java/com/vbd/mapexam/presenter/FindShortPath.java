package com.vbd.mapexam.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.FindShortPathCallback;
import com.vbd.mapexam.model.ShortPathObj;
import com.vietbando.vietbandosdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by tandat on 11/15/2017.
 */

public class FindShortPath extends AsyncTask<String, Void, String> {
    //OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public FindShortPath(ArrayList<LatLng> listLatLng, int type, Context context, FindShortPathCallback findShortPathCallback) {
        this.listLatLng = listLatLng;
        this.type = type;
        this.findShortPathCallback = findShortPathCallback;
        this.context = context;
    }


    ArrayList<LatLng> listLatLng;
    int type;
    FindShortPathCallback findShortPathCallback;
    String url = Config.FIND_SHORTED_PATH_URL;
    ProgressDialog pd;
    Context context;

    @Override
    protected String doInBackground(String... strings) {

        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < listLatLng.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("latitude", listLatLng.get(i).getLatitude());
                jsonObject.put("longitude", listLatLng.get(i).getLongitude());
                jsonArray.put(jsonObject);
            }
            ServiceControl.clearHeader();
            ServiceControl.addHeader("Content-Type", "application/json");
            InputStream inputStream = ServiceControl.doPost(url.replace("{type}", Integer.toString(type)), jsonArray.toString());

            Log.e("aaaaa", jsonArray.toString());
            return ServiceControl.convertStreamToString(inputStream);

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
        pd.dismiss();
        if (s != null) {
            try {
                Log.d("rsssssss", s);
                ShortPathObj shortPathObj = new Gson().fromJson(new JSONObject(s).toString(), ShortPathObj.class);
                findShortPathCallback.onFindShortPathFinish(shortPathObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            findShortPathCallback.onFindShortPathFinish(null);
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
