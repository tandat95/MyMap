package com.vbd.mapexam.listener;

import com.vbd.mapexam.model.PointSaved;

import java.util.ArrayList;

/**
 * Created by tandat on 11/17/2017.
 */

public interface GetAllPointSavedCallback {
    void  onGetAllPointSavedFinish(ArrayList<PointSaved> pointSavedList);
}
