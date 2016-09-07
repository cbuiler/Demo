package com.demo.dj.HuanXin.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.beans.AreaInfoBean;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class AreaInfo extends ModelBase {

    private String JSON_PROTOCOL_VALUE = "HXCB_UP_0202";

    @Override
    public String buildRequest() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(USBProtocol.JSON_PROTOCOL_KEY, JSON_PROTOCOL_VALUE);
        Log.d("usbh", "buildRequest: " + jsonObj.toString());
        return jsonObj.toString();
    }

    @Override
    public void onUSBProtocolResponse(String response) throws JSONException {
        JSONObject jsonObj = new JSONObject(response);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        for (int i=0; i<jsonArray.length(); i++)
        {
            AreaInfoBean item = (AreaInfoBean)jsonArray.get(i);
            AppManager.getInstance().getDbManager().replaceAreaInfo(item);
        }

        Toast.makeText(AppManager.getInstance(), response, Toast.LENGTH_SHORT).show();
    }
}