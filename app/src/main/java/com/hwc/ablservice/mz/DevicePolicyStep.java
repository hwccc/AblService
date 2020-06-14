package com.hwc.ablservice.mz;

import android.os.Message;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.AblStepBase;
import com.hwc.abllib.AblStepHandler;
import com.hwc.abllib.AblSteps;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.abllib.callback.AniCallBack;
import com.hwc.abllib.utils.AblViewUtil;
import com.hwc.ablservice.ClientApplication;
import com.hwc.ablservice.utils.AppIntentUtils;

/**
 * 设备管理器设置步骤
 *
 * @author hwc
 */
public class DevicePolicyStep extends AblStepBase implements AblSteps {

    public DevicePolicyStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                // 设置设备管理器
                AppIntentUtils.registerDevicePolicyManager(ClientApplication.getInstance(), new AblSettingCallBack() {

                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                            AblStepHandler.sendMsg(STEP_4);
                        } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_2);
                        } else {
                            onCallBackFail();
                        }
                    }

                    @Override
                    public void onFail() {
                        LogUtils.d(TAG, "onFail");
                        // 取消的话，指责不需要进行下一步骤，直接中断设置
                        onCallBackFail();
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
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_3);
                            }

                            @Override
                            public void fail() {
                                onCallBackFail();
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                AblStepHandler.getInstance().initStepClass(true, new BatteryOptimizedStep(ablSettingCallBack));
                AblStepHandler.sendMsg(STEP_1);
                break;
            }
            case STEP_4: {
                AblViewUtil.back();
                AblStepHandler.getInstance().initStepClass(true, new BatteryOptimizedStep(ablSettingCallBack));
                AblStepHandler.sendMsg(STEP_1);
                break;
            }
            default:
                break;
        }
    }
}
