package com.mirine.sos2020;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mirine.sos2020.R;
import com.mirine.sos2020.lib.Preference;

public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout 		= null;
    private View mFragmentContainerView 	= null;
    private ImageView close_img = null;

    private LinearLayout linear_1;
    private LinearLayout linear_2;
    private LinearLayout linear_3;
    private LinearLayout linear_4;
    private LinearLayout linear_5;
    private LinearLayout linear_6;

    private ImageView img_1;
    private ImageView img_2;
    private ImageView img_3;
    private ImageView img_4;
    private ImageView img_5;
    private ImageView img_6;


    private ImageView photo_img;
    private TextView name_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.navigation_drawer, container, false);




        photo_img = v.findViewById(R.id.photo_img);
        name_text = v.findViewById(R.id.name_text);




        close_img = v.findViewById(R.id.close_img);
        linear_1 = v.findViewById(R.id.linear_1);
        linear_2 = v.findViewById(R.id.linear_2);
        linear_3 = v.findViewById(R.id.linear_3);
        linear_4 = v.findViewById(R.id.linear_4);
        linear_5 = v.findViewById(R.id.linear_5);
        linear_6 = v.findViewById(R.id.linear_6);
        img_1 = v.findViewById(R.id.img_1);
        img_2 = v.findViewById(R.id.img_2);
        img_3 = v.findViewById(R.id.img_3);
        img_4 = v.findViewById(R.id.img_4);
        img_5 = v.findViewById(R.id.img_5);
        img_6 = v.findViewById(R.id.img_6);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        close_img.setOnClickListener(this);
        linear_1.setOnClickListener(this);
        linear_2.setOnClickListener(this);
        linear_3.setOnClickListener(this);
        linear_4.setOnClickListener(this);
        linear_5.setOnClickListener(this);
        linear_6.setOnClickListener(this);

        name_text.setText(Preference.get(getActivity()).getPreference("name" , ""));




        Glide.with(this)
                .load(Preference.get(getActivity()).getPreference("photourl" , ""))
                .into(photo_img);
    }





    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_img: {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
                break;
            }
            case R.id.linear_1: {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
                setType(1);
                break;
            }
            case R.id.linear_2: {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
                setType(2);
                break;
            }
            case R.id.linear_3: {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
                setType(3);
                break;
            }
            case R.id.linear_4: {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
                setType(4);
                break;
            }
            case R.id.linear_5: {

                setType(5);
                break;
            }






        }
    }

    public void setType(int type) {
        switch (type) {
            case 1: {
                linear_1.setBackgroundColor(Color.parseColor("#ff0000"));
                linear_2.setBackgroundColor(0x00000000);
                linear_3.setBackgroundColor(0x00000000);
                linear_4.setBackgroundColor(0x00000000);
                linear_5.setBackgroundColor(0x00000000);

                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.GONE);
                img_4.setVisibility(View.GONE);
                img_5.setVisibility(View.GONE);

                ((MainActivity)getActivity()).replaceFragment( new FragmentTest(), "회원정보");
                break;
            }
            case 2: {
                linear_2.setBackgroundColor(Color.parseColor("#ff0000"));
                linear_1.setBackgroundColor(0x00000000);
                linear_3.setBackgroundColor(0x00000000);
                linear_4.setBackgroundColor(0x00000000);
                linear_5.setBackgroundColor(0x00000000);

                img_1.setVisibility(View.GONE);
                img_2.setVisibility(View.VISIBLE);
                img_3.setVisibility(View.GONE);
                img_4.setVisibility(View.GONE);
                img_5.setVisibility(View.GONE);



                Intent intent = new Intent(getActivity() , ActualPayment.class);
                startActivity(intent);
                break;
            }
            case 3: {
                linear_3.setBackgroundColor(Color.parseColor("#ff0000"));
                linear_1.setBackgroundColor(0x00000000);
                linear_2.setBackgroundColor(0x00000000);
                linear_4.setBackgroundColor(0x00000000);
                linear_5.setBackgroundColor(0x00000000);

                img_1.setVisibility(View.GONE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.VISIBLE);
                img_4.setVisibility(View.GONE);
                img_5.setVisibility(View.GONE);

                Intent intent = new Intent(getActivity() , TestActivity.class);
                startActivity(intent);
                break;
            }
            case 4: {
                linear_4.setBackgroundColor(Color.parseColor("#ff0000"));
                linear_1.setBackgroundColor(0x00000000);
                linear_2.setBackgroundColor(0x00000000);
                linear_3.setBackgroundColor(0x00000000);
                linear_5.setBackgroundColor(0x00000000);

                img_1.setVisibility(View.GONE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.GONE);
                img_4.setVisibility(View.VISIBLE);
                img_5.setVisibility(View.GONE);
                break;
            }

            case 5: {
                linear_5.setBackgroundColor(0x00000000);
                linear_1.setBackgroundColor(0x00000000);
                linear_2.setBackgroundColor(0x00000000);
                linear_3.setBackgroundColor(0x00000000);
                linear_4.setBackgroundColor(0x00000000);


                img_1.setVisibility(View.GONE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.GONE);
                img_4.setVisibility(View.GONE);
                img_5.setVisibility(View.GONE);


            }
            case 6: {
                linear_5.setBackgroundColor(Color.parseColor("#ff0000"));
                linear_1.setBackgroundColor(0x00000000);
                linear_2.setBackgroundColor(0x00000000);
                linear_3.setBackgroundColor(0x00000000);
                linear_4.setBackgroundColor(0x00000000);


                img_1.setVisibility(View.GONE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.GONE);
                img_4.setVisibility(View.GONE);
                img_5.setVisibility(View.VISIBLE);


            }









        }
    }
}