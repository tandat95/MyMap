package com.vbd.mapexam.presenter;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.DeletePointCallback;

import java.io.InputStream;

/**
 * Created by tandat on 11/17/2017.
 */

public class DeletePoint extends AsyncTask<Void, Void,Boolean> {


    String id;
    SharedPreferences userInfoPf;
    DeletePointCallback deletePointCallback;

    public DeletePoint( String id, SharedPreferences userInfoPf, DeletePointCallback deletePointCallback) {

        this.id = id;
        this.userInfoPf = userInfoPf;
        this.deletePointCallback = deletePointCallback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String tokenAccess = userInfoPf.getString("access_token", "");
        String url = Config.DELETE_POINT_URL;
//ServiceControl.addHeader("Content-Type", "application/json");
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

        deletePointCallback.onDeleteFinish(s);
    }
}
