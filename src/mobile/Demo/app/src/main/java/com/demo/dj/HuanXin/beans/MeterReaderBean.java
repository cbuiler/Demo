package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class MeterReaderBean extends BaseBean {
    String readerName;
    String password;
    String readerId;

    public String getPassword() {
        return password;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReaderId(String userId) { this.readerId = readerId; }

    public void setReaderName(String userName) {
        this.readerName = readerName;
    }

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        MeterReaderBean obj = (MeterReaderBean)gson.fromJson(serial, this.getClass());
        this.readerName = obj.getReaderName();
        this.password = obj.getPassword();
        this.readerId = obj.getReaderId();
    }

}
