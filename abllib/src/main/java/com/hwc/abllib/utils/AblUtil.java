package com.hwc.abllib.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.AblConfig;
import com.hwc.abllib.AblService;

import java.util.ArrayList;
import java.util.List;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public class AblUtil {

    private static final String TAG = AblUtil.class.getSimpleName();

    /**
     * 打开无障碍服务设置
     */
    public static void openAccessibilitySettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    /**
     * 检查是否是需要监听的APP包名
     *
     * @param thisPackageName APP包名
     * @return
     */
    public static boolean checkPackageName(String thisPackageName) {
        if (AblConfig.sMonitoringPackageNames != null && AblConfig.sMonitoringPackageNames.length > 0) {
            for (String packageName : AblConfig.sMonitoringPackageNames) {
                if (packageName.contentEquals(thisPackageName)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }


    /**
     * 辅助服务是否开启
     *
     * @return
     */
    public static boolean isAccessibilityServiceOpen(Context context, Class classs) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Short.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            LogUtils.d(TAG, info.service.getClassName());
            if (info.service.getClassName().equals(classs.getName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测是否开启辅助功能
     */
    public static boolean isOpenAccessibility(Context context, Class classs) {
        try {
            String service = context.getPackageName() + "/" + classs.getCanonicalName();
            int accessibility = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
            TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter(':');
            LogUtils.e(TAG, "accessibility: " + accessibility);
            if (accessibility == 1) {
                String value = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
                if (!TextUtils.isEmpty(value)) {
                    stringSplitter.setString(value);
                    while (stringSplitter.hasNext()) {
                        String next = stringSplitter.next();
                        if (next.equalsIgnoreCase(service)) {
                            LogUtils.e(TAG, "isOpenAccessibility: true");
                            return true;
                        }
                    }
                }
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 检查AblService服务
     *
     * @param context
     */
    public static boolean checkAblService(Context context) {
        if (AblUtil.isOpenAccessibility(context, AblService.class)) {
            Intent intentService = new Intent(context, AblService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intentService);
            } else {
                context.startService(intentService);
            }
            return true;
        } else {
            AblUtil.openAccessibilitySettings();
        }
        return false;
    }

}
