package com.hwc.abllib;

import com.blankj.utilcode.util.LogUtils;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public abstract class AblStepBase implements AblStepHandler.StepListener {

    protected String TAG = getClass().getSimpleName();

    protected AblSettingCallBack ablSettingCallBack;

    /**
     * 当前步骤
     */
    protected int currentStep;

    public AblStepBase() {
    }

    public AblStepBase(AblSettingCallBack ablSettingCallBack) {
        this.ablSettingCallBack = ablSettingCallBack;
    }

    public void setAblSettingCallBack(AblSettingCallBack ablSettingCallBack) {
        this.ablSettingCallBack = ablSettingCallBack;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * 成功回调
     */
    public void onCallBackSuccess(AblStateBean ablStateBean) {
       onCallBackSuccess(true, ablStateBean);
    }

    /**
     * 成功回调
     */
    public void onCallBackSuccess(boolean isStop, AblStateBean ablStateBean) {
        LogUtils.d(TAG, "onCallBackSuccess: " + ablSettingCallBack);
        if (ablSettingCallBack != null) {
            AblStepHandler.getInstance().setStop(isStop);
            ablStateBean.currentStep = currentStep;
            ablSettingCallBack.onSuccess(ablStateBean);
        }
    }

    /**
     * 失败回调
     */
    public void onCallBackFail(AblStateBean ablStateBean) {
        LogUtils.d(TAG, "onCallBackFail: " + ablSettingCallBack);
        if (ablSettingCallBack != null) {
            AblStepHandler.getInstance().setStop(true);
            if (ablStateBean == null) {
                ablStateBean = new AblStateBean();
            }
            ablStateBean.currentStep = currentStep;
            ablSettingCallBack.onFail(ablStateBean);
        }
    }


    /**
     * 结束回调
     */
    public void onCallBackEnd() {
        LogUtils.d(TAG, "onCallBackEnd: " + ablSettingCallBack);
        if (ablSettingCallBack != null) {
            AblStepHandler.getInstance().setStop(true);
            ablSettingCallBack.onSetEnd();
        }
    }

}
