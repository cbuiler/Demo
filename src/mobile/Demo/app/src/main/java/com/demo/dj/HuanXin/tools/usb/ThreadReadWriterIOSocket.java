package com.demo.dj.HuanXin.tools.usb;

import android.os.Handler;
import android.os.Message;

import com.demo.dj.HuanXin.protocols.IUSBProtocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by guozhaohui on 2016/8/18.
 */
public class ThreadReadWriterIOSocket implements Runnable {

    public static final int MSG_CLIENT_RESPONSE = 0;
    public static final int MSG_END = 1;
    public static final int MSG_ERROR = 2;
    public static final int MSG_SYNC_REQUEST = 3;

    private Socket client;
    private Handler handler;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String response = "";
    private String request = "";

    public ThreadReadWriterIOSocket(
            Handler handler, Socket client, String request)
    {
        this.client = client;
        this.handler = handler;
        this.request = request;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(client.getOutputStream())), true);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

         try {

             SyncConnectService.ioThreadFlag = true;
             while (SyncConnectService.ioThreadFlag){
                 try {

                     //如果失去连接则抛出异常
                     if(!client.isConnected()) {
                         throw new Exception("通讯失去连接！");
                     }

                     //调用对应Model对象创建请求串，然后发出向PC端的同步请求
                     out.println(request);

                     //获取客户端返回的同步结果，然后调用对应Model对象的数据响应处理
                     response = in.readLine();

                     //发出数据响应消息
                     Message msg = handler.obtainMessage();
                     msg.what = MSG_CLIENT_RESPONSE;
                     msg.obj = response;
                     handler.sendMessage(msg);

                     //线程结束
                     SyncConnectService.ioThreadFlag = false;
                 }
                 catch (Exception e)
                 {
                     //发送数据错误消息
                     Message msg = handler.obtainMessage();
                     msg.what = MSG_ERROR;
                     msg.obj = e.getMessage();
                     handler.sendMessage(msg);

                     e.printStackTrace();
                 }
             }
             out.close();
             in.close();
         }
         catch (Exception e)
         {
             // TODO: handle exception
             Message msg = handler.obtainMessage();
             msg.what = MSG_ERROR;
             msg.obj = e.getMessage();
             handler.sendMessage(msg);

             e.printStackTrace();
         }
         finally
         {
             try
             {
                 if (client != null)
                 {
                     client.close();
                 }
             } catch (IOException e)
             {
                 e.printStackTrace();
             }
         }

        //发出同步结束消息
        Message msg = handler.obtainMessage();
        msg.what = MSG_END;
        handler.sendMessage(msg);
    }
}
