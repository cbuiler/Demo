package com.demo.dj.HuanXin.tools.blutooth;

/**
 * Created by guozhaohui on 2016/8/16.
 */
public interface IMeterReaderListener {
    public void onStateChanged(int stateCode, String stateMsg);
    public void onError(String errMsg);
    public void onMessage(String msg);
    public void onReceivedData(StringBuffer data);
}
