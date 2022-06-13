package com.mirine.sos2020.background;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mirine.sos2020.CountDownActivity;
import com.mirine.sos2020.common.AudioManager;
import com.mirine.sos2020.common.Constants;
import com.mirine.sos2020.common.GPSManager;
import com.mirine.sos2020.common.SendMessage;
import com.mirine.sos2020.lib.Preference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainRecever extends BroadcastReceiver {

    private final static String SCREEN_COUNT = "screen_count";
    private static AudioManager audioManager;

    public static boolean sending;
    public static int timer;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {


        if(intent.getAction().equals(Intent.ACTION_SCREEN_ON) || intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            if(sending) {
                return;
            }
            this.screenTimerStart(context);

            if(screenCountPlus(context) == 4) {
                this.onSos(context);
            }

        } else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, PowerSwichService.class);
            context.startService(serviceIntent);

        } else if(intent.getAction().equals(Constants.RECEIVER_SEND_MESSAGE_CANCEL)) {
            this.cancelSos(context);

        }
    }

    // SOS 취소
    private void cancelSos(Context context) {
        if(audioManager != null) {
            audioManager.stopRecording();
            audioManager = null;
        }

        sending = false;
        GPSManager.getInstance(context).removeLocationListener();
    }

    // SOS 시작
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onSos(final Context context) {
        GPSManager.getInstance(context).startLocationCheck();

        sending = true;
        this.onNoiseMode(context);
        this.wakeUpScreen(context);
        Intent itt = new Intent(context, CountDownActivity.class);
        itt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        itt.putExtra(CountDownActivity.LOCK_ON, true);
        context.startActivity(itt);

        new Thread() {
            @Override
            public void run() {
                try {
                    timer = 0;
                    while(timer < 10 && sending) {
                        Thread.sleep(1000);
                        timer += 1;
                        try {
                            CountDownActivity.instance.setCountText(10 - timer);
                        } catch (Exception e) {

                        }

                    }
                    timer = 0;
                    if(sending) {
                        try {
                            MainRecever.this.sendAddressMessage(context);
                            MainRecever.this.sendAudioMessage(context);
                            CountDownActivity.instance.setSendSuccess();

                        } catch (Exception e) {
                            CountDownActivity.instance.setSendSuccess();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void onNoiseMode(Context context) { // 무음
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (notificationManager.isNotificationPolicyAccessGranted()) {
                android.media.AudioManager mAudioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mAudioManager.setRingerMode(android.media.AudioManager.RINGER_MODE_SILENT);
            }
        }

    }

    // 오디오 메시지 보내기
    private void sendAudioMessage(final Context context) {
        audioManager = new AudioManager(context, Environment.getExternalStorageDirectory(), "(" + System.currentTimeMillis() + ")");
        audioManager.recordeAudio(60, new AudioManager.RecordeSuccessListener() {
            @Override
            public void success(String audioPath) {
                if(sending) {
                    String phone =  Preference.get(context).getPreference(Constants.PREF_RECEIVER , "");
                    String phone2 =  Preference.get(context).getPreference(Constants.PREF_RECEIVER_SUB , "");

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.sendMMSWithAudio(context, phone, audioPath);
                    sendMessage.sendMMSWithAudio(context, phone2, audioPath);

                    audioManager = null;

                    GPSManager.getInstance(context).removeLocationListener();
                    sending = false;
                }
            }
        });
    }

    // 내위치 메시지 보내기
    private void sendAddressMessage(Context context) {
        String phone = Preference.get(context).getPreference(Constants.PREF_RECEIVER , "");
        String phone2 = Preference.get(context).getPreference(Constants.PREF_RECEIVER_SUB , "");

        SendMessage sendMessage = new SendMessage();
        GPSManager gpsManager = GPSManager.getInstance(context);
//        String url = "https://m.map.naver.com/search2/search.nhn?query=" + gpsManager.getAddress() + "&sm=hty&style=v5#/map";

        String query = null;
        try {
            query = URLEncoder.encode(gpsManager.getAddress(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://www.google.co.kr/maps/place/" + gpsManager.getAddress().replace(" ", "+");
        sendMessage.sendMMStext(context, phone, "주소 : " + gpsManager.getAddress() + "\n구글 지도 : " + url);
        sendMessage.sendMMStext(context, phone2, "주소 : " + gpsManager.getAddress() + "\n구글 지도 : " + url);
    }

    // 스크린 카운트 UP
    private int screenCountPlus(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        int screenCount = prefs.getInt(SCREEN_COUNT, 0);
        screenCount += 1;
        editor.putInt(SCREEN_COUNT, screenCount);
        editor.apply();

        Log.e("DEBUG", "screenCount : " + screenCount);

        return screenCount;
    }

    // 스크린 카운트 리셋
    private void screenCountReset(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SCREEN_COUNT, 0);
        editor.apply();
    }

    // 스크린 카운트 타이머
    private void screenTimerStart(final Context context) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep (1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                screenCountReset(context);
            }
        }.start();

    }

    // 화면 ON
    private void wakeUpScreen(Context context) {
        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);

        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "WAKELOCK");
        wakeLock.acquire();
    }

}

