package com.hwc.ablservice;

import android.app.Application;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public class ClientApplication extends Application {

    private volatile static ClientApplication instance;

    public static ClientApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
