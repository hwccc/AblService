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
import com.hwc.ablservice.utils.NotificationUtils;

import java.util.List;

/**
 * 通知权限设置步骤
 *
 * @author hwc
 */
public class NoticeReadStep extends AblStepBase implements AblSteps {

    public NoticeReadStep() {
    }

    public NoticeReadStep(AblSettingCallBack ablSettingCallBack) {
        super(ablSettingCallBack);
    }

    @Override
    public void onStep(int step, Message msg) {
        switch (step) {
            case STEP_1: {
                //打开通知权限
                NotificationUtils.setNotification(new AblSettingCallBack() {
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
                        onCallBackFail(ablStateBean);
                    }
                });

                break;
            }
            case STEP_2: {
                AblViewUtil.findById("com.android.settings:id/list",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                List<AccessibilityNodeInfo> accessibilityNodeInfos = AblViewUtil.findByText(AppConfig.APP_NAME);
                                if (!accessibilityNodeInfos.isEmpty()) {
                                    AccessibilityNodeInfo parent = accessibilityNodeInfos.get(0).getParent();
                                    if (parent != null) {
                                        List<AccessibilityNodeInfo> byId = AblViewUtil.findById(parent, "android:id/checkbox");
                                        if (!byId.isEmpty()) {
                                            if (AblViewUtil.clickOpenCheckNodeInfo(byId.get(0), true)) {
                                                AblStepHandler.sendMsg(AblSteps.STEP_3);
                                                return;
                                            }
                                        }
                                    }
                                }
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                            }

                            @Override
                            public void fail() {
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                            }
                        }
                );
                break;
            }
            case STEP_3: {
                AblViewUtil.findById("android:id/button1",
                        0, new AniCallBack() {
                            @Override
                            public void succ(AccessibilityNodeInfo info) {
                                AblViewUtil.clickNodeInfo(info);
                                AblStepHandler.sendTimeMsg(AblSteps.STEP_4);
                            }

                            @Override
                            public void fail() {
                                onCallBackFail(new AblStateBean(AblStateBean.STATE_NO_FIND_INTENT));
                            }
                        }
                );
                break;
            }
            case STEP_4: {
                AblViewUtil.back();
                AblStepHandler.sendTimeMsg(AblSteps.STEP_5);
                break;
            }
            case STEP_5: {
                onCallBackSuccess(new AblStateBean(AblStateBean.STATE_SET_SUCCESS));
                AppUtils.moveToFront(FlowerApplication.getInstance());
                break;
            }
            default:
                break;
        }
    }
}
