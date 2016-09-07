package com.demo.dj.HuanXin.protocols;

/**
 * Created by guozhaohui on 2016/9/2.
 */
public interface IUSBClient {
    public void onUSBClientStart();
    public void onUSBClientStop();
    public void onUSBClientReady();
    public void onUSBClientEnd();
    public void onUSBClientError(String errMsg);
}
