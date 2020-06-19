package com.hwc.abllib.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtils {

    /**
     * 日志打印方法
     * 从什么地方开始调用的路径日志
     */
    public static void callFrom() {
        if (com.blankj.utilcode.util.LogUtils.getConfig().isLogSwitch()) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder log = new StringBuilder();
            for (int i = stackTrace.length - 1; i >= 0; i--) {
                StackTraceElement stackTraceElement = stackTrace[i];
                String className = stackTraceElement.getClassName();
                String fileName = stackTraceElement.getFileName();
                String methodName = stackTraceElement.getMethodName();
                int lineNumber = stackTraceElement.getLineNumber();
                if (fileName != null) {
                    fileName = fileName.replace(".java", "");
                }
                if (TextUtils.equals(methodName, "callFrom")) {
                    //过滤掉当前callFrom打印方法的输出
                } else {
                    log.append("->").append(fileName).append(".").append(methodName).append("(").append(lineNumber).append(") \n");
                }
            }
            Log.d("AblService", log.toString());
        }
    }
}
