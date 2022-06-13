package com.mirine.sos2020;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener {



    private Button btn_googlelogout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        FirebaseApp.initializeApp(this);


        btn_googlelogout.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();













    }



    //  1388
    public void onButton3Clicked2(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.safe182.go.kr"));
        intent.setPackage("com.android.chrome");

        startActivity(intent);
    }

    //  미리내 마술극단 홈페이지
    public void onButton4Clicked2(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mirinemagic.com/HyAdmin/list.php?bbs_id=bo01"));
        intent.setPackage("com.android.chrome");

        startActivity(intent);
    }


    //  사용법
    public void onButton1Clicked2(View view) {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                HowtouseActivity2.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다

    }



    //회사소
    public void onButton2Clicked2(View view) {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                IntroduceActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다

    }


    //지인등
    public void wldls123123(View view) {
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                LoginActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로!

    }






    public void onClick(View v) {
        if (v == btn_googlelogout)
            firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }



}

