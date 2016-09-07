package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class UserMeterageBean extends BaseBean {
    String wellNumber;
    String userCardNo;
    Date openTime;
    Date closeTime;
    String remain;
    String elecUsage;
    String waterUsage;
    byte syncFlag;

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        UserMeterageBean obj = (UserMeterageBean)gson.fromJson(serial, this.getClass());

        this.wellNumber = obj.getWellNumber();
        this.userCardNo = obj.getUserCardNo();
        this.openTime = obj.getOpenTime();
        this.closeTime = obj.getCloseTime();
        this.remain = obj.getRemain();
        this.elecUsage = obj.getElecUsage();
        this.syncFlag = obj.getSyncFlag();
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public String getElecUsage() {
        return elecUsage;
    }

    public String getRemain() {
        return remain;
    }

    public String getUserCardNo() {
        return userCardNo;
    }

    public String getWellNumber() {
        return wellNumber;
    }

    public String getWaterUsage() { return waterUsage; }

    public byte getSyncFlag() {
        return syncFlag;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public void setElecUsage(String elecUsage) {
        this.elecUsage = elecUsage;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public void setUserCardNo(String userCardNo) {
        this.userCardNo = userCardNo;
    }

    public void setWellNumber(String wellNo) {
        this.wellNumber = wellNo;
    }

    public void setSyncFlag(byte syncFlag) {
        this.syncFlag = syncFlag;
    }

    public void setWaterUsage(String waterUsage) { this.waterUsage = waterUsage; }
}
