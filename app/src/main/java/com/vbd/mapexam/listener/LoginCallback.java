package com.vbd.mapexam.listener;

import com.vbd.mapexam.model.User;

/**
 * Created by tandat on 11/17/2017.
 */

public interface LoginCallback {
    void onLoginFinish(User userObj);
}
