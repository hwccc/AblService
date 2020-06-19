package com.hwc.abllib;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hwc
 * date:   On 2020/6/8
 */
public class AblStepHandler extends Handler {

    private List<StepListener> mStepListeners = new ArrayList<>();
    //是否停止
    private boolean isStop = true;
    //数据
    private String[] mDatas;
    //消息延迟发送时间
    private long mStepMsgDelayMillis = 2000;
    //已经初始化过的步骤
    private List<String> mInitializedSteps = new ArrayList<>();

    private volatile static AblStepHandler instance;

    private AblStepHandler() {
        super(Looper.getMainLooper());
    }

    public static AblStepHandler getInstance() {
        if (instance == null) {
            synchronized (AblStepHandler.class) {
                if (instance == null) {
                    instance = new AblStepHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 发送handler执行消息
     *
     * @param step        步骤
     * @param delayMillis 延迟时间
     * @param datas       数据
     */
    public static void sendMsg(int step, long delayMillis, String... datas) {
        AblStepHandler ablStepHandler = AblStepHandler.getInstance();
        if (datas != null && datas.length > 0) {
            ablStepHandler.setDatas(datas);
        }
        ablStepHandler.sendEmptyMessageDelayed(step, delayMillis);
    }

    /**
     * 发送handler执行消息
     *
     * @param step        步骤
     */
    public static void sendTimeMsg(int step) {
        sendMsg(step, 200, new String[]{});
    }

    /**
     * 发送handler执行消息
     *
     * @param step        步骤
     * @param delayMillis 延迟时间
     */
    public static void sendMsg(int step, long delayMillis) {
        sendMsg(step, delayMillis, new String[]{});
    }

    /**
     * 发送handler执行消息
     *
     * @param step  步骤
     * @param datas 数据
     */
    public static void sendMsg(int step, String... datas) {
        sendMsg(step, AblStepHandler.getInstance().getStepMsgDelayMillis(), datas);
    }

    /**
     * 发送handler执行消息
     *
     * @param step 步骤
     */
    public static void sendMsg(int step) {
        sendMsg(step, AblStepHandler.getInstance().getStepMsgDelayMillis(), new String[]{});
    }

    /**
     * 根据viewid或文字发送执行消息
     *
     * @param millisInFuture    view查找超时时间
     * @param countDownInterval 查找间隔时间
     * @param step              步骤
     */
    public static void sendMsgByView(
            long millisInFuture, long countDownInterval,
            int step, String... nextMsgViewIdOrText) {
        FindViewCountDown.start(millisInFuture, countDownInterval, step, nextMsgViewIdOrText);
    }

    /**
     * 根据viewid或文字发送执行消息
     *
     * @param millisInFuture    view查找超时时间
     * @param countDownInterval 查找间隔时间
     * @param callBack          回调，注：如果回调不为空将不发送消息，要发送消息就自己在成功的回调里发送
     * @param viewIdOrText      viewid或者文字内容
     */
    public static void sendMsgByView(
            long millisInFuture, long countDownInterval,
            @NonNull FindViewCountDown.CallBack callBack,
            String... viewIdOrText) {
        FindViewCountDown.start(millisInFuture, countDownInterval, callBack, viewIdOrText);
    }

    /**
     * 根据viewid或文字发送执行消息
     *
     * @param step         步骤
     * @param viewIdOrText viewid或者文字内容
     */
    public static void sendMsgByView(
            int step, String... viewIdOrText) {
        FindViewCountDown.start(step, viewIdOrText);
    }

    /**
     * 根据viewid或文字发送执行消息
     *
     * @param callBack     回调，注：如果回调不为空将不发送消息，要发送消息就自己在成功的回调里发送
     * @param viewIdOrText viewid或者文字内容
     */
    public static void sendMsgByView(
            @NonNull FindViewCountDown.CallBack callBack,
            String... viewIdOrText) {
        FindViewCountDown.start(callBack, viewIdOrText);
    }


    public String[] getDatas() {
        return mDatas;
    }

    public void setDatas(String... datas) {
        mDatas = datas;
    }

    public long getStepMsgDelayMillis() {
        return mStepMsgDelayMillis;
    }

    public void setStepMsgDelayMillis(long msgDelayMillis) {
        mStepMsgDelayMillis = msgDelayMillis;
    }

    public boolean isStop() {

        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (isStop) return;
        LogUtils.v("step", msg.what);
        for (StepListener stepListener : mStepListeners) {
            stepListener.onStep(msg.what, msg);
        }
    }

    private void addStepListener(StepListener handleMessageListener) {
        mStepListeners.add(handleMessageListener);
    }

    public void removeStepListener(StepListener handleMessageListener) {
        mStepListeners.remove(handleMessageListener);
    }

    public void clearListeners() {
        mStepListeners.clear();
        mInitializedSteps.clear();
        ;
    }

    /**
     * 初始化步骤实现类
     *
     * @param ablStepBases 继承了StepBase的实现类
     */
    public void initStepClass(boolean isClearListeners, AblStepBase... ablStepBases) {
        if (isClearListeners) {
            clearListeners();
        }
        for (AblStepBase ablStepBase : ablStepBases) {
            if (mInitializedSteps.contains(ablStepBase.getClass().getName())) {
                LogUtils.e(ablStepBase.getClass().getName() + "已经初始化，请勿重复初始化");
                continue;
            }
            addStepListener(ablStepBase);
            mInitializedSteps.add(ablStepBase.getClass().getName());
        }
    }

    /**
     * 初始化步骤实现类
     *
     * @param ablStepBases 继承了StepBase的实现类
     */
    public void initStepClass(AblStepBase... ablStepBases) {
        initStepClass(false, ablStepBases);
    }


    public interface StepListener {
        void onStep(int step, Message msg);
    }

}
