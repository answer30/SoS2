package com.mirine.sos2020.background;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.Calendar;

import androidx.annotation.Nullable;

public class PowerSwichService extends Service {

    public static Intent serviceIntent;
    MainRecever receiver;
    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MainRecever();

        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);

        serviceIntent = null;
        setAlarmTimer();
        Thread.currentThread().interrupt();

    }

    protected void setAlarmTimer() {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 1);
        Intent intent = new Intent(this, AlarmRecever.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0,intent,0);

        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

