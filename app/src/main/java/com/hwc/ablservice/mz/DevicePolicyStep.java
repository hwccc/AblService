package com.hwc.ablservice.mz;

import android.content.Intent;
import android.os.Message;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.AblStepBase;
import com.hwc.abllib.AblStepHandler;
import com.hwc.abllib.AblSteps;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.abllib.callback.AniCallBack;
import com.hwc.abllib.utils.AblViewUtil;
import com.hwc.ablservice.FlowerApplication;
import com.hwc.ablservice.MainActivity;
import com.hwc.ablservice.activity.MyActivityLifecycle;
import com.hwc.ablservice.utils.AppIntentUtils;
import com.hwc.ablservice.utils.AppUtils;

/**
 * 设备管理器设置步骤
 *
 * @author hwc
 */
public class DevicePolicyStep extends AblStepBase implements AblSteps {

    public DevicePolicyStep() {
    }

    public DevicePolicyStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                // 设置设备管理器
                AppIntentUtils.getInstance().registerDevicePolicyManager(FlowerApplication.getInstance(), new AblSettingCallBack() {

                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                            AblStepHandler.sendMsg(STEP_3);
                        } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_2);
                        } else {
                            onCallBackFail(ablStateBean);
                        }
                    }

                    @Override
                    public void onFail(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "onFail");
                        onCallBackFail(ablStateBean);
                    }
                });
                break;
            }
            case STEP_2: {
                // 点击激活设备管理器
                AblViewUtil.findById("com.android.settings:id/action_button",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                // 点击激活，会自己关闭界面
                                AppIntentUtils.getInstance().registerDevicePolicyManager(FlowerApplication.getInstance(), null);
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendTimeMsg(STEP_3);
                            }

                            @Override
                            public void fail() {
                                onCallBackFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                onCallBackSuccess(new AblStateBean(AblStateBean.STATE_SET_SUCCESS));
                AppUtils.moveToFront(FlowerApplication.getInstance());
                break;
            }
            default:
                break;
        }
    }
}
