package com.zy.xxl.androidscheduletask;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.wenming.library.LogReport;
import com.wenming.library.save.imp.CrashWriter;

/**
 * Author ： zhangyang
 * Date   ： 2017/11/8
 * Email  :  18610942105@163.com
 * Description  :
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initCrashReport();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initCrashReport() {
        LogReport.getInstance()
                .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(getApplicationContext());
    }

}
