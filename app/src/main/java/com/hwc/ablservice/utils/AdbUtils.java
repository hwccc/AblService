package com.hwc.ablservice.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.ClientApplication;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public class AdbUtils {

    private static final String TAG = AdbUtils.class.getSimpleName();

    private volatile static AdbUtils instance;

    public static AdbUtils getInstance() {
        if (instance == null) {
            synchronized (AdbUtils.class) {
                if (instance == null) {
                    instance = new AdbUtils();
                }
            }
        }
        return instance;
    }

    private AdbUtils() {

    }

    /**
     * 检查adb调试模式是否打开，没有打开则打开USB 设置界面
     *
     * @param ablSettingCallBack
     * @return
     */
    public void setUsbDebugging(AblSettingCallBack ablSettingCallBack) {
        AblStateBean ablStateBean = new AblStateBean();
        if (isOpenAdb(ClientApplication.getInstance())) {
            if (startDevelopmentActivity()) {
                ablStateBean.state = AblStateBean.STATE_FIND_INTENT;
            } else {
                ablStateBean.state = AblStateBean.STATE_NO_FIND_INTENT;
            }
        } else {
            ablStateBean.state = AblStateBean.STATE_HAVE;
        }
        ablSettingCallBack.onSuccess(ablStateBean);
    }


    /**
     * 检查adb调试模式是否打开
     *
     * @param context
     * @return
     */
    public boolean isOpenAdb(Context context) {
        boolean enableAdb = (Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        if (enableAdb) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开开发者模式界面
     */
    private boolean startDevelopmentActivity() {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            startActivity(intent);
            return true;
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                startActivity(intent);
                return true;
            } catch (Exception e1) {
                try {
                    Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");//部分小米手机采用这种方式跳转
                    startActivity(intent);
                } catch (Exception e2) {
                    return false;
                }

            }
        }
        return false;
    }

}
