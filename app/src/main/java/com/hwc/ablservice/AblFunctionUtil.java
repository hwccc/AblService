package com.hwc.ablservice;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hwc.abllib.AblConfig;
import com.hwc.abllib.AblStepBase;
import com.hwc.abllib.AblStepHandler;
import com.hwc.abllib.AblSteps;
import com.hwc.abllib.bean.AblStateBean;
import com.hwc.abllib.callback.AblSettingCallBack;
import com.hwc.abllib.utils.AblUtil;
import com.hwc.ablservice.mz.AppUsagePermissionsStep;
import com.hwc.ablservice.mz.BatteryOptimizedStep;
import com.hwc.ablservice.mz.DevicePolicyStep;
import com.hwc.ablservice.mz.LauncherStep;
import com.hwc.ablservice.mz.UsbDebugStep;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 无障碍功能
 *
 * @author hwc
 */
public class AblFunctionUtil {

    private static final String TAG = AblFunctionUtil.class.getSimpleName();

    private Map<Integer, AblStepBase> ablStepBaseMap;

    private volatile static AblFunctionUtil instance;

    private int currentStep;

    private boolean isSuccess;

    private AblSettingCallBack settingCallBack;

    public static AblFunctionUtil getInstance() {
        if (instance == null) {
            synchronized (AblFunctionUtil.class) {
                if (instance == null) {
                    instance = new AblFunctionUtil();
                }
            }
        }
        return instance;
    }

    private AblFunctionUtil() {
    }


    public void init() {
        AblConfig.Builder().setLogTag("AblService").setLog(true)
                .setStepMsgDelayMillis(1000)
                .setFindViewCountDownInterval(400)
                .setFindViewMillisInFuture(10000)
                .build().init();
        initStep();
    }

    /**
     * 初始化设置步骤
     * 1、设备管理器设置步骤
     * 2、电池优化设置步骤
     * 3、打开应用使用记录设置步骤
     * 4、Launcher设置步骤
     * 5、Usb设置步骤
     * 6、通知权限设置步骤
     */
    private void initStep() {
        ablStepBaseMap = new LinkedHashMap<>();
        ablStepBaseMap.put(1, new DevicePolicyStep());
        ablStepBaseMap.put(2, new BatteryOptimizedStep());
        ablStepBaseMap.put(3, new AppUsagePermissionsStep());
        ablStepBaseMap.put(4, new LauncherStep());
        ablStepBaseMap.put(5, new UsbDebugStep());
    }

    /**
     * 启动无障碍设置,设置步骤如下
     */
    public void start(AblSettingCallBack ablSettingCallBack) {
        if (AblUtil.checkAblService(FlowerApplication.getInstance())) {
            AblStepHandler.getInstance().setStop(false);
            settingCallBack = new AblSettingCallBack() {
                @Override
                public void onSuccess(AblStateBean ablStateBean) {
                    isSuccess = true;
                    currentStep = ablStateBean.currentStep;
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onSuccess(ablStateBean);
                    }
                }

                @Override
                public void onFail(AblStateBean ablStateBean) {
                    isSuccess = false;
                    currentStep = ablStateBean.currentStep;
                    LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onFail(ablStateBean);
                    }
                }

                @Override
                public void onSetEnd() {
                    isSuccess = false;
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onSetEnd();
                    }
                }
            };
            startStep(0, settingCallBack);
        } else {
            ToastUtils.showShort("请先开启辅助服务");
            AblUtil.openAccessibilitySettings();
        }
    }

    /**
     * 继续启动监测
     *
     * @param ablSettingCallBack
     */
    public void continueStart(AblSettingCallBack ablSettingCallBack) {
        if (isSuccess) {
            startStep(currentStep, new AblSettingCallBack() {
                @Override
                public void onSuccess(AblStateBean ablStateBean) {
                    isSuccess = true;
                    currentStep = ablStateBean.currentStep;
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onSuccess(ablStateBean);
                    }
                }

                @Override
                public void onFail(AblStateBean ablStateBean) {
                    isSuccess = false;
                    currentStep = ablStateBean.currentStep;
                    LogUtils.d(TAG, "ablStateBean: " + ablStateBean.toString());
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onFail(ablStateBean);
                    }
                }

                @Override
                public void onSetEnd() {
                    isSuccess = false;
                    if (ablSettingCallBack != null) {
                        ablSettingCallBack.onSetEnd();
                    }
                }
            });
        } else {
            ablSettingCallBack.onSetEnd();
        }
    }

    private boolean startStep(int currentStep, AblSettingCallBack ablSettingCallBack) {
        if (ablStepBaseMap != null && !ablStepBaseMap.isEmpty() && ablStepBaseMap.containsKey(currentStep + 1)) {
            currentStep = currentStep + 1;
            AblStepBase ablStepBase = ablStepBaseMap.get(currentStep);
            ablStepBase.setCurrentStep(currentStep);
            ablStepBase.setAblSettingCallBack(ablSettingCallBack);
            AblStepHandler.getInstance().setStop(false);
            AblStepHandler.getInstance().initStepClass(true, ablStepBase);
            AblStepHandler.sendMsg(AblSteps.STEP_1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 停止无障碍设置
     */
    public void stop() {
        AblStepHandler.getInstance().setStop(true);
        AblStepHandler.getInstance().clearListeners();
    }

    /**
     * 是否停止检测
     *
     * @return
     */
    public boolean isStop() {
        return AblStepHandler.getInstance().isStop();
    }
}
