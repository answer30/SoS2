package com.mirine.sos2020;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.mirine.sos2020.background.MainRecever;
import com.mirine.sos2020.background.PowerSwichService;
import com.mirine.sos2020.common.Constants;

  /*

public class KakaopayActivity extends AppCompatActivity implements View.OnClickListener {

  /*  private Intent serviceIntent;
    private EditText et_receiver;
    private EditText et_receiverSub;
  *//*  private EditText et_receiverSub1;
    private EditText et_receiverSub2;
    private EditText et_receiverSub3;
*//*


    private ImageButton buttonLogout;
    private FirebaseAuth firebaseAuth;;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaopay);




        setService();
        viewInit();
        requestPermissionCheck();
        noisePermissionCheck();
        setBattryOptimizations();


        buttonLogout = (ImageButton) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }










    //  사용법
    public void onClick_buttonkakao1 (View view) {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                ActualPayment.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다

    }




    @Override
    public void onClick(View view) {
        if (view == buttonLogout)
            firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (MainRecever.timer != 0) {
            Intent itt = new Intent(this, CountDownActivity.class);
            itt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(itt);
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    private void setService() {
        if (PowerSwichService.serviceIntent == null) {
            serviceIntent = new Intent(this, PowerSwichService.class);
            startService(serviceIntent);
        } else {
            serviceIntent = PowerSwichService.serviceIntent;//getInstance().getApplication();
            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_LONG).show();
        }
    }

    private void viewInit() {
        getWindow().setStatusBarColor(Color.parseColor("#ed1b24"));

        et_receiver = (EditText) findViewById(R.id.activity_main_et_receiver);
        et_receiverSub = (EditText) findViewById(R.id.activity_main_et_receiverSub);
      *//*  et_receiverSub1 = (EditText) findViewById(R.id.activity_main_et_receiverSub1);
        et_receiverSub2 = (EditText) findViewById(R.id.activity_main_et_receiverSub2);
        et_receiverSub3= (EditText) findViewById(R.id.activity_main_et_receiverSub3);*//*









        et_receiver.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_RECEIVER, ""));
        et_receiverSub.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_RECEIVER_SUB, ""));
      *//*  et_receiverSub1.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_RECEIVER_SUB1, ""));
        et_receiverSub2.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_RECEIVER_SUB2, ""));
        et_receiverSub3.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_RECEIVER_SUB3, ""));
*//*
    }

    private void showBattryOptimizationsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("배터리 사용량 최적화");
        builder.setMessage("배터리 사용량 최적화 앱 목록에 SOS앱이 포함되어 있으면 SOS기능이 동작하지 않을 수 있습니다. 배터리 사용량 최적화 앱에서 SOS앱을 제외 하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    //배터리 절전
    private void setBattryOptimizations() {
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }
        if (!isWhiteListing) {
            showBattryOptimizationsDialog();
        }
    }

    private void showNoisePermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("방해 금지 권한이 없습니다.");
        builder.setMessage("방해 금지 권한이 없어 위급사항 발생시 무음모드로 전환이 되지 않을 수 있습니다. 설정에서 무음 모드 권한을 설정하시겠습니까?\n(방해 금지 권한 허용 -> SOS 버튼 ON)");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void noisePermissionCheck() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            showNoisePermissionDialog();

        }
    }

    private void requestPermissionCheck() {

        boolean permission = true;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permission = false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permission = false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            permission = false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            permission = false;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permission = false;
        }

        if (!permission) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.RECEIVE_MMS,
                    Manifest.permission.VIBRATE,
                    //Manifest.permission.SMS_FINANCIAL_TRANSACTIONS,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);

        }

    }

    public void receiverSave(View v) {
        if (et_receiver.getText().toString().length() > 1) {
            if (et_receiver.getText().toString().length() < 8) {
                Toast.makeText(this, "첫번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (et_receiverSub.getText().toString().length() > 1) {
            if (et_receiverSub.getText().toString().length() < 8) {
                Toast.makeText(this, "두번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
*//*       if (et_receiverSub1.getText().toString().length() > 1) {
            if (et_receiverSub1.getText().toString().length() < 8) {
                Toast.makeText(this, "세번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
      if (et_receiverSub2.getText().toString().length() > 1) {
            if (et_receiverSub2.getText().toString().length() < 8) {
                Toast.makeText(this, "네번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (et_receiverSub3.getText().toString().length() > 1) {
            if (et_receiverSub3.getText().toString().length() < 8) {
                Toast.makeText(this, "다섯번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }*//*


        String receiver = et_receiver.getText().toString();
        String receiver_sub = et_receiverSub.getText().toString();
       *//* String receiver_sub1 = et_receiverSub1.getText().toString();
        String receiver_sub2 = et_receiverSub2.getText().toString();
        String receiver_sub3 = et_receiverSub3.getText().toString();*//*

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREF_RECEIVER, receiver);
        editor.putString(Constants.PREF_RECEIVER_SUB, receiver_sub);
      *//*  editor.putString(Constants.PREF_RECEIVER_SUB1, receiver_sub1);
        editor.putString(Constants.PREF_RECEIVER_SUB2, receiver_sub2);
        editor.putString(Constants.PREF_RECEIVER_SUB3, receiver_sub3);*//*

        editor.apply();

        Toast.makeText(this, "수신인이 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void homekkos(View view) {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                LoginActivity2.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다

    }


}




*/