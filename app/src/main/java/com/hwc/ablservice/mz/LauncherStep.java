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
import com.hwc.ablservice.FlowerApplication;
import com.hwc.ablservice.config.AppConfig;
import com.hwc.ablservice.utils.AppIntentUtils;
import com.hwc.ablservice.utils.AppUtils;

import java.util.List;

/**
 * Launcher设置步骤
 *
 * @author hwc
 */
public class LauncherStep extends AblStepBase implements AblSteps {

    public LauncherStep() {
    }

    public LauncherStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                // 设置默认桌面
                AppIntentUtils.getInstance().setLauncher(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                            AblStepHandler.getInstance().initStepClass(true, new UsbDebugStep(ablSettingCallBack));
                            AblStepHandler.sendMsg(AblSteps.STEP_1);
                        } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_2);
                        } else {
                            onCallBackFail(ablStateBean);
                        }
                    }

                    @Override
                    public void onFail(AblStateBean ablStateBean) {
                        onCallBackFail(ablStateBean);
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
                                    AblStepHandler.sendMsg(AblSteps.STEP_3);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_3);
                                }
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_3);
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
                                    AblStepHandler.sendMsg(AblSteps.STEP_4);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                                }
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
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = info.findAccessibilityNodeInfosByText(AppConfig.APP_NAME);
                                if (!accessibilityNodeInfos.isEmpty()) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_5);
                            }

                            @Override
                            public void fail() {
                                onCallBackFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                            }
                        }
                );
                break;
            }
            case STEP_5: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(AblSteps.STEP_6);
                break;
            }
            case STEP_6: {
                onCallBackSuccess(new AblStateBean(AblStateBean.STATE_SET_SUCCESS));
                AppUtils.moveToFront(FlowerApplication.getInstance());
                break;
            }
            default:
                break;
        }
    }
}
