package com.hwc.abllib.bean;

public class AblStateBean {

    /**
     * 已经拥有
     */
    public static int STATE_HAVE = 0;

    /**
     * 找到意图
     */
    public static int STATE_FIND_INTENT = 1;

    /**
     * 找不到意图
     */
    public static int STATE_NO_FIND_INTENT = 2;

    /**
     * 跳转回界面
     */
    public static int STATE_JUMP_ACTIVITY = 3;

    /**
     * 设置成功
     */
    public static int STATE_SET_SUCCESS = 4;

    /**
     * 当前状态
     */
    public int state = -1;

    /**
     * 当前步骤
     */
    public int currentStep = -1;

    /**
     * 是否自动跳转Activity
     */
    public boolean isAutoJumpActivity = true;


    public AblStateBean() {

    }

    public AblStateBean(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AblStateBean{" +
                "state=" + state +
                ", currentStep=" + currentStep +
                ", isAutoJumpActivity=" + isAutoJumpActivity +
                '}';
    }
}
