package com.hwc.abllib;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public class AblConfig {

    private String mLogTag;

    private boolean mIsLog;

    //消息延迟发送时间
    private long mStepMsgDelayMillis;

    //查找view倒计时总时长
    private long findViewMillisInFuture;

    //查找view检查间隔时间
    private long findViewCountDownInterval;

    /**
     * 所监测的APP包名，如果不设置将会监听整个系统的界面变化，
     * 如果设置后只会监听指定包名的APP
     * 【暂时不用】
     */
    public static String[] sMonitoringPackageNames;

    private AblConfig(Builder builder) {
        mLogTag = builder.mLogTag;
        mIsLog = builder.mIsLog;
        sMonitoringPackageNames = builder.mMonitoringPackageNames;
        mStepMsgDelayMillis = builder.mStepMsgDelayMillis;
        findViewCountDownInterval = builder.findViewCountDownInterval;
        findViewMillisInFuture = builder.findViewMillisInFuture;
    }

    /**
     * 初始化
     */
    public void init() {
        LogUtils.getConfig().setGlobalTag(mLogTag);
        LogUtils.getConfig().setLogSwitch(mIsLog);
        AblStepHandler.getInstance().setStepMsgDelayMillis(mStepMsgDelayMillis);
        FindViewCountDown.countDownInterval = findViewCountDownInterval;
        FindViewCountDown.millisInFuture = findViewMillisInFuture;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private String mLogTag = "abllib";
        private boolean mIsLog = true;
        private String[] mMonitoringPackageNames;

        //步骤延迟发送时间
        private long mStepMsgDelayMillis = 2000;

        //查找view倒计时总时长
        private long findViewMillisInFuture = 10000;

        //查找view检查间隔时间
        private long findViewCountDownInterval = 500;

        public Builder setLogTag(String logTag) {
            mLogTag = logTag;
            return this;
        }

        public Builder setLog(boolean log) {
            mIsLog = log;
            return this;
        }

        public Builder setMonitoringPackageNames(String... monitoringPackageNames) {
            mMonitoringPackageNames = monitoringPackageNames;
            return this;
        }

        public Builder setStepMsgDelayMillis(long stepMsgDelayMillis) {
            mStepMsgDelayMillis = stepMsgDelayMillis;
            return this;
        }

        public Builder setFindViewMillisInFuture(long findViewMillisInFuture) {
            this.findViewMillisInFuture = findViewMillisInFuture;
            return this;
        }

        public Builder setFindViewCountDownInterval(long findViewCountDownInterval) {
            this.findViewCountDownInterval = findViewCountDownInterval;
            return this;
        }

        public AblConfig build() {
            return new AblConfig(this);
        }
    }
}
