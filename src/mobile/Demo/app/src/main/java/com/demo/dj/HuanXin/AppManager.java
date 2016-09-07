package com.demo.dj.HuanXin;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.models.DBManager;
import com.demo.dj.HuanXin.models.MeterReader;
import com.demo.dj.HuanXin.protocols.IUSBClient;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;
import com.demo.dj.HuanXin.tools.usb.SyncConnectService;

import org.json.JSONException;

/**
 * Created by guozhaohui on 2016/8/21.
 */
public class AppManager extends Application implements IUSBClient {

    private MeterReader meterReader;
    private DBManager dbManager;
    private USBProtocol usbProtocol;

    private static AppManager instance;
    private Handler serviceHandler;

    public static AppManager getInstance() {
        return instance;
    }

    public MeterReader getMeterReader(){
        return meterReader;
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public USBProtocol getUsbProtocol() { return usbProtocol; }

    public Boolean isUsbReady() { return usbProtocol.isUsbReady(); }

    public Handler getServiceHandler() {
        return serviceHandler;
    }

    public void setServiceHandler(Handler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dbManager = new DBManager(getApplicationContext());
        usbProtocol = new USBProtocol(getApplicationContext());
        usbProtocol.registerClientListener(this);

        meterReader = new MeterReader();
    }

    public void synchronize(IUSBProtocol listener) throws JSONException {
        usbProtocol.synchronize(listener);
    }

    @Override
    public void onUSBClientStart() {
        Toast.makeText(this, "PC客户端启动", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUSBClientStop() {
        Toast.makeText(this, "PC客户端停止", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUSBClientReady() {
        Toast.makeText(this, "PC客户端已连接", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUSBClientEnd() {
        Toast.makeText(this, "PC客户端结束", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUSBClientError(String errMsg) {
        Toast.makeText(this, "客户端错误: " + errMsg, Toast.LENGTH_LONG).show();
    }
}
