package com.hwc.ablservice.phone;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.activity.DevicePolicyActivity;
import com.hwc.ablservice.utils.AdbUtils;
import com.hwc.ablservice.utils.AppIntentUtils;
import com.hwc.ablservice.utils.AppUtils;

/**
 * 魅族设置
 *
 * @author: hwc
 * date:   On 2020/6/16
 */
public class MzAblPhone extends BaseAblPhone {
    @Override
    public void registerDevicePolicyManager(Context context, AblSettingCallBack ablSettingCallBack) {
        DevicePolicyActivity.request(context, ablSettingCallBack);
    }

    @Override
    public void setLauncher(AblSettingCallBack ablSettingCallBack) {
        AblStateBean ablStateBean = new AblStateBean();
        //进入应用列表进行设置默认桌面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtils.staticActivity(intent, ablStateBean);
        ablSettingCallBack.onSuccess(ablStateBean);
    }

    @Override
    public void setBattery(AblSettingCallBack ablSettingCallBack) {
        AblStateBean ablStateBean = new AblStateBean();
        //进入应用列表进行设置默认桌面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtils.staticActivity(intent, ablStateBean);
        ablSettingCallBack.onSuccess(ablStateBean);
    }

    @Override
    public void openAppUsagePermissions(AblSettingCallBack ablSettingCallBack) {
        AblStateBean ablStateBean = new AblStateBean();
        //进入应用列表进行设置默认桌面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AppUtils.staticActivity(intent, ablStateBean);
        ablSettingCallBack.onSuccess(ablStateBean);
    }

    @Override
    public void setUsbDebugging(AblSettingCallBack ablSettingCallBack) {
        AdbUtils.getInstance().setUsbDebugging(ablSettingCallBack);
    }
}
