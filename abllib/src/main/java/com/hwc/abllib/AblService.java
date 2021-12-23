package com.hwc.abllib;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.text.TextUtils;
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
        setForegroundService();
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
        LogUtils.d("==============Start====================");
        if ((event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
                && TextUtils.equals("com.android.systemui", event.getPackageName())
                && TextUtils.equals("android.widget.FrameLayout", event.getClassName())) {
            LogUtils.d("recent");
        }
        int eventType = event.getEventType();
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

    /**
     * 设置为前台进程服务
     */
    private void setForegroundService() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(TAG, TAG, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), TAG).build();
            startForeground(1, notification);
        }
    }
}
