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

    public AblStepBase(AblSettingCallBack ablSettingCallBack) {
        this.ablSettingCallBack = ablSettingCallBack;
    }

    /**
     * 成功回调
     */
    public void onCallBackSuccess(AblStateBean ablStateBean) {
        LogUtils.d(TAG, "onCallBackSuccess: " + ablSettingCallBack);
        if (ablSettingCallBack != null) {
            AblStepHandler.getInstance().setStop(true);
            ablSettingCallBack.onSuccess(ablStateBean);
        }
    }

    /**
     * 失败回调
     */
    public void onCallBackFail() {
        LogUtils.d(TAG, "onCallBackFail: " + ablSettingCallBack);
        if (ablSettingCallBack != null) {
            AblStepHandler.getInstance().setStop(true);
            ablSettingCallBack.onFail();
        }
    }

}
