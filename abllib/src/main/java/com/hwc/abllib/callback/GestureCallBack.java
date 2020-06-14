package com.hwc.abllib.callback;

import android.accessibilityservice.GestureDescription;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public interface GestureCallBack {
    void succ(GestureDescription gestureDescription);

    void fail(GestureDescription gestureDescription);
}
