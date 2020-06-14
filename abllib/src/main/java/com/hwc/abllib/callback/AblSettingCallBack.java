package com.hwc.abllib.callback;

import com.hwc.abllib.bean.AblStateBean;

public interface AblSettingCallBack {

    /**
     * 成功回调
     */
    void onSuccess(AblStateBean ablStateBean);

    /**
     * 失败回调
     */
    void onFail();
}
