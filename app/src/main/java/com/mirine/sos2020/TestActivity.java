package com.mirine.sos2020;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mirine.sos2020.lib.Preference;

public class TestActivity extends AppCompatActivity {


    private TextView name_text;
    private TextView email_text;
    private TextView photo_text;
    private ImageView photo_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_text);

        name_text = findViewById(R.id.name_text);
        email_text = findViewById(R.id.email_text);

        photo_img = findViewById(R.id.photo_img);

        name_text.setText(Preference.get(this).getPreference("name" , ""));
        email_text.setText(Preference.get(this).getPreference("email" , ""));
        photo_text.setText(Preference.get(this).getPreference("photourl" , ""));

        Glide.with(this)
                .load(Preference.get(this).getPreference("photourl" , ""))
                .into(photo_img);
    }
}
