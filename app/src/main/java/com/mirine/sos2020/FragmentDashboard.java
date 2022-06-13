

package com.mirine.sos2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mirine.sos2020.background.MainRecever;
import com.mirine.sos2020.background.PowerSwichService;
import com.mirine.sos2020.common.Constants;
import com.mirine.sos2020.lib.Preference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static kr.co.bootpay.Bootpay.finish;

public class FragmentDashboard extends Fragment implements View.OnClickListener {

    private EditText ed_name_1;
    private EditText ed_name_2;
    private EditText et_receiver;
    private EditText et_receiverSub;


    private Intent serviceIntent;

    private Button edit_btn_1;
    private Button edit_btn_2;
    private Button save_btn_1;
    private Button save_btn_2;

    private LinearLayout pay_linear;
    private FirebaseAuth firebaseAuth;
    private String receiver;


    @Override
    public void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);

    }

    public FragmentDashboard() {


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);


        ed_name_1 = view.findViewById(R.id.ed_name_1);
        ed_name_2 = view.findViewById(R.id.ed_name_2);
        et_receiver = view.findViewById(R.id.et_receiver);
        et_receiverSub = view.findViewById(R.id.et_receiverSub);



        edit_btn_1 = view.findViewById(R.id.edit_btn_1);
        edit_btn_2 = view.findViewById(R.id.edit_btn_2);
        save_btn_1 = view.findViewById(R.id.save_btn_1);
        save_btn_2 = view.findViewById(R.id.save_btn_2);

        pay_linear = view.findViewById(R.id.pay_linear);
        firebaseAuth = FirebaseAuth.getInstance();

        setService();

        return view;


    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String number_one = Preference.get(getActivity()).getPreference(Constants.PREF_RECEIVER , "");
        String number_two = Preference.get(getActivity()).getPreference(Constants.PREF_RECEIVER_SUB , "");
        String name_one = Preference.get(getActivity()).getPreference(Constants.PREF_RECEIVER_NAME , "");
        String name_two = Preference.get(getActivity()).getPreference(Constants.PREF_RECEIVER_NAME_1 , "");

        if(name_one != null && !name_one.isEmpty()) {
            ed_name_1.setText(name_one);
        }

        if(name_two != null && !name_two.isEmpty()) {
            ed_name_2.setText(name_two);
        }

        if(number_one != null && !number_one.isEmpty()) {
            et_receiver.setText(number_one);
            ed_name_1.setEnabled(false);
            et_receiver.setEnabled(false);
        }

        if(number_two != null && !number_two.isEmpty()) {
            et_receiverSub.setText(number_two);
            ed_name_2.setEnabled(false);
            et_receiverSub.setEnabled(false);
        }

        edit_btn_1.setOnClickListener(this);
        edit_btn_2.setOnClickListener(this);
        save_btn_1.setOnClickListener(this);
        save_btn_2.setOnClickListener(this);
        pay_linear.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (MainRecever.timer != 0) {
            Intent intent = new Intent(FragmentDashboard.this.getActivity(), CountDownActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            finish();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceIntent!= null) {
            getActivity().stopService(serviceIntent);
            serviceIntent = null;
        }
    }

    private void setService() {
        if (PowerSwichService.serviceIntent == null)
            serviceIntent = new Intent(FragmentDashboard.this.getActivity(), PowerSwichService.class);
            getActivity().startService(serviceIntent);
        }











    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_btn_1: {
                ed_name_1.setEnabled(true);
                et_receiver.setEnabled(true);

                ed_name_1.requestFocus();
                break;
            }
            case R.id.edit_btn_2: {
                ed_name_2.setEnabled(true);
                et_receiverSub.setEnabled(true);

                ed_name_2.requestFocus();
                break;
            }
            case R.id.save_btn_1: {
                if (et_receiver.getText().toString().length() > 1) {
                    if (et_receiver.getText().toString().length() < 8) {
                        Toast.makeText(getActivity(), "첫번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        if(ed_name_1.getText().toString().length() > 0) {
                            Preference.get(getActivity()).setPreference(Constants.PREF_RECEIVER_NAME , ed_name_1.getText().toString());
                        }

                        ed_name_1.setEnabled(false);
                        et_receiver.setEnabled(false);


                        Preference.get(getActivity()).setPreference(Constants.PREF_RECEIVER , et_receiver.getText().toString());
                        Toast.makeText(getActivity(), "수신인이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case R.id.save_btn_2: {
                if (et_receiverSub.getText().toString().length() > 1) {
                    if (et_receiverSub.getText().toString().length() < 8) {
                        Toast.makeText(getActivity(), "두번째 수신인 전화번호를 정확히 입력 해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        if(ed_name_2.getText().toString().length() > 0) {
                            Preference.get(getActivity()).setPreference(Constants.PREF_RECEIVER_NAME_1 , ed_name_2.getText().toString());
                        }

                        ed_name_2.setEnabled(false);
                        et_receiverSub.setEnabled(false);

                        Preference.get(getActivity()).setPreference(Constants.PREF_RECEIVER_SUB , et_receiverSub.getText().toString());
                        Toast.makeText(getActivity(), "수신인이 저장되었습니다.", Toast.LENGTH_SHORT).show();








                    }




                }
                break;
            }
            case R.id.pay_linear: {

                Intent intent = new Intent(getActivity() , ActualPayment.class);
                startActivity(intent);
                break;
            }
        }
    }
}
