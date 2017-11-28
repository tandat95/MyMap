package com.vbd.mapexam.presenter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by tandat on 11/22/2017.
 */

public class GetMyLocation {
    Context activity;
    protected Double latitude, longitude;

    public GetMyLocation(Context activity) {
        this.activity = activity;
    }


    LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

    public void getLocatin() {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

        } else {
            Toast.makeText(activity.getApplicationContext(), "asdsdfdruio", Toast.LENGTH_LONG).show();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 30, new MyLocationListener());
    }
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            Toast.makeText(activity, "Alert sent. Location: "+ latitude+" "+ longitude, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(activity, "Provider Status Changed: ", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(activity, "GPS ON", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(activity, "GPS OFF:", Toast.LENGTH_LONG).show();
        }
    }
}
