package com.mirine.sos2020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mirine.sos2020.R;


public class ActionBarCustomView {

    private RelativeLayout white_actionbar;
    private RelativeLayout color_actionbar;
    private RelativeLayout color_actionbar_1;
    private ImageView navi_icon;
    private ImageView sample_img;
    private ImageView black_back_btn;
    private ImageView white_back_btn;
    private TextView title_text;
    private TextView title_color_text;
    private TextView title_color_text1;


    public ActionBarCustomView(AppCompatActivity activity, View.OnClickListener onClickListener) {
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater mInflater = LayoutInflater.from(activity);

        View customView = mInflater.inflate(R.layout.actionbar, null);

        white_actionbar = customView.findViewById(R.id.white_actionbar);
        color_actionbar = customView.findViewById(R.id.color_actionbar);
        color_actionbar_1 = customView.findViewById(R.id.color_actionbar_1);
        navi_icon = customView.findViewById(R.id.navi_icon);
        sample_img = customView.findViewById(R.id.sample_img);
        black_back_btn = customView.findViewById(R.id.black_back_btn);
        white_back_btn = customView.findViewById(R.id.white_back_btn);
        title_text = customView.findViewById(R.id.title_text);
        title_color_text = customView.findViewById(R.id.title_color_text);
        title_color_text1 = customView.findViewById(R.id.title_color_text1);



        if (onClickListener != null) {
            navi_icon.setOnClickListener(onClickListener);
            sample_img.setOnClickListener(onClickListener);
            black_back_btn.setOnClickListener(onClickListener);
            white_back_btn.setOnClickListener(onClickListener);
        }

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(customView, layoutParams);
    }

    public void setAction(int nType , String title) {
        switch (nType) {
            case 0: {
                white_actionbar.setVisibility(View.VISIBLE);
                color_actionbar.setVisibility(View.GONE);
                color_actionbar_1.setVisibility(View.GONE);

                title_text.setVisibility(View.VISIBLE);
                title_text.setText(title);
                break;
            }
            case 1: {
                white_actionbar.setVisibility(View.GONE);
                color_actionbar.setVisibility(View.VISIBLE);
                color_actionbar_1.setVisibility(View.GONE);

                title_color_text.setVisibility(View.VISIBLE);
                title_color_text.setText(title);
                break;
            }
            case 2: {
                white_actionbar.setVisibility(View.GONE);
                color_actionbar.setVisibility(View.GONE);
                color_actionbar_1.setVisibility(View.VISIBLE);

                title_color_text1.setVisibility(View.VISIBLE);
                title_color_text1.setText(title);
                break;
            }
        }

    }
}
