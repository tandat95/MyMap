package com.vbd.mapexam.presenter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.DeleteStreetCallback;

import java.io.InputStream;

/**
 * Created by tandat on 11/27/2017.
 */

public class DeleteStreet extends AsyncTask<Void, Void, Boolean> {

    String id;
    SharedPreferences userInfoPf;
    DeleteStreetCallback deleteStreetCallback;

    public DeleteStreet(String id, SharedPreferences userInfoPf, DeleteStreetCallback deleteStreetCallback) {
        this.id = id;
        this.userInfoPf = userInfoPf;
        this.deleteStreetCallback = deleteStreetCallback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String tokenAccess = userInfoPf.getString("access_token", "");
        String url = Config.DELETE_STREET_URL;

        try {
            ServiceControl.clearHeader();
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
            Log.e("token", tokenAccess);
            InputStream inputStream = ServiceControl.doPost(url.replace("{id}", id), "");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        deleteStreetCallback.onDeleteFinish(s);

    }
}
