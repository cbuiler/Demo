package com.demo.dj.HuanXin.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class UserInfo extends ModelBase {

    private String JSON_PROTOCOL_VALUE = "HXCB_UP_0201";

    @Override
    public String buildRequest() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(USBProtocol.JSON_PROTOCOL_KEY, JSON_PROTOCOL_VALUE);
        Log.d("usbh", "buildRequest: " + jsonObj.toString());
        return jsonObj.toString();
    }

    @Override
    public void onUSBProtocolResponse(String response) throws JSONException {

    }

}
