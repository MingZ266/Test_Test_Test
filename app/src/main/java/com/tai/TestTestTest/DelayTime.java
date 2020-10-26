package com.tai.TestTestTest;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

class DelayTime {
    void Show(final Context context, int delayTime) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "成功召唤！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        };
        new Timer().schedule(task, delayTime);
    }
}
