package com.demo.dj.HuanXin.tools.usb;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.demo.dj.HuanXin.AppManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by guozhaohui on 2016/8/18.
 */
public class SyncConnectService extends Service {

    public static final String ACTION_CLIENT_START = "HUANXIN_SYNC_CLIENT_START";
    public static final String ACTION_CLIENT_STOP = "HUANXIN_SYNC_CLIENT_STOP";
    public static final String ACTION_CLIENT_RESPONSE = "HUANXIN_SYNC_CLIENT_RESPONSE";
    public static final String ACTION_CLIENT_READY = "HUANXIN_SYNC_CLIENT_READY";
    public static final String ACTION_END = "HUANXIN_SYNC_END";
    public static final String ACTION_ERROR = "HUANXIN_SYNC_ERROR";

    public static final String TAG = "chl";
    public static Boolean mainThreadFlag = true;
    public static Boolean ioThreadFlag = true;
    ServerSocket serverSocket = null;
    final int SERVER_PORT = 10086;
    private Socket socket = null;

    private String request;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent();
            switch (msg.what) {
                case ThreadReadWriterIOSocket.MSG_CLIENT_RESPONSE:
                    intent.setAction(ACTION_CLIENT_RESPONSE);
                    intent.putExtra("data", (String)msg.obj);
                    sendBroadcast(intent);
                    break;

                case ThreadReadWriterIOSocket.MSG_ERROR:
                    intent.setAction(ACTION_ERROR);
                    intent.putExtra("data", (String)msg.obj);
                    sendBroadcast(intent);
                    break;

                case ThreadReadWriterIOSocket.MSG_END:
                    intent.setAction(ACTION_END);
                    sendBroadcast(intent);
                    break;

                case ThreadReadWriterIOSocket.MSG_SYNC_REQUEST:
                    if (socket != null) {
                        //启动通讯线程
                        new Thread(
                                new ThreadReadWriterIOSocket(
                                        handler, socket, (String)msg.obj)).start();
                    }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        Log.d("usbh", "创建服务...");
        super.onCreate();
        Log.d("usbh", "创建服务完成...");
        AppManager.getInstance().setServiceHandler(handler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("usbh", "启动服务...");
        mainThreadFlag = true;
        request = intent.getStringExtra("request");
        new Thread()
        {
            public void run()
            {
                doListen();
            };
        }.start();
        return START_NOT_STICKY;
    }

    private void doListen()
    {
        serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while (mainThreadFlag)
            {
                Log.d("usbh", "准备监听客户端连接。。。");
                socket = serverSocket.accept();
                Log.d("usbh", "监听到客户端连接");

                //向主线程发送客户端已连接消息
                Intent intent = new Intent(ACTION_CLIENT_READY);
                Log.d("usbh", "准备发送ACTION_CLIENt_READY广播");
                sendBroadcast(intent);
                Log.d("usbh", "已发送ACTION_CLIENt_READY广播");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainThreadFlag = false;
        ioThreadFlag = false;
        try
        {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
