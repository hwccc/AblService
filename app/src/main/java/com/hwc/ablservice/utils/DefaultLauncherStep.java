package com.hwc.ablservice.utils;

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
import com.hwc.ablservice.config.AppConfig;
import com.hwc.ablservice.mz.UsbStep;

import java.util.List;

/**
 * Launcher设置步骤
 *
 * @author hwc
 */
public class DefaultLauncherStep extends AblStepBase implements AblSteps {

    public DefaultLauncherStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                // 设置默认桌面
                AppIntentUtils.setLauncher(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                            AblStepHandler.getInstance().initStepClass(true, new UsbStep(ablSettingCallBack));
                            AblStepHandler.sendMsg(AblSteps.STEP_1);
                        } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_2);
                        } else {
                            onCallBackFail();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
                break;
            }
            case STEP_2: {
                // 设置默认桌面找到默认应用
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = info.findAccessibilityNodeInfosByText("默认应用");
                                if (!accessibilityNodeInfos.isEmpty()) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
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
                // 设置默认桌面，找到桌面设置
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = info.findAccessibilityNodeInfosByText("桌面");
                                if (!accessibilityNodeInfos.isEmpty()) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_4);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(AblSteps.STEP_4);
                            }
                        }
                );
                break;
            }
            case STEP_4: {
                // 选择自己应用，设置为桌面
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = info.findAccessibilityNodeInfosByText("系统");
                                if (!accessibilityNodeInfos.isEmpty()) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_5);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(AblSteps.STEP_5);
                            }
                        }
                );
                break;
            }
            case STEP_5: {
                AblViewUtil.back();
                AblStepHandler.sendMsg(AblSteps.STEP_6);
                break;
            }
            case STEP_6: {
                AblViewUtil.back();
                AblStepHandler.sendMsg(AblSteps.STEP_7);
                break;
            }
            case STEP_7: {
                AblViewUtil.back();
                // 回到程序无障碍设置界面
//                AblStepHandler.getInstance().initStepClass(true, new UsbStep(ablSettingCallBack));
//                AblStepHandler.sendMsg(AblSteps.STEP_1);
                break;
            }
            default:
                break;
        }
    }
}
