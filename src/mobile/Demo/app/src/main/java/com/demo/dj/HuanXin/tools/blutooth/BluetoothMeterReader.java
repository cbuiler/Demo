package com.demo.dj.HuanXin.tools.blutooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by guozhaohui on 2016/8/11.
 */
public class BluetoothMeterReader {
    private final String METER_NAME = "HC-05";
    private final String METER_PASSWORD = "2222";

    private static BluetoothMeterReader mInstance = new BluetoothMeterReader();

    private BluetoothAdapter mBA;
    BluetoothDevice mMeterHunXin = null;
    private Context mContext;

    private IMeterReaderListener mListener = null;

    public BluetoothMeterReader() {}

    public static BluetoothMeterReader getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;

        mBA = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }

    public void destroy() {
        mContext.unregisterReceiver(mReceiver);
    }

    public void registerListener(IMeterReaderListener ls) {
        mListener = ls;
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //启动查找
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                if (mListener != null)
                    mListener.onMessage("开始查找蓝牙设备...");
            }
            //找到设备
            else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(
                        BluetoothDevice.EXTRA_DEVICE);

                if (mListener != null)
                    mListener.onMessage("找到蓝牙设备："
                            + device.getName() + " - " + device.getAddress());

                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (mListener != null)
                        mListener.onMessage("该设备可以匹配："
                                 + device.getName() + " - " + device.getAddress());
                    if (device.getName().equals(METER_NAME)) {
                        if (mListener != null)
                            mListener.onMessage("找到尚未匹配的环欣设备");

                        try {
                            if (ClsUtils.pair(mMeterHunXin.getAddress(), METER_PASSWORD)) {
                                if (mListener != null)
                                    mListener.onMessage("设备成功配对");
                                mMeterHunXin = device;
                            }
                        } catch (Exception e) {
                            if (mListener != null)
                                mListener.onError(e.getMessage());
                        }
                    }
                }
            }
            //搜索完成
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mListener != null)
                    mListener.onMessage("蓝牙设备搜索完成！");
            }
        }
    };

    public void connect() {
        if (mBA == null) {
            if (mListener != null)
                mListener.onError("无法获取蓝牙设备列表");
            return;
        }

        if (!mBA.isEnabled()) {
            if (mListener != null)
                mListener.onMessage("蓝牙设备没有打开，将强制开启蓝牙");
            mBA.enable();
        }

        mBA.startDiscovery();
    }

    /*
    private class ClientThread extends Thread {
        public void run(){
            try{
                socket
            }
        }
    }
    */
}
