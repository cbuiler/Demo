package com.demo.dj.HuanXin.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.beans.MeterReaderBean;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guozhaohui on 2016/8/21.
 */
public class MeterReader extends ModelBase{

    private String JSON_PROTOCOL_VALUE = "HXCB_UP_0101";

    public boolean isAnonymous() {
        return !mDbManager.isAuthorized();
    }

    private IMeterReader listener = null;
    //注册MeterReader事件接收器
    public void registerListener(IMeterReader listener) {
        this.listener = listener;
    }

    //身份验证
    public boolean checkAuthorition(String name, String pass) {
        return mDbManager.checkAuthorization(name, pass);
    }

    @Override
    public String buildRequest() throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(USBProtocol.JSON_PROTOCOL_KEY, JSON_PROTOCOL_VALUE);
        Log.d("usbh", "buildRequest: " + jsonObj.toString());
        return jsonObj.toString();
    }

    @Override
    public void onUSBProtocolResponse(String response) throws JSONException {
        Log.d("usbh", this.getClass().toString() + ": 收到客户端响应: " + response);

        JSONObject jsonObj = new JSONObject(response);
        JSONArray jsonData = jsonObj.getJSONArray(USBProtocol.JSON_DATA_KEY);

        Log.d("usbh", this.getClass().toString() + ": 响应数据: " + jsonData.toString());

        if (jsonData.length() > 0) {
            MeterReaderBean meterReader = new MeterReaderBean();
            meterReader.parse(jsonData.getString(0));

            if (mDbManager.authorize(meterReader)) {
                if (listener != null)
                    listener.onAuthorized(meterReader.getReaderId());

                Log.d("usbh", "管理员姓名: " +
                        meterReader.getReaderName() + "; ID: " +
                        meterReader.getReaderId() + "; Password: " +
                        meterReader.getPassword());
            }
            else {
                Toast.makeText(AppManager.getInstance(),
                        "获取APP授权信息失败！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
