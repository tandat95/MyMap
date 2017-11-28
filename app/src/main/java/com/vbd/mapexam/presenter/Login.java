package com.vbd.mapexam.presenter;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.LoginCallback;
import com.vbd.mapexam.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by tandat on 11/16/2017.
 */

public class Login extends AsyncTask<String, Void, String> {
    String tokenAccess;
    LoginCallback loginCallback;

    public Login(String tokenAccess, LoginCallback loginCallback) {
        this.tokenAccess = tokenAccess;
        this.loginCallback = loginCallback;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "http://192.168.10.91:88/api/me";
        try {
            ServiceControl.addHeader("Content-Type", "application/json");
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);

            InputStream inputStream = ServiceControl.doGet(url, "");
            String userObj = ServiceControl.convertStreamToString(inputStream);
            ServiceControl.clearHeader();


            return userObj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        User userObj = null;
        if (s != null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                userObj = new Gson().fromJson(jsonObject.toString(), User.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        loginCallback.onLoginFinish(userObj);
    }
}
