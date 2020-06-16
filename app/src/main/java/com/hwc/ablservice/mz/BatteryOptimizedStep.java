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
import com.hwc.ablservice.utils.BatteryUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 电池优化设置步骤
 *
 * @author hwc
 */
public class BatteryOptimizedStep extends AblStepBase implements AblSteps {

    public BatteryOptimizedStep() {
    }

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
                    AblStepHandler.sendMsg(STEP_3);
                } else if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                    AblStepHandler.sendMsg(STEP_2, 3000);
                } else {
                    onCallBackFail(ablStateBean);
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
                                AblStepHandler.sendMsg(STEP_3, 3000);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(STEP_3);
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                AppIntentUtils.getInstance().setBattery(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
                            AblStepHandler.sendMsg(STEP_4);
                        } else {
                            onCallBackFail(ablStateBean);
                        }
                    }

                    @Override
                    public void onFail(AblStateBean ablStateBean) {
                        AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                    }

                });
                break;
            }
            case STEP_4: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText(info, "特殊权限");
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_5);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_5);
                            }
                        }
                );
                break;
            }
            case STEP_5: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(accessibilityNodeInfos, info, "电池优化", true);
                                LogUtils.d(TAG, "size: " + accessibilityNodeInfos.size());
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                    AblStepHandler.sendMsg(AblSteps.STEP_6);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_6);
                                }
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_6);
                            }
                        }
                );
                break;
            }
            case STEP_6: {
                AblViewUtil.findById("com.android.settings:id/filter_spinner",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_7);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(STEP_7);
                            }
                        }
                );
                break;
            }
            case STEP_7: {
                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText("所有应用");
                if (!accessibilityNodeInfos.isEmpty()) {
                    accessibilityNodeInfos.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                AblStepHandler.sendMsg(STEP_8, 1500);
                break;
            }

            case STEP_8: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(nodeInfos, info, AppConfig.APP_NAME, true);
                                if (nodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = nodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                    AblStepHandler.sendMsg(AblSteps.STEP_9);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_9);
                                }
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_9);
                            }
                        }
                );
                break;
            }
            case STEP_9: {
                AblViewUtil.findById("com.android.settings:id/ignore_on",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendMsg(STEP_10, 1500);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(STEP_10);
                            }
                        }
                );
                break;
            }
            case STEP_10: {
                AblViewUtil.findById("android:id/button1",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                AblStepHandler.sendTimeMsg(STEP_11);
                            }

                            @Override
                            public void fail() {
                                onCallBackFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                            }
                        }
                );
                break;
            }
            case STEP_11: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(STEP_12);
                break;
            }
            case STEP_12: {
                onCallBackSuccess(new AblStateBean(AblStateBean.STATE_SET_SUCCESS));
                AppUtils.moveToFront(FlowerApplication.getInstance());
                break;
            }
            default:
                break;
        }
    }
}
