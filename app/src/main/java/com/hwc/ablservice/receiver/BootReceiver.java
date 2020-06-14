package com.hwc.ablservice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.hwc.ablservice.service.NotificationMonitorService;

/**
 * @author: hwc
 * date:   On 2020/6/10
 */
public class BootReceiver extends BroadcastReceiver {

    private final static String TAG = BootReceiver.class.getSimpleName();

    public final static String GRAY_WAKE_ACTION = "com.abl.wake";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        String action = intent.getAction();
        Log.i("notify通知WakeReceiver:", "wake !! wake !! " + intent.getAction());
        switch (action) {
            case GRAY_WAKE_ACTION:
            case Intent.ACTION_BOOT_COMPLETED:
            case Intent.ACTION_USER_PRESENT:
            case ConnectivityManager.CONNECTIVITY_ACTION:
                Intent wakeIntent = new Intent(context, NotificationMonitorService.class);
                context.startService(wakeIntent);
                break;
            default:
                break;
        }
    }
}
