package com.mirine.sos2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.mirine.sos2020.lib.Preference;

public class FragmentTest extends Fragment {

    private TextView name_text;
    private TextView email_text;
    private TextView photo_text;

    private ImageView photo_img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, null);

        name_text = view.findViewById(R.id.name_text);
        email_text = view.findViewById(R.id.email_text);
        photo_text = view.findViewById(R.id.photo_text);
        photo_img = view.findViewById(R.id.photo_img);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        name_text.setText(Preference.get(getActivity()).getPreference("name" , ""));
        email_text.setText(Preference.get(getActivity()).getPreference("email" , ""));
        photo_text.setText(Preference.get(getActivity()).getPreference("photourl" , ""));

        Glide.with(this)
                .load(Preference.get(getActivity()).getPreference("photourl" , ""))
                .into(photo_img);
    }
}
