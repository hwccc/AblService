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
import com.hwc.ablservice.utils.AppIntentUtils;

import java.util.List;

/**
 * Usb设置步骤
 *
 * @author hwc
 */
public class UsbStep extends AblStepBase implements AblSteps {
    public UsbStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                AppIntentUtils.setUsbDebugging(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                            AblStepHandler.getInstance().initStepClass(true, new NoticeReadStep(ablSettingCallBack));
                            AblStepHandler.sendMsg(AblSteps.STEP_1);
                        } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_2);
                        } else {
                            onCallBackFail();
                        }
                    }

                    @Override
                    public void onFail() {
                        onCallBackFail();
                    }
                });
                break;
            }
            case STEP_2: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText(info, "USB调试");
                                for (int i = 0; i < accessibilityNodeInfos.size(); i++) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(i).getParent();
                                    if (child != null) {
                                        List<AccessibilityNodeInfo> byId = AblViewUtil.findById(child, "com.android.settings:id/switchWidget");
                                        if (!byId.isEmpty()) {
                                            if (byId.get(0).isChecked()) {
                                                AblViewUtil.clickNodeInfo(child);
                                            }
                                        }
                                    }
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_3);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(AblSteps.STEP_3);
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                AblViewUtil.back();
                AblStepHandler.getInstance().initStepClass(true, new NoticeReadStep(ablSettingCallBack));
                AblStepHandler.sendMsg(AblSteps.STEP_1);
                break;
            }
            default:
                break;
        }
    }
}
