package com.mirine.sos2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class ActualPayment extends AppCompatActivity {
    private int stuck = 999;

    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_payment);
        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
        BootpayAnalytics.init(this, "5e0417c30627a80026682a35");

    }



    public void onClick_request(View v) {
        // 결제호출
        BootUser bootUser = new BootUser().setPhone("010-1234-5678");
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

        Bootpay.init(getFragmentManager())
                .setApplicationId("5e0417c30627a80026682a35") // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.KAKAO) // 결제할 PG 사
                .setMethod(Method.EASY) // 결제수단
                .setContext(this)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                .setName("유료버전") // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호expire_month
                .setPrice(1100) // 결제할 금액
                .addItem("유료버전 - 부가세포함", 1, "ITEM_CODE_pay", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                //.addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {

                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d("confirm", message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        Log.d("done", message);
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d("ready", message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d("cancel", message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("error", message);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String message) {
                                Log.d("close", "close");
                            }
                        })
                .request();


    }

    //버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "미리내 문화그룹 환불 정책\n" +
                "\n" +
                "환불 정책은 회사에 직접 이메일문의 혹은 전화상담을 통해 가능하며,\n" +
                "구체적인 정책은 아래와 같습니다.\n" +
                "\n" +
                "전화 : 82-031-241-1238 , 이메일 (mirinegroup@naver.com)\n" +
                "평일 오전 9시 ~ 오후 6시 까지 상담가능. \n(주말, 공휴일 불가)\n" +
                "\n" +
                "\n" +
                "환불 정책\n" +
                "\n" +
                "어플리케이션 결제 시간을 기준으로 하며,\n" +
                "휴대폰 오작동, 어플리케이션 동기화 문제를 제외하고는\n" +
                "이틀이내 바로 환불이 가능합니다.\n" +
                "\n" +
                "48시간 이내: 이메일, 전화를 통해 구매내역을 증명해 주시면 바로 환불이 가능합니다. \n(100% 환불 가능합니다)\n" +
                "48시간 이후: 1회 결제분에 대해 환불은 어려우며, 어플리케이션 오류 혹은 휴대폰의 문제로 인한 환불은 가능합니다.\n" +
                "(이 역시 메일등을 통해 증명이 가능할시 환불이 가능합니다.)\n" +
                "\n" +
                "환불 소요 기간\n" +
                "\n" +
                "환불액은 원래 구매에 사용된 결제 수단으로 환불됩니다. 환불에 소요되는 기간은 결제 수단에 따라 다릅니다. ");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
    }
    public void homeAP(View view) {
        //데이터 전달하기
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }

    public void homeAP1(View view) {
        //데이터 전달하기
        Intent intent = new Intent(
                getApplicationContext(), // 현재 화면의 제어권자
                MainActivity.class); // 다음 넘어갈 클래스 지정
        startActivity(intent); // 다음 화면으로 넘어간다
    }





}

