package com.elishaazaria.advancedactivitychooser;

import android.app.Application;

import com.elishaazaria.advancedactivitychooser.tools.MyPreferencesManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MyPreferencesManager.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MyPreferencesManager.end();
    }
}
