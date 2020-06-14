package com.hwc.ablservice.utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.ClientApplication;
import com.hwc.ablservice.MainActivity;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public class NotificationUtils {

    private static final String TAG = NotificationUtils.class.getSimpleName();

    /**
     * 跳转到通知权限设置界面
     */
    public static void setNotification(AblSettingCallBack ablSettingCallBack) {
        if (NotificationUtils.isNotificationListenerEnabled(ClientApplication.getInstance())) {
            ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_HAVE));
        } else {
            try {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityUtils.startActivity(intent);
                ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_FIND_INTENT));
                return;
            } catch (ActivityNotFoundException e) {
                //普通情况下找不到的时候需要再特殊处理找一次
                try {
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                    intent.setComponent(cn);
                    intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                    ActivityUtils.startActivity(intent);
                    ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_FIND_INTENT));
                    return;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
                ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
            }
        }
    }

    public static boolean isNotificationListenerEnabled(Context context) {
        try {
            String packageName = context.getApplicationContext().getPackageName();
            String str1 = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), "enabled_notification_listeners");
            if (!TextUtils.isEmpty(str1)) {
                String[] strings = str1.split(":");
                for (String string : strings) {
                    ComponentName localComponentName
                            = ComponentName.unflattenFromString(string);
                    if ((localComponentName == null)
                            || (!TextUtils.equals(packageName, localComponentName.getPackageName()))) {
                        LogUtils.d(TAG, localComponentName.getPackageName());
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
