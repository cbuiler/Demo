package com.demo.dj.HuanXin.protocols;

import org.json.JSONException;

/**
 * Created by guozhaohui on 2016/8/28.
 */
public interface IUSBProtocol {
    public String buildRequest() throws JSONException;
    public void onUSBProtocolResponse(String response) throws JSONException;
}

