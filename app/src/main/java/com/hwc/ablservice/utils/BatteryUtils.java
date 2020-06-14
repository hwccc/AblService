package com.hwc.ablservice.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.hwc.abllib.bean.AblStateBean;
import com.hwc.ablservice.ClientApplication;

public class BatteryUtils {

    /**
     * 关闭耗电优化
     */
    public static AblStateBean closeBatteryOptimization() {
        AblStateBean ablStateBean = new AblStateBean();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) ClientApplication.getInstance().getSystemService(Context.POWER_SERVICE);
            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(ClientApplication.getInstance().getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (hasIgnored) {
                ablStateBean.state = AblStateBean.STATE_HAVE;
            } else {
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + ClientApplication.getInstance().getPackageName()));
                AppIntentUtils.staticActivity(intent, ablStateBean);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = ComponentName.unflattenFromString("com.android.settings/.Settings$HighPowerApplicationsActivity");
            intent.setComponent(cn);
            AppIntentUtils.staticActivity(intent, ablStateBean);
        }
        return ablStateBean;
    }

}
