package com.vbd.mapexam.listener;

import com.vbd.mapexam.model.SearchResultObj;

import java.util.ArrayList;

/**
 * Created by tandat on 11/13/2017.
 */

public interface SearchPointCallback {
    void  onSearchFinish(ArrayList<SearchResultObj> searchResultObjArrayList);
}
