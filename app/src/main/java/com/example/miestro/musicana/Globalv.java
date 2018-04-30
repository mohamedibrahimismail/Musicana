package com.example.miestro.musicana;

import android.app.Application;

/**
 * Created by MIESTRO on 19/08/2017.
 */

public class Globalv extends Application {

    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
