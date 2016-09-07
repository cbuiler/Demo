package com.demo.dj.HuanXin.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.beans.WellElecMeterageBean;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class WellElecMeterage extends ModelBase {
    private String JSON_PROTOCOL_VALUE = "HXCB_UP_0302";

    private ArrayList<WellElecMeterageBean> data = new ArrayList<WellElecMeterageBean>();

    @Override
    public String buildRequest() throws JSONException {

        JSONObject jsonObj = new JSONObject();

        //添加协议字段
        jsonObj.put(USBProtocol.JSON_PROTOCOL_KEY, JSON_PROTOCOL_VALUE);

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();

        //添加数据项到Json数组
        for (int i=0; i<data.size(); i++) {
            WellElecMeterageBean item = data.get(i);
            String jsonStr = gson.toJson(item);
            JSONObject jsonItem = new JSONObject(jsonStr);
            jsonArray.put(i, jsonItem);
        }

        //添加生成的Json数组到data字段
        jsonObj.put("data", jsonArray);

        Log.d("usbh", "buildRequest: " + jsonObj.toString());

        return jsonObj.toString();
    }

    @Override
    public void onUSBProtocolResponse(String response) throws JSONException {

    }

}
