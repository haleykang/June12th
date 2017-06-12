package com.haley.test.june12th;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private Intent intent;

    // Intent intent = new Intent(전달 할 클래스, 전달 받을 클래스);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.show_noti:
                intent = new Intent(this, ShowNotify.class);
                // this가 안될 경우 MainActivity.this
                startActivity(intent);
                break;


        }

    }
}
