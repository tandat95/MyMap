package com.vbd.mapexam.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vbd.mapexam.R;
import com.vietbando.vietbandosdk.annotations.IconFactory;
import com.vietbando.vietbandosdk.annotations.Marker;
import com.vietbando.vietbandosdk.annotations.MarkerOptions;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;

import static com.vbd.mapexam.view.activity.MainActivity.iconMylocation;
import static com.vbd.mapexam.view.activity.MainActivity.mylocation;

/**
 * Created by tandat on 11/4/2017.
 */

public class VbdMapFragment extends android.app.Fragment {

    public static  MapView mapView;
    FloatingActionButton fabFindStreet;
    public static VietbandoMap map;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.stub_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.setStyleUrl("http://images.vietbando.com:9999/style/vt_vbddefault/c382e41a-5e36-4dcc-b0cf-a6797c48bc4e");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapReady(VietbandoMap vietbandoMap) {
            map = vietbandoMap;
            map.easeCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15), 500);
            iconMylocation= IconFactory.getInstance(getContext()).fromResource(R.drawable.ic_start);
            Marker markerMyLocation = map.addMarker(new MarkerOptions().position(mylocation).icon(iconMylocation));
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
