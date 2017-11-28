package com.vbd.mapexam.listener;

import com.vbd.mapexam.model.StreetSaved;

import java.util.ArrayList;

/**
 * Created by tandat on 11/24/2017.
 */

public interface GetAllStreetSavedCallback {
    void onGetStreetFinish(ArrayList<StreetSaved> listStreetSaved);
}
