package com.hwc.ablservice.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.ClientApplication;
import com.hwc.ablservice.activity.DevicePolicyActivity;
import com.hwc.ablservice.config.PhoneConfig;

/**
 * @author: hwc
 * date:   On 2020/6/12
 */
public class AppIntentUtils implements PhoneConfig {

    private static final String TAG = AppIntentUtils.class.getSimpleName();

    public static void registerDevicePolicyManager(Context context, AblSettingCallBack ablSettingCallBack) {
        switch (Build.BRAND) {
            case MEIZU:
                DevicePolicyActivity.request(context, ablSettingCallBack);
                break;
            default:
                break;
        }
    }

    /**
     * 设置Launcher
     * @param ablSettingCallBack
     */
    public static void setLauncher(AblSettingCallBack ablSettingCallBack) {
        switch (Build.BRAND) {
            case MEIZU:
                AblStateBean ablStateBean = new AblStateBean();
                // TODO hwc 这个状态不准
//                if (LauncherUtils.isHome(ClientApplication.getInstance())) {
//                    ablStateBean.state = AblStateBean.STATE_HAVE;
//                } else {
                    //进入应用列表进行设置默认桌面
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    staticActivity(intent, ablStateBean);
//                }
                ablSettingCallBack.onSuccess(ablStateBean);
                break;
            default:
                break;
        }
    }

    /**
     * 设置电池
     * @param ablSettingCallBack
     */
    public static void setBattery(AblSettingCallBack ablSettingCallBack) {
        switch (Build.BRAND) {
            case MEIZU:
                AblStateBean ablStateBean = new AblStateBean();
                //进入应用列表进行设置默认桌面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                staticActivity(intent, ablStateBean);
                ablSettingCallBack.onSuccess(ablStateBean);
                break;
            default:
                break;
        }
    }


    /**
     * 打开查看应用使用权限
     * @param ablSettingCallBack
     */
    public static void openAppUsagePermissions(AblSettingCallBack ablSettingCallBack) {
        switch (Build.BRAND) {
            case MEIZU:
                AblStateBean ablStateBean = new AblStateBean();
                //进入应用列表进行设置默认桌面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                staticActivity(intent, ablStateBean);
                ablSettingCallBack.onSuccess(ablStateBean);
                break;
            default:
                break;
        }
    }

    /**
     * 设置USB调试
     * @param ablSettingCallBack
     */
    public static void setUsbDebugging(AblSettingCallBack ablSettingCallBack) {
        switch (Build.BRAND) {
            case MEIZU:
                AdbUtils.getInstance().setUsbDebugging(ablSettingCallBack);
                break;
            default:
                break;
        }
    }

    /**
     * 启动Activity
     *
     * @param intent
     * @param ablStateBean
     * @return
     */
    public static boolean staticActivity(Intent intent, AblStateBean ablStateBean) {
        if (IntentUtils.isIntentAvailable(intent)) {
            ablStateBean.state = AblStateBean.STATE_FIND_INTENT;
            ActivityUtils.startActivity(intent);
            return true;
        } else {
            ablStateBean.state = AblStateBean.STATE_NO_FIND_INTENT;
            return false;
        }
    }

}
