package com.hwc.ablservice.phone;

import android.content.Context;

import com.hwc.abllib.callback.AblSettingCallBack;

/**
 * @author: hwc
 * date:   On 2020/6/16
 */
public abstract class BaseAblPhone {

    protected final String TAG = getClass().getSimpleName();

    public abstract void registerDevicePolicyManager(Context context, AblSettingCallBack ablSettingCallBack);

    /**
     * 设置Launcher
     *
     * @param ablSettingCallBack
     */
    public abstract void setLauncher(AblSettingCallBack ablSettingCallBack);

    /**
     * 设置电池
     *
     * @param ablSettingCallBack
     */
    public abstract void setBattery(AblSettingCallBack ablSettingCallBack);


    /**
     * 打开查看应用使用权限
     *
     * @param ablSettingCallBack
     */
    public abstract void openAppUsagePermissions(AblSettingCallBack ablSettingCallBack);

    /**
     * 设置USB调试
     *
     * @param ablSettingCallBack
     */
    public abstract void setUsbDebugging(AblSettingCallBack ablSettingCallBack);


}
