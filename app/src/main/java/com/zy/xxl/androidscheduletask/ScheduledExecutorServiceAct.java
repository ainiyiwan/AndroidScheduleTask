package com.zy.xxl.androidscheduletask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduledExecutorServiceAct extends AppCompatActivity {

    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.btn_next)
    Button btnNext;

    @SuppressLint("HandlerLeak")
    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ScheduledExecutorServiceAct.this.time.setText(getTime());
                    break;
                default:
                    break;

            }
        }
    };
    private ScheduledExecutorService scheduleTaskExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_executor_service);
        ButterKnife.bind(this);

        scheduleTaskExecutor =
                new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                    private AtomicInteger atoInteger = new AtomicInteger(0);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("xxx-Thread "+ atoInteger.getAndIncrement());
                        return t;
                    }
                });

        //Schedule a task to run every 5 seconds (or however long you want)
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Do stuff here!
                Message message = new Message();
                message.what = 1;
                uiHandler.sendMessage(message);

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
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
