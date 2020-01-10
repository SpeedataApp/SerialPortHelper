package com.serialport_helper_new;

import android.app.Application;

/**
 * @author xuyan
 */
public class AppSph extends Application {
    private static AppSph instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppSph getInstance() {
        return instance;
    }
}
