package com.hwc.ablservice.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.FlowerApplication;
import com.hwc.ablservice.activity.DevicePolicyActivity;
import com.hwc.ablservice.config.PhoneConfig;
import com.hwc.ablservice.phone.BaseAblPhone;
import com.hwc.ablservice.phone.MzAblPhone;

import java.util.List;

/**
 * @author: hwc
 * date:   On 2020/6/12
 */
public class AppIntentUtils implements PhoneConfig {

    private static final String TAG = AppIntentUtils.class.getSimpleName();

    private BaseAblPhone baseAblPhone;

    private volatile static AppIntentUtils instance;

    public static AppIntentUtils getInstance() {
        if (instance == null) {
            synchronized (AppIntentUtils.class) {
                if (instance == null) {
                    instance = new AppIntentUtils();
                }
            }
        }
        return instance;
    }

    private AppIntentUtils() {
        switch (Build.BRAND) {
            case MEIZU:
                baseAblPhone = new MzAblPhone();
                break;
            default:
                break;
        }
    }


    public void registerDevicePolicyManager(Context context, AblSettingCallBack ablSettingCallBack) {
        if (baseAblPhone != null) {
            baseAblPhone.registerDevicePolicyManager(context, ablSettingCallBack);
        }
    }

    /**
     * 设置Launcher
     *
     * @param ablSettingCallBack
     */
    public void setLauncher(AblSettingCallBack ablSettingCallBack) {
        if (baseAblPhone != null) {
            baseAblPhone.setLauncher(ablSettingCallBack);
        }
    }

    /**
     * 设置电池
     *
     * @param ablSettingCallBack
     */
    public void setBattery(AblSettingCallBack ablSettingCallBack) {
        if (baseAblPhone != null) {
            baseAblPhone.setBattery(ablSettingCallBack);
        }
    }


    /**
     * 打开查看应用使用权限
     *
     * @param ablSettingCallBack
     */
    public void openAppUsagePermissions(AblSettingCallBack ablSettingCallBack) {
        if (baseAblPhone != null) {
            baseAblPhone.openAppUsagePermissions(ablSettingCallBack);
        }
    }

    /**
     * 设置USB调试
     *
     * @param ablSettingCallBack
     */
    public void setUsbDebugging(AblSettingCallBack ablSettingCallBack) {
        if (baseAblPhone != null) {
            baseAblPhone.setUsbDebugging(ablSettingCallBack);
        }
    }
}
