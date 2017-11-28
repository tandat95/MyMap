package com.vbd.mapexam.listener;

import com.vietbando.vietbandosdk.geometry.LatLng;

import java.util.ArrayList;

/**
 * Created by tandat on 11/24/2017.
 */

public interface StreetSavedItemClickCallback {
    void  onItemClick(ArrayList<LatLng> listLatlng);
}
