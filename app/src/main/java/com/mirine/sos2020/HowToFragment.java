package com.mirine.sos2020;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HowToFragment extends Fragment {

    private TextView html_text1;
    private TextView html_text2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_howto, null);

        html_text1 = view.findViewById(R.id.html_text1);
        html_text2 = view.findViewById(R.id.html_text2);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        html_text1.setText(Html.fromHtml(getString(R.string.howto_text1)));
        html_text2.setText(Html.fromHtml(getString(R.string.howto_text2)));
    }
}
