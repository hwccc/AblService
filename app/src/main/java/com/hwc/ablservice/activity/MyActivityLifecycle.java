package com.hwc.ablservice.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author: hwc
 * date:   On 2020/6/16
 */
public class MyActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = MyActivityLifecycle.class.getSimpleName();

    public static Activity currentStartActivity;


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        LogUtils.d(TAG, "onActivityCreated is run");
        currentStartActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentStartActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentStartActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        currentStartActivity = null;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        currentStartActivity = null;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.d(TAG, "onActivityDestroyed is run");
        currentStartActivity = null;
    }
}
