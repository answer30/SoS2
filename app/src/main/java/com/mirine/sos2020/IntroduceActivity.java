package com.mirine.sos2020;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ImageButton imagebutton = (ImageButton) findViewById(R.id.callme);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0312411238"));

                startActivity(intent);
            }
        });
    }

    //  사용법
    public void ingomhme (View view) {
        Intent intent = new Intent(
                getApplicationContext(),
                LoginActivity.class);
        startActivity(intent);

    }
}
