package com.hwc.ablservice;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hwc.abllib.AblConfig;
import com.hwc.abllib.AblStepHandler;
import com.hwc.abllib.AblSteps;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.abllib.utils.AblUtil;
import com.hwc.ablservice.mz.DevicePolicyStep;
import com.hwc.ablservice.utils.AdbUtils;

/**
 * @author hwc
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        AblConfig.Builder().setLogTag("AblService").setLog(true)
                .setStepMsgDelayMillis(1000)
                .setFindViewCountDownInterval(200)
                .setFindViewMillisInFuture(10000)
                .build().init();
        initView();
        AblFunctionUtil.getInstance().init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        AblFunctionUtil.getInstance().continueStart(new AblSettingCallBack() {
            @Override
            public void onSuccess(AblStateBean ablStateBean) {
                Log.d(TAG, "onSuccess ablStateBean: " + ablStateBean);
            }

            @Override
            public void onFail(AblStateBean ablStateBean) {
                Log.d(TAG, "onFail ablStateBean: " + ablStateBean);
            }

            @Override
            public void onSetEnd() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void initView() {
        findViewById(R.id.bt_onAppUsagePermissions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启查看应用使用权限
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_onSetLauncher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.bt_onUsbDebugging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭USB调试
                AdbUtils.getInstance().setUsbDebugging(new AblSettingCallBack() {
                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {

                    }

                    @Override
                    public void onFail(AblStateBean ablStateBean) {

                    }
                });
            }
        });
        findViewById(R.id.bt_battery_optimization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //电池优化
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        findViewById(R.id.bt_openNotification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开通知权限
                //启动无障碍设置
//                AblStepHandler.getInstance().initStepClass(true, new DefaultLauncherStep(new AblSettingCallBack() {
//
//                    @Override
//                    public void onSuccess(AblStateBean ablStateBean) {
//                        LogUtils.d(TAG, "onSuccess");
//                    }
//
//                    @Override
//                    public void onFail() {
//                        LogUtils.d(TAG, "onFail");
//                    }
//                }));
//                if (AblUtil.checkAblService(MainActivity.this)) {
//                    AblStepHandler.getInstance().setStop(false);
//                    AblStepHandler.sendMsg(AblSteps.STEP_1);
//                } else {
//                    ToastUtils.showShort("请先开启辅助服务");
//                    AblUtil.openAccessibilitySettings();
//                }
            }
        });
        findViewById(R.id.bt_closeSuspendedBall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  TODO 关闭悬浮球,可以不进行关闭，关闭也无效
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ComponentName cn = new ComponentName("com.android.settings", "com.meizu.settings.MzSettingsActivity$FlymeNavigationAndRecentsSettingsActivity");
//                intent.setComponent(cn);
//                startActivity(intent);
            }
        });
        findViewById(R.id.bt_startAccessibilitySettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动无障碍设置
                AblFunctionUtil.getInstance().start((new AblSettingCallBack() {

                    @Override
                    public void onSuccess(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "onSuccess");
                    }

                    @Override
                    public void onFail(AblStateBean ablStateBean) {
                        LogUtils.d(TAG, "onFail");
                    }

                    @Override
                    public void onSetEnd() {
                        LogUtils.d(TAG, "onSetEnd");
                    }
                }));
            }
        });
    }
}
