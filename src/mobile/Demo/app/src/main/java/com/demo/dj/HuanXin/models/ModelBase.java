package com.demo.dj.HuanXin.models;

import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.beans.MeterReaderBean;
import com.demo.dj.HuanXin.protocols.IUSBProtocol;
import com.demo.dj.HuanXin.protocols.USBProtocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guozhaohui on 2016/9/3.
 */
public class ModelBase implements IUSBProtocol {
    protected DBManager mDbManager;

    public ModelBase() {
        mDbManager = AppManager.getInstance().getDbManager();
    }

    public boolean isAnonymous() {
        return !mDbManager.isAuthorized();
    }

    //与PC同步数据
    public void synchronize() throws JSONException {
        if (AppManager.getInstance().isUsbReady()) {
            //注册同步事件监听及消息监听（由USB数据线连接事件触发）
            AppManager.getInstance().synchronize(this);
        }
        else {
            Toast.makeText(AppManager.getInstance(),
                    "请连接好数据线并启动管理软件！", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public String buildRequest() throws JSONException {
        return "";
    }

    @Override
    public void onUSBProtocolResponse(String response) throws JSONException {
        Log.d("usbh", "基类（"+ this.getClass().toString() + "）收到客户端响应: " + response);
    }
}
