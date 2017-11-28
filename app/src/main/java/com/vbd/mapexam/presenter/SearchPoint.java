package com.vbd.mapexam.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.listener.SearchPointCallback;
import com.vbd.mapexam.model.SearchResultObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class SearchPoint extends AsyncTask<String, Void, String> {
    SearchPointCallback searchPointCallback;
    String url = Config.SEARCH_ALL_URL;
    private String keyword;
    ProgressDialog pd;
    Context context;

    public SearchPoint(String keyword, Context context, SearchPointCallback searchPointCallback) {
        this.keyword = keyword;
        this.context = context;
        this.searchPointCallback = searchPointCallback;
    }

    @Override
    protected String doInBackground(String... strings) {

//        try {
//
//            InputStream inputStream = ServiceControl.doPost(url.replace("{key}", "'" + keyword + "'"), "");
//            String s = ServiceControl.convertStreamToString(inputStream);
//
//            return s;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        url = url.replace("{key}", "'" + keyword + "'");
        return docNoiDung_Tu_URL(url);
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
        ArrayList<SearchResultObj> arrayList = new ArrayList<>();
        JSONArray jsonArray = null;
        if (s != null) {
            if (!s.equals("")) {
                try {
                    jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        SearchResultObj obj = new Gson().fromJson(jsonObject.toString(), SearchResultObj.class);
                        arrayList.add(obj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.d("laseirto", s);
        }

        searchPointCallback.onSearchFinish(arrayList);
        Log.d("laseirto", s);
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
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