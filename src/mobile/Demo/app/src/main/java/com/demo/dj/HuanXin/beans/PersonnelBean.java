package com.demo.dj.HuanXin.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PersonnelBean implements Serializable {
    private long id;//用户id
    private String address;//地址
    private String name;//姓名
    private String picUrl;//头像url
    private double used;//用量
    private double remain;//剩余
    private double price;//费用


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
