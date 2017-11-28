package com.vbd.mapexam.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.vbd.mapexam.config.Config;
import com.vbd.mapexam.helper.ServiceControl;
import com.vbd.mapexam.listener.UpdatePointCallback;

import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created by tandat on 11/23/2017.
 */

public class UpdatePoint extends AsyncTask<Void, Void, Boolean>{
    String id;
    String newName;
    String adress;
    String info;
    SharedPreferences userInfoPf;
    Context context;
    UpdatePointCallback updatePointCallback;
    ProgressDialog pd;

    public UpdatePoint(String id, String newName, String adress, String info,
                       SharedPreferences userInfoPf, Context context, UpdatePointCallback updatePointCallback) {
        this.id = id;
        this.newName = newName;
        this.adress = adress;
        this.info = info;
        this.userInfoPf = userInfoPf;
        this.context = context;
        this.updatePointCallback = updatePointCallback;
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
    protected Boolean doInBackground(Void... voids) {

        try {
            String url = Config.UPDATE_POINT_URL;
            url = url.replace("{id}", URLEncoder.encode(id, "UTF-8"));
            url = url.replace("{NamePoint}",URLEncoder.encode(newName, "UTF-8") );
            url = url.replace("{Address}",URLEncoder.encode(adress, "UTF-8"));
            url = url.replace("{Inform}",URLEncoder.encode(info, "UTF-8") );
            ServiceControl.clearHeader();

            String tokenAccess = userInfoPf.getString("access_token", "");
            ServiceControl.addHeader("Authorization", "Bearer " + tokenAccess);
            Log.e("token", tokenAccess);
            InputStream inputStream = ServiceControl.doPost(url, "");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        pd.dismiss();
        updatePointCallback.onUpdateFinish(aBoolean);
    }
}
