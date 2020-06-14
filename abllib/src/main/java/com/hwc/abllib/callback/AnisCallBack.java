package com.hwc.abllib.callback;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public interface AnisCallBack {
    void succ(List<AccessibilityNodeInfo> infos);

    default void fail() {
    }
}
