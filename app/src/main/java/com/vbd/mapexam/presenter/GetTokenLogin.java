package com.vbd.mapexam.presenter;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.listener.GetTokenLoginCallback;
import com.vbd.mapexam.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tandat on 11/16/2017.
 */

public class GetTokenLogin extends AsyncTask<String, Void, String> {
    String username;
    String pasword;
    GetTokenLoginCallback getTokenLoginCallback;

    public GetTokenLogin(String username, String pasword, GetTokenLoginCallback getTokenLoginCallback) {
        this.username = username;
        this.pasword = pasword;
        this.getTokenLoginCallback = getTokenLoginCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {
        String url = Config.GET_TOKEN_URL;

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("username", username);
        hashMap.put("password", pasword);
        hashMap.put("grant_type", "password");

        try {
            String param = getDataString(hashMap);
            InputStream inputStream = ServiceControl.doPost(url, param);
            return ServiceControl.convertStreamToString(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Token tokenObj = null;
        if (s != null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                tokenObj = new Gson().fromJson(jsonObject.toString(), Token.class);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getTokenLoginCallback.onGetTokenLoginFinish(tokenObj);
    }

    private String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
