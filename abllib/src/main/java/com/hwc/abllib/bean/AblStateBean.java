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
     * 当前状态
     */
    public int state = -1;

    public AblStateBean() {

    }

    public AblStateBean(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AblStateBean{" +
                "state=" + state +
                '}';
    }
}
