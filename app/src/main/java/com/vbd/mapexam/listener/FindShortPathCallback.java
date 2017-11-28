package com.vbd.mapexam.listener;

import com.vbd.mapexam.model.ShortPathObj;

/**
 * Created by tandat on 11/15/2017.
 */

public interface FindShortPathCallback {
    void onFindShortPathFinish(ShortPathObj shortPathObj);
}
