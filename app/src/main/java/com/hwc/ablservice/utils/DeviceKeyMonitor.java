package com.hwc.ablservice.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

/**
 * Created by zhuxiaoxin
 */

public class DeviceKeyMonitor {

    private final String TAG = "DeviceKeyMonitor";

    /**
     * 参数
     */
    private static final String SYSTEM_REASON = "reason";
    private static final String SYSTEM_HOME_KEY = "homekey";
    private static final String SYSTEM_HOME_RECENT_APPS = "recentapps";

    private Context mContext;
    private BroadcastReceiver mDeviceKeyReceiver = null;
    private OnKeyListener mListener;

    public DeviceKeyMonitor(Context context, OnKeyListener listener) {
        mContext = context;
        mListener = listener;
        mDeviceKeyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    String reason = intent.getStringExtra(SYSTEM_REASON);
                    if (!TextUtils.isEmpty(reason)) {
                        if (SYSTEM_HOME_KEY.equals(reason)) {
                            mListener.onHomeClick();
                        } else if (SYSTEM_HOME_RECENT_APPS.equals(reason)) {
                            mListener.onRecentClick();
                        }
                    }
                }
            }
        };
        mContext.registerReceiver(mDeviceKeyReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    public interface OnKeyListener {
        void onHomeClick();

        void onRecentClick();
    }

    public void unregister() {
        if (mDeviceKeyReceiver != null) {
            mContext.unregisterReceiver(mDeviceKeyReceiver);
            mDeviceKeyReceiver = null;
        }
    }
}

