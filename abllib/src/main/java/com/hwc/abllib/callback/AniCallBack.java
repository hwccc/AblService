package com.hwc.abllib.callback;

import android.view.accessibility.AccessibilityNodeInfo;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public interface AniCallBack {

    void succ(AccessibilityNodeInfo info);

    default void fail() {
    }
}
