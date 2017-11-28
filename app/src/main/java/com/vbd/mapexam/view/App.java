package com.vbd.mapexam.view;

import android.app.Application;

import com.vbd.mapexam.R;
import com.vietbando.vietbandosdk.Vietbando;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Vietbando.getInstance(getApplicationContext(), getString(R.string.vietbando_access_token));
    }
}
