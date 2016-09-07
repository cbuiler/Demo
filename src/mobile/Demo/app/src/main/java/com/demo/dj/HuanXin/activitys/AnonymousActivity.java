package com.demo.dj.HuanXin.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.models.IMeterReader;
import com.demo.dj.HuanXin.models.MeterReader;
import com.demo.dj.demo.R;

import org.json.JSONException;

/**
 * Created by guozhaohui on 2016/8/21.
 */
public class AnonymousActivity extends FragmentActivity implements IMeterReader {

    final String SYNC_SUCCESS = "HUANXIN_SYNC_SUCCESS";
    final String SYNC_ERROR = "HUANXIN_SYNC_ERROR";

    private MeterReader mMeterReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous);

        mMeterReader = ((AppManager)getApplication()).getMeterReader();
        mMeterReader.registerListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(SYNC_SUCCESS);
        filter.addAction(SYNC_ERROR);
        registerReceiver(mReceiver, filter);
    }

    public void onAuthorize(View view) {
        //启动与PC连接获取授权的服务
        try {
            mMeterReader.synchronize();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (SYNC_SUCCESS.equalsIgnoreCase(action)) {
                //Todo:授权信息同步成功

            }
            else if (SYNC_ERROR.equalsIgnoreCase(action)) {
                //Todo:授权信息同步失败
                Toast.makeText(context.getApplicationContext(),
                        "授权信息同步失败！", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onAuthorized(String readerId) {
        Intent intent = new Intent();
        intent.setClass(this.getApplicationContext(),
                LoginActivity.class);
        intent.putExtra("readerId", readerId);
        startActivity(intent);
    }
}
