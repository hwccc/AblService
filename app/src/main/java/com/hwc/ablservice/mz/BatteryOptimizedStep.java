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
import com.hwc.ablservice.config.AppConfig;
import com.hwc.ablservice.utils.AppIntentUtils;
import com.hwc.ablservice.utils.BatteryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 电池优化设置步骤
 *
 * @author hwc
 */
public class BatteryOptimizedStep extends AblStepBase implements AblSteps {
    public BatteryOptimizedStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                // 设置耗电保护
                AblStateBean ablStateBean = BatteryUtils.closeBatteryOptimization();
                if (ablStateBean.state == AblStateBean.STATE_HAVE) {
                    AblStepHandler.sendMsg(STEP_4);
                } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                    AblStepHandler.sendMsg(STEP_2);
                } else {
                    onCallBackFail();
                }
                break;
            }
            case STEP_2: {
                // 关闭耗电保护
                AblViewUtil.findById("android:id/button1",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_3);
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                AblStepHandler.sendMsg(STEP_4);
                break;
            }
            case STEP_4: {
                AppIntentUtils.setBattery(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_5);
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
            case STEP_5: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText(info, "特殊权限");
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_6);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(AblSteps.STEP_6);
                            }
                        }
                );
                break;
            }
            case STEP_6: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(accessibilityNodeInfos, info, "电池优化", true);
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    if (AblViewUtil.clickNodeInfo(child)) {
                                        AblStepHandler.sendMsg(AblSteps.STEP_7, 1500);
                                        return;
                                    }
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_7);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(AblSteps.STEP_7);
                            }
                        }
                );
                break;
            }
            case STEP_7: {
                AblViewUtil.findById("com.android.settings:id/filter_spinner",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_8);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(STEP_8);
                            }
                        }
                );
                break;
            }
            case STEP_8: {
                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText("所有应用");
                if (!accessibilityNodeInfos.isEmpty()) {
                    accessibilityNodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                AblStepHandler.sendMsg(STEP_9, 1000);
                break;
            }

            case STEP_9: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(nodeInfos, info, AppConfig.APP_NAME, true);
                                if (nodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = nodeInfos.get(0).getParent();
                                    if (AblViewUtil.clickNodeInfo(child)) {
                                        AblStepHandler.sendMsg(AblSteps.STEP_10, 1000);
                                        return;
                                    }
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_10, 1000);


                            }
                        }
                );
                break;
            }
            case STEP_10: {
                AblViewUtil.findById("com.android.settings:id/ignore_on",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_11);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(STEP_11);
                            }
                        }
                );
                break;
            }
            case STEP_11: {
                AblViewUtil.findById("android:id/button1",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_12);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendMsg(STEP_12);
                            }
                        }
                );
                break;
            }

            case STEP_12: {
                AblViewUtil.back();
                AblStepHandler.sendMsg(AblSteps.STEP_13);
                break;
            }
            case STEP_13: {
                AblViewUtil.back();
                AblStepHandler.sendMsg(AblSteps.STEP_14);
                break;
            }

            case STEP_14: {
                AblViewUtil.back();
                // 回到程序无障碍设置界面， 开启查看应用使用权限
                AblStepHandler.getInstance().initStepClass(true, new AppUsagePermissionsStep(ablSettingCallBack));
                AblStepHandler.sendMsg(STEP_1);
                break;
            }
            default:
                break;
        }
    }
}
