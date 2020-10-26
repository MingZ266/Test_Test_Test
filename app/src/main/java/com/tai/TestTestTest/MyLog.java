package com.tai.TestTestTest;

import android.util.Log;

import androidx.annotation.NonNull;

class MyLog {
    //控制是否输出Log
    private static final boolean Debug = true;

    static void i(String TAG, DebugInfo d, String Msg) {
        if (Debug)
            Log.i(TAG, d.toString() + Msg);
    }

    static void i(String TAG, DebugInfo d, String Msg, Throwable e) {
        if (Debug)
            Log.i(TAG, d.toString() + Msg, e);
    }

    static void w(String TAG, DebugInfo d, String Msg) {
        if (Debug)
            Log.w(TAG, d.toString() + Msg);
    }

    static void e(String TAG, DebugInfo d, String Msg) {
        if (Debug)
            Log.e(TAG, d.toString() + Msg);
    }
}

class DebugInfo extends Exception {
    private boolean noAtDrawLayout = true;
    private int num;

    DebugInfo() {
        super();
    }

    DebugInfo(int num) {
        this.num = num;
        noAtDrawLayout = false;
    }

    @NonNull
    @Override
    public String toString() {
        if (noAtDrawLayout) {
            StackTraceElement[] trace = getStackTrace();
            if (trace.length == 0)
                return "";
            return "第" + trace[0].getLineNumber() + "行"
                    /* + "类" + trace[0].getClassName() + "的" + trace[0].getMethodName() + "方法"*/
                    + ": ";
        } else {
            return "第" + num + "行: ";
        }
    }
}
