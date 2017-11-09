package com.zy.xxl.androidscheduletask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerActivity extends AppCompatActivity {

    @BindView(R.id.time)
    TextView time;

    private static final long HEART_BEAT_RATE = 1000;

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity.this.time.setText(getTime());
            uiHandler.sendEmptyMessageDelayed(0x01, HEART_BEAT_RATE);
        }
    };

//    private Runnable heartBeatRunnable = new Runnable() {
//        @Override
//        public void run() {
//            // excute task
//            uiHandler.postDelayed(this, HEART_BEAT_RATE);
//            HandlerActivity.this.time.setText(getTime());
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        uiHandler.sendEmptyMessageDelayed(0x01, HEART_BEAT_RATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 通过SimpleDateFormat获取24小时制时间
     */
    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

}
