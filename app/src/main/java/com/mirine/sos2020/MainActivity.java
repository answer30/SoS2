package com.mirine.sos2020;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout btn1 = null;
    private LinearLayout btn2 = null;
    private LinearLayout btn3 = null;


    private FirebaseAuth firebaseAuth;
    private ImageButton buttonLogout;

    private NavigationDrawerFragment mNavigationDrawerFragment = null;
    private DrawerLayout mDrawerLayout = null;
    private View mFragmentContainerView = null;
    private Toolbar mToolbar = null;
    private LinearLayout bottom_linear = null;
    private ActionBarCustomView actionBarCustomView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        actionBarCustomView = new ActionBarCustomView(this, this);
        actionBarCustomView.setAction(0, "긴급상황도우미");


        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mFragmentContainerView = findViewById(R.id.navigation_drawer);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        bottom_linear = findViewById(R.id.bottom_linear);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new FragmentHome()).commit();

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


        buttonLogout = (ImageButton) findViewById(R.id.sample_img);
        buttonLogout.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();


        requestPermissionCheck();

        setBattryOptimizations();




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

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    public void replaceFragment(Fragment fragment , String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        actionBarCustomView.setAction(1, title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1: {
                bottom_linear.setVisibility(View.GONE);
                replaceFragment(new HowToFragment());
                actionBarCustomView.setAction(2, "how to");
                break;
            }
            case R.id.white_back_btn:
            case R.id.black_back_btn:
            case R.id.btn_2: {
                bottom_linear.setVisibility(View.VISIBLE);
                replaceFragment(new FragmentHome());
                actionBarCustomView.setAction(0, "긴급상황도우미");
                break;
            }
            case R.id.btn_3: {
                bottom_linear.setVisibility(View.GONE);
                replaceFragment(new FragmentDashboard());
                actionBarCustomView.setAction(1, "사용자추가");
                break;
            }

            case R.id.navi_icon: {
                if (mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
                    mDrawerLayout.closeDrawer(mFragmentContainerView);
                } else {
                    mNavigationDrawerFragment.setType(5);
                    mDrawerLayout.openDrawer(mFragmentContainerView);
                }
                break;
            }

            case R.id.sample_img: {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            }

        }
    }
}
