package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class WellInfoBean extends BaseBean {
    String wellName;
    String wellNumber;
    String areaNumber;

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        WellInfoBean obj = (WellInfoBean)gson.fromJson(serial, this.getClass());
        this.wellName = obj.getWellName();
        this.wellNumber = obj.getWellNumber();
    }

    public String getWellName() {return wellName;}
    public String getWellNumber() {return wellNumber;}
    public String getAreaNumber() {return areaNumber;}

    public void setWellName(String name) {wellName = name;}
    public void setWellNumber(String number) {wellNumber = number;}
    public void setAreaNumber(String number) {areaNumber = number;}

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
