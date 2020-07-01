package com.hwc.ablservice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import com.hwc.ablservice.utils.DeviceKeyMonitor;

/**
 * @author hwc
 */
public class TestActivity extends AppCompatActivity implements DeviceKeyMonitor.OnKeyListener {

    private final String TAG = TestActivity.class.getSimpleName();

    private DeviceKeyMonitor mKeyMonitor;
    private boolean isClickRecent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().addFlags(Window.FEATURE_INDETERMINATE_PROGRESS);
        mKeyMonitor = new DeviceKeyMonitor(this, this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isClickRecent) {
            isClickRecent = false;
            //处理按了多任务键的逻辑
        } else {
            //处理原本的resume逻辑
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isClickRecent) {
            ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.moveTaskToFront(getTaskId(), 0);
        } else {
            //处理原本的pause逻辑
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("DeviceKeyMonitor", "按了返回键");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onHomeClick() {
        Log.d("DeviceKeyMonitor", "按了Home键");
    }

    @Override
    public void onRecentClick() {
        isClickRecent = true;
        Log.d("DeviceKeyMonitor", "按了多任务键");
    }

    @Override
    protected void onDestroy() {
        mKeyMonitor.unregister();
        super.onDestroy();
    }
}

