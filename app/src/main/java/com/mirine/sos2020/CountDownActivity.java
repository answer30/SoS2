package com.mirine.sos2020;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mirine.sos2020.background.MainRecever;
import com.mirine.sos2020.common.Constants;

import androidx.annotation.Nullable;

public class CountDownActivity extends Activity {

    public final static String LOCK_ON = "lock_on";
    public static CountDownActivity instance;
    private ImageButton btn;
    private TextView tv_Count;
    private Button btn_Cancel;
    private Handler handler;
    private WindowManager.LayoutParams params;
    private float brightness;
    private ContentResolver cResolver;
    private Window window;
    private TextView tv;
    private ImageButton ib;
    private long mLastClickTime = 0;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);


        params = getWindow().getAttributes();
        instance = this;
        handler = new Handler();


        setWindowFlags();
        viewInit();


       tv = (TextView) findViewById(R.id.testview);
        ImageButton ib = (ImageButton) findViewById(R.id.sound1);
        // 버튼의 이벤트 처리 - 클릭, 롱클릭, 터치
        ib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (System.currentTimeMillis() - mLastClickTime < 0) {

                    return;

                }
             /**   mediaPlayer.stop();

                mediaPlayer.reset();

                Toast.makeText(getApplicationContext(), ".",
                        Toast.LENGTH_SHORT).show(); **/


            }


        });
        ib.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                }



                Toast.makeText(getApplicationContext(), "",
                        Toast.LENGTH_SHORT).show();


                return false;

            }
        });



            } // end of onCreate
//

            @Override
            protected void onDestroy() {
                super.onDestroy();


                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }


            @Override
            protected void onResume() {
                super.onResume();

                // 기존 밝기 저장

                // 최대 밝기로 설정
                params.screenBrightness = 0.1f;
                // 밝기 설정 적용
                getWindow().setAttributes(params);
            }


            @Override
            protected void onPause() {
                super.onPause();

                params.screenBrightness = 0f;

                getWindow().setAttributes(params);

            }


            private void setWindowFlags() {
                if (getIntent().getBooleanExtra(LOCK_ON, false)) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                }
            }

            private void viewInit() {
                tv_Count = findViewById(R.id.activity_countdown_tv_Count);
                btn_Cancel = findViewById(R.id.activity_countdown_btn_Cancel);

                btn_Cancel.setVisibility(View.GONE);
            }

            public void sendMessageCancel(View v) {
                tv_Count.setText("SOS 메시지 전송이 취소 되었습니다.");
                btn_Cancel.setVisibility(View.GONE);

                this.activityFinish();
                Intent intent = new Intent(CountDownActivity.this,
                        MainRecever.class);
                intent.setAction(Constants.RECEIVER_SEND_MESSAGE_CANCEL);
                sendBroadcast(intent);
            }

            public void setCountText(final int time) {
                if (tv_Count.getText().toString().equals("SOS 메시지 전송이 취소 되었습니다.")) {
                    return;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_Count.setText(time + "초 후 SOS 메시지가 전송 됩니다.");
                        btn_Cancel.setVisibility(View.VISIBLE);
                    }
                });
            }

            public void setSendSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_Count.setText("SOS 메시지 전송이 완료 되었습니다.");
                        btn_Cancel.setVisibility(View.GONE);
                    }
                });

                this.activityFinish();

                MainRecever.sending = false;
            }


            private void activityFinish() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        finish();
                    }
                }.start();
            }

        }

