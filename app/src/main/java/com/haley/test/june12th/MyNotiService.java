package com.haley.test.june12th;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
// 핸들러 클래스 사용
import android.os.Handler;
// handleMessage() 함수에서 사용할 Message
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import android.util.Log;

public class MyNotiService extends Service {

    // 전역 함수
    private NotificationManager mNotificationManager;

    private NotificationCompat.Builder mNotificationCompatBuilder;

    private MyNotiThread mMyNotiThread;


    // 핸들러 클래스 만들기
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // sendEmptyMessage() 함수와 자동 연결되는 함수
            // sendEmptyMessage(정수 값) -> handleMessage() 함수 실행
            // 정수 값은 handleMessage() 함수의 매개 변수 인 Message msg의 what 정수 변수가 받음
            // 예) if(Message 참조 변수이름.what == 0)

            /*
                sendEmptyMessage() 함수로 부터 받는 값에 따라서 처리할 명령문들 실행
             */
            if(msg.what == 0) {
                // 개발자가 정함 -> 화면에 notification을 출력하는 명령문 들을 작성
                myLog("if(msg.what == 0)");

                // 1. 인텐트 객체 생성 / 임시 변수 저장
                Intent intent = new Intent(MyNotiService.this, ShowNotify.class);
                // MyNotiService 클래스에서 -> ShowNotify 클래스로 흐름

                // 2. PendingIntent 객체 생성 : getActivity() 함수 사용
                PendingIntent pendingIntent = PendingIntent.getActivity(MyNotiService.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                // MyNotiService.this : 현재 서비스 클래스의 컨텍스트
                // 0 : 요청 코드
                // intent : 위에서 만든 intent 객체
                // PendingIntent.FLAG_UPDATE_CURRENT : 플래그 정수 값
                //      -> putExtra() 함수를 사용하는 경우에 메모리에 Intent 객체가 있는 경우
                //      Intent객체를 재사용 하고 putExtra() 함수에서 사용한 데이터를 갱신


                // 3. 빌더 객체 생성
                mNotificationCompatBuilder = new NotificationCompat.Builder(MyNotiService.this);

                // 화면에 출력될 Notification 타이틀 정하기
                mNotificationCompatBuilder.setContentTitle("타이틀 : 고구마밭");
                mNotificationCompatBuilder.setContentText("내용 : 나는 고구마");
                mNotificationCompatBuilder.setSmallIcon(R.mipmap.ic_launcher);
                // 티커 텍스트 : 상태바에 노티피케이션이 추가된 후에 바로 출력되는 텍스트
                mNotificationCompatBuilder.setTicker("티커 텍스트 : 고구마가 좋아");
                // 위에서 만든 PendingIntent객체 사용
                mNotificationCompatBuilder.setContentIntent(pendingIntent);
                // 진동,빛(LED),소리 알람 선택
                mNotificationCompatBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
                // DEFAULT_VIBRATE : 진동
                // DEFAULT_LIGHTS : 빛
                // DEFAULT_SOUND : 소리
                // DEFAULT_ALL : 전부

                // setAutoCancel() : 사용자가 선택하면 화면에서 사라지기
                mNotificationCompatBuilder.setAutoCancel(true);

                // build() : 메모리에 생성
                Notification notification = mNotificationCompatBuilder.build();

                // notify() : 화면에 출력
                mNotificationManager.notify(1, notification);
                // 1: 아이디

                myLog("notify()");


            } else if(msg.what == 1) {
                // 화면에 출력된 Notification을 삭제하는 명령문들을 작성
                myLog("else if(msg.what == 1)");

                // cancel() 함수 실행 : 강제로 노티피케이션을 화면에서 삭제
                mNotificationManager.cancel(1);
                // 1 : 아이디 -> notify() 함수 실행 했을 때의 정수 값(아이디)와 일치해야함

                myLog("cancel(1)");
            }

        } // end of handleMessage(Message msg)
    } // end of   private class MyHandler extends Handler

    // 기본 생성자 함수
    public MyNotiService() {


    }

    // startService() 함수에서는 onBind() 함수 사용 안함
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //  throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    // onStartCommand() 함수 재정의 : startService() 함수에 의해서 자동으로 호출되는 함수
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        myLog("onStartCommand() 시작");

        // 1. Notification 서비스에 접근
        mNotificationManager =
                (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // 2. 핸들러 객체 생성
        MyHandler mMyHandler = new MyHandler();

        // 3. 핸들러 객체를 스레드 클래스에 전달
        mMyNotiThread = new MyNotiThread(mMyHandler);

        // 4. 스레드 객체를 실행 가능한 상태로 바꾸기
        mMyNotiThread.start();


        myLog("onStartCommand() 종료");
        // 5. 함수의 반환 값 : 서비스 클래스가 갖고 있는 START_STICKY
        return Service.START_STICKY;
        // 서비스가 강제 종료 됐을 때, 다시 서비스를 재시작해주는 플래그
    }

    // onDestroy() : 가장 마지막에 종료되는 함수 재정의
    @Override
    public void onDestroy() {
        // super.onDestroy();
        myLog("onDestroy() 시작");

        // 스레드 클래스에서 만든 stopNoti() 함수 실행해서 노티피케이션 삭제
        mMyNotiThread.stopNoti();
        // 가비지 컬렉터에 의해서 메모리에서 삭제되는 객체 지정
        mMyNotiThread = null;
        // mMyNotiThread 변수로 접근할 수 있었던 메모리 공간 삭제
    }

    private void myLog(String ob) {
        Log.v("**MyNotiService**", ob);
    }
}
