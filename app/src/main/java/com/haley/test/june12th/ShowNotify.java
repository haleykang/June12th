package com.haley.test.june12th;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShowNotify extends Activity {

    // 전역 함수
    private Button showNotiStartBt;
    private Button showNotiStopBt;

    // 재정의 함수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        myLog("onCreat() 시작");

       /* this.showNotiStartBt = (Button)this.findViewById(R.id.show_noti_start_bt);
        this.showNotiStopBt = (Button)this.findViewById(R.id.stop_service_bt);

        // 버튼 클릭 이벤트(뭐 하나면 이렇게 해도 될 듯)
        this.showNotiStartBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLog("알림창 출력 버튼 클릭");

                // startService() 함수 실행
                // 1. intent 객체 생성
                Intent intent = new Intent(ShowNotify.this, MyNotiService.class);
                myLog("new Intent() in ShowNotify Class");
                // 2. intent 객체를 startService() 함수에 전달
                startService(intent);

                // ShowNotify 클래스에서 MyNotiService 클래스로 이동함
                // MyNotiService 클래스의 기본 생성자 -> onCreate() 함수 실행 ->
                // onStartCommand() 함수 순서로 실행됨


            }
        }); // 이걸 -> onClick으로 간단하게 변경하자!! 나중에

        this.showNotiStopBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLog("알림창 닫기 버튼 클릭");

                // stopService 함수 실행
                Intent intent = new Intent(ShowNotify.this, MyNotiService.class);
                stopService(intent);
            }
        });*/



    } // end of onCreate()


    // 사용자 정의 함수
    public void onClick(View v) {

        Intent intent = new Intent(ShowNotify.this, MyNotiService.class);

        switch(v.getId()) {
            case R.id.show_noti_start_bt :
                startService(intent);
                break;
            case R.id.stop_service_bt :
                stopService(intent);
                break;
        }
    }



    private void myLog(String ob) {
        Log.v("**ShowNotify**", ob);
    }

} // end of public class ShowNotify