package com.mirea.fedotov.looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.concurrent.TimeUnit;


public class MyLooper extends Thread{
    Handler handler;
    public int timeSleep = 19;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int age = msg.getData().getInt("age");
                try {
                    TimeUnit.SECONDS.sleep(age);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MyLooper", "My age" + ": " + age);
                Log.d("MyLooper", "My work" + ": "+ msg.getData().getString("work"));
            }
        };
        Looper.loop();
    }
}
