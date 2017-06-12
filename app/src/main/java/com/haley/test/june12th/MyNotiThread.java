package com.haley.test.june12th;

import android.os.Handler;
import android.util.Log;

/**
 * Created by 202-18 on 2017-06-12.
 */

// 서비스 클래스에서 호출되는 스레드 클래스
// 스레드 클래스에서는 Handler 클래스를 실행
// 서비스 클래스에서 호출 -> 스레드 클래스 실행 -> Handelr 클래스 실행

/*
    1. 서비스 클래스에서 스레드 객체 생성 & start() 함수 실행

    2. 스레드 클래스에서 run() 함수에서 Handler 클래스 실행 -> sendEmptyMessage() 함수 실행

*/

public class MyNotiThread extends Thread {

    // Handler 변수를 선언해서 서비스 클래스에 있는 MyHandler 클래스의 참조 변수 보관
    private Handler mHandler;

    // 핸들러의 실행 여부를 변수로 제어
    // true : 실행 중
    // false : 실행 X
    private boolean mIsHandlerRun = true;

    // 생성자 만들기 : 서비스 클래스에서 핸들러 객체 주소를 받는 생성자 함수
    public MyNotiThread(Handler handlerValue) {

        Log.v("**MyNotiThread**", "public MyNotiThread 생성자 실행");

        this.mHandler = handlerValue;
    }


    // run() 함수 재정의
    @Override
    public void run() {
        // super.run();

        Log.v("**MyNotiThread**", "run() 시작");

        // mIsHandlerRun 값이 참일 동안 계속해서 반복되는 무한 반복문 작성
        while(mIsHandlerRun == true) {
            // sendEmtpyMessage() 함수를 실행해서 화면에 노티피케이션 출력
            Log.v("**MyNotiThread**", "while(mIsHandlerRun == true)");
            mHandler.sendEmptyMessage(0);

            // sleep() 함수를 실행해서 잠깐 현재 스레드 실행 멈추기
            try {
                Thread.sleep(2000); // 2초 동안 쿨쿨
                Log.v("**MyNotiThread**", "2초동안 스레드 sleep");
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        } // end of while()

        Log.v("**MyNotiThread**", "while() 반복문 벗어남");

        // while 반복문을 벗어난 위치에는 노티피케이션을 삭제하기 위해
        // sendEmptyMessage로 1 값을 보냄
        mHandler.sendEmptyMessage(1);

    } // end of run()


    // mIsHandlerRun 변수의 값을 false로 바꿔주는 함수 만들기
    public void stopNoti() {

        // 동기화 블럭을 사용해서 하나의 스레드만이 mIsHandlerRun 변수를 사용할 수 있게 함
        // 서비스 클래스에서 onDestroy() 함수에서 실행
        synchronized(this) {

            mIsHandlerRun = false;

        }
    }
} // end of public class MyNotiThread extends Thread
