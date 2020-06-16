package com.hwc.ablservice;

import android.app.Application;
import android.os.Build;

import com.blankj.utilcode.util.LogUtils;
import com.hwc.ablservice.activity.MyActivityLifecycle;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public class FlowerApplication extends Application {

    private static final String TAG = FlowerApplication.class.getSimpleName();

    private volatile static FlowerApplication instance;

    public static FlowerApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityListener();
    }

    /**
     * 注册Activity生命周期监听，
     * 自Android 4.0（API-14）开始，Application 中多了一个方法registerActivityLifecycleCallbacks
     * 目前项目使用最低版本是17所有判断更改成Build.VERSION_CODES.JELLY_BEAN_MR1
     */
    private void registerActivityListener() {
        LogUtils.d(TAG, "registerActivityListener is run");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            registerActivityLifecycleCallbacks(new MyActivityLifecycle());
        }
    }
}
