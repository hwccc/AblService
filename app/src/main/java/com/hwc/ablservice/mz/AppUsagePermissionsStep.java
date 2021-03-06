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

import java.util.ArrayList;
import java.util.List;

/**
 * 打开应用使用记录设置步骤
 *
 * @author hwc
 */
public class AppUsagePermissionsStep extends AblStepBase implements AblSteps {

    public AppUsagePermissionsStep() {
    }

    public AppUsagePermissionsStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                AppIntentUtils.getInstance().openAppUsagePermissions(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                        if (ablStateBean.state == AblStateBean.STATE_FIND_INTENT) {
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
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText(info, "特殊权限");
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                }
                                AblStepHandler.sendMsg(AblSteps.STEP_3);
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
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(accessibilityNodeInfos, info, "使用记录访问", true);
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                    AblStepHandler.sendMsg(AblSteps.STEP_4, 3000);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                                }
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                            }
                        }
                );
                break;
            }
            case STEP_4: {
                AblViewUtil.findById("android:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = new ArrayList<>();
                                AblViewUtil.findByText(accessibilityNodeInfos, info, AppConfig.APP_NAME, true);
                                if (accessibilityNodeInfos.size() > 0) {
                                    AccessibilityNodeInfo child = accessibilityNodeInfos.get(0).getParent();
                                    AblViewUtil.clickNodeInfo(child);
                                    AblStepHandler.sendMsg(AblSteps.STEP_5);
                                } else {
                                    AblStepHandler.sendTimeMsg(AblSteps.STEP_5);
                                }
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
                AblViewUtil.findById("com.android.settings:id/flyme_switch", 0, new AniCallBack() {
                    @Override
                    public void succ(AccessibilityNodeInfo info) {
                        AblViewUtil.clickOpenCheckNodeInfo(info, true);
                        AblStepHandler.sendTimeMsg(AblSteps.STEP_6);
                    }

                    @Override
                    public void fail() {
                        onCallBackFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                    }
                });
                break;
            }
            case STEP_6: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(AblSteps.STEP_7);
                break;
            }
            case STEP_7: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(AblSteps.STEP_8);
                break;
            }
            case STEP_8: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(AblSteps.STEP_9);
                break;
            }
            case STEP_9: {
                onCallBackSuccess(new AblStateBean(AblStateBean.STATE_SET_SUCCESS));
                AppUtils.moveToFront(FlowerApplication.getInstance());
                break;
            }
            default:
                break;
        }
    }
}
