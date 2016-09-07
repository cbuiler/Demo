package com.demo.dj.HuanXin.protocols;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.beans.BaseBean;
import com.demo.dj.HuanXin.tools.usb.SyncConnectService;
import com.demo.dj.HuanXin.tools.usb.ThreadReadWriterIOSocket;
import com.google.gson.Gson;

import org.json.JSONException;

/**
 * Created by guozhaohui on 2016/8/24.
 */
public class USBProtocol {
    /**
     * 数据同步请求（发给PC）
     */
    /*
    private class Request {
        private String protoName;
        private String data;

        public String getProtoName() {
            return protoName;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void setProtoName(String protoName) {
            this.protoName = protoName;
        }
    }
    */

    /**
     * 数据同步响应（PC返回）
     */
    /*
    private class Response {
        private String status;
        private String data;

        public String getStatus() {
            return status;
        }

        public String getData() {
            return data;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    */

    static public final String JSON_PROTOCOL_KEY = "protocol";
    static public final String JSON_DATA_KEY = "data";
    static public final String JSON_RESPONSE_STATUS = "status";

    /*
    private Request request = new Request();
    private Response response = new Response();
    */
    private IUSBClient clientListener;
    private IUSBProtocol protocolListener;
    private Context context;

    private boolean usbReady = false;

    Gson gson = new Gson();

    public USBProtocol(Context context) {
        this.context = context;
        registerReceiver();
    }

    private void registerReceiver() {
        usbReady = false;

        IntentFilter filter = new IntentFilter();

        filter.addAction(SyncConnectService.ACTION_CLIENT_START);
        filter.addAction(SyncConnectService.ACTION_CLIENT_STOP);
        filter.addAction(SyncConnectService.ACTION_CLIENT_RESPONSE);
        filter.addAction(SyncConnectService.ACTION_CLIENT_READY);
        filter.addAction(SyncConnectService.ACTION_ERROR);
        filter.addAction(SyncConnectService.ACTION_END);

        context.registerReceiver(mReceiver, filter);
    }

    public boolean isUsbReady() {
        return usbReady;
    }

    public void destroy() {
        context.unregisterReceiver(mReceiver);
    }

    public void registerClientListener(IUSBClient listener) {
        this.clientListener = listener;
    }

    private void registerProtocolListener(IUSBProtocol listener){
        this.protocolListener = listener;
    }

    public void synchronize(IUSBProtocol listener) throws JSONException {
        registerProtocolListener(listener);

        //发出数据同步请求消息给监听服务
        Handler handler = AppManager.getInstance().getServiceHandler();
        Message msg = handler.obtainMessage();
        msg.what = ThreadReadWriterIOSocket.MSG_SYNC_REQUEST;
        msg.obj = listener.buildRequest();
        handler.sendMessage(msg);

    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.d("usbh", "收到广播消息： " + action);

            //客户端启动
            if (SyncConnectService.ACTION_CLIENT_START.equalsIgnoreCase(action)) {
                if (clientListener != null)
                    clientListener.onUSBClientStart();

                //启动Socket监听服务
                Intent serviceIntent;
                serviceIntent = new Intent(context, SyncConnectService.class);
                context.startService(serviceIntent);
            }
            //客户端终止
            else if (SyncConnectService.ACTION_CLIENT_STOP.equalsIgnoreCase(action)) {
                if (clientListener != null)
                    clientListener.onUSBClientStop();
                context.stopService(new Intent(context, SyncConnectService.class));
                //context.unregisterReceiver(this);
            }
            //同步服务就位
            else if (SyncConnectService.ACTION_CLIENT_READY.equalsIgnoreCase(action)) {
                usbReady = true;
                Log.d("usbh", "usbReady标志为True");
                if (clientListener != null)
                    clientListener.onUSBClientReady();
                Log.d("usbh", "客户端已连接");
            }
            //同步服务完成
            else if (SyncConnectService.ACTION_END.equalsIgnoreCase(action)) {
                if (clientListener != null)
                    clientListener.onUSBClientEnd();
            }
            //同步服务错误
            else if (SyncConnectService.ACTION_ERROR.equalsIgnoreCase(action)) {
                if (clientListener != null)
                    clientListener.onUSBClientError(intent.getDataString());
            }
            //同步服务PC端响应
            else if (SyncConnectService.ACTION_CLIENT_RESPONSE.equalsIgnoreCase(action)) {
                if (protocolListener != null)
                    try {
                        Log.d("usbh", "收到客户端响应广播：ProtocolListener准备处理onUSBProtocolResponse: "+
                            intent.getStringExtra("data"));
                        protocolListener.onUSBProtocolResponse(
                                intent.getStringExtra("data"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                Log.d("usbh", "客户端响应");
            }
        }
    };
}
