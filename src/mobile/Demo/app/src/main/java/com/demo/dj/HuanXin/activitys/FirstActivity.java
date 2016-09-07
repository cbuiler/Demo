package com.demo.dj.HuanXin.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.models.MeterReader;
import com.demo.dj.demo.R;

/**
 * Created by guozhaohui on 2016/8/8.
 */
public class FirstActivity extends FragmentActivity {

    ListView listViewDevices;
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        /*
        BluetoothMeterReader.getInstance().init(this);
        BluetoothMeterReader.getInstance().registerListener(this);
        */

    }

    @Override
    protected void onDestroy() {
        //BluetoothMeterReader.getInstance().destroy();
        //UsbHelper.getInstance().destroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private Intent destIntent;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!isFirst) return;
        isFirst = false;

        MeterReader reader = ((AppManager)getApplication()).getMeterReader();

        destIntent = new Intent();
        if (!reader.isAnonymous()) {
            //未授权则进入匿名待授权页面
            destIntent.setClass(getApplicationContext(),
                    AnonymousActivity.class);
        }
        else {
            //已授权直接进入登陆页面
            destIntent.setClass(getApplicationContext(),
                    LoginActivity.class);
        }

        //等启动界面显示后才执行初始化工作
        Handler handler = new Handler();

        Runnable updateThread = new Runnable(){
            public void run(){
                startActivity(destIntent);
                finish();
            }

        };
        handler.postDelayed(updateThread, 3000);
    }

    public void onListDevicesClicked(View view) {
        //BluetoothMeterReader.getInstance().connect();
    }
}
