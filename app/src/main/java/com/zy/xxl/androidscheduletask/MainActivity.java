package com.zy.xxl.androidscheduletask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.zy.xxl.androidscheduletask.AlarmManager.AlarmActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_timer)
    Button btnTimer;
    @BindView(R.id.btn_Scheduled)
    Button btnScheduled;
    @BindView(R.id.btn_handler)
    Button btnHandler;
    @BindView(R.id.btn_alarm)
    Button btnAlarm;

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("handleMessage thread id " + Thread.currentThread().getId());
                    System.out.println("msg.arg1:" + msg.arg1);
                    System.out.println("msg.arg2:" + msg.arg2);
                    MainActivity.this.time.setText(getTime());
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        DownloadThread downloadThread = new DownloadThread();
        downloadThread.start();
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

    @OnClick(R.id.btn_timer)
    public void onTimerClicked() {
        startActivity(new Intent(MainActivity.this, TimerActivity.class));
    }

    @OnClick(R.id.btn_Scheduled)
    public void onScheduledClicked() {
        startActivity(new Intent(MainActivity.this, ScheduledExecutorServiceAct.class));
    }

    @OnClick(R.id.btn_handler)
    public void onHandlerClicked() {
        startActivity(new Intent(MainActivity.this, HandlerActivity.class));
    }

    @OnClick(R.id.btn_alarm)
    public void onAlarmClicked() {
        startActivity(new Intent(MainActivity.this, AlarmActivity.class));
    }


    class DownloadThread extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("DownloadThread id " + Thread.currentThread().getId());
                System.out.println("开始下载文件");
                Thread.sleep(4000);
                System.out.println("文件下载完成");
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 123;
                msg.arg2 = 321;
                uiHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
