package com.hwc.ablservice.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.Utils;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.ablservice.receiver.MyDeviceAdminReceiver;

/**
 * 设备激活管理界面
 *
 * @author hwc
 */
public class DevicePolicyActivity extends Activity {

    private static AblSettingCallBack ablSettingCallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerDevicePolicyManager();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (0 == requestCode && ablSettingCallBack != null) {
            if (resultCode == Activity.RESULT_OK) {
                ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_HAVE));
            } else {
                ablSettingCallBack.onFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
            }
        }
        finish();
    }


    public static synchronized void request(Context context, AblSettingCallBack ablSettingCallBack) {
        DevicePolicyActivity.ablSettingCallBack = ablSettingCallBack;
        Intent intent = new Intent(context, DevicePolicyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 注册成为"设备管理器"
     */
    public void registerDevicePolicyManager() {
        try {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) Utils.getApp().getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName componentName = new ComponentName(Utils.getApp(), MyDeviceAdminReceiver.class);
            if (devicePolicyManager.isAdminActive(componentName)) {
                if (ablSettingCallBack != null) {
                    ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_HAVE));
                }
            } else {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                //注册设备管理器时可以显示一些话术，就在这里添加
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "我需要这些权限");
                //打开注册页面
                if (com.blankj.utilcode.util.IntentUtils.isIntentAvailable(intent)) {
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onSuccess(new AblStateBean(AblStateBean.STATE_FIND_INTENT));
                        ablSettingCallBack = null;
                        startActivityForResult(intent, 0);
                    }
                } else {
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (ablSettingCallBack != null) {
                ablSettingCallBack.onFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
            }
        }
    }


}
