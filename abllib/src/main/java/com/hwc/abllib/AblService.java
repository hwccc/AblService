package com.hwc.abllib;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.LogUtils;


/**
 * @author hwc
 */
public class AblService extends AccessibilityService {

    private static final String TAG = AblService.class.getSimpleName();

    private static AblService mAblService;

    public static AblService getInstance() {
        if (mAblService == null) {
            throw new NullPointerException("AblService辅助服务未开启");
        }
        return mAblService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAblService = this;
        init();
    }

    private void init() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LogUtils.v("onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        LogUtils.d();
        LogUtils.d("==============Start====================");
        LogUtils.d("eventType: " + eventType);
        AccessibilityNodeInfo noteInfo = event.getSource();
        if (noteInfo != null) {
            LogUtils.d(noteInfo.toString());
        }
        LogUtils.d("=============END=====================");
    }

    @Override
    public void onInterrupt() {

    }
}
