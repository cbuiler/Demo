package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class WellWaterMeterageBean extends BaseBean {
    String wellNo;
    byte reportType;
    Date reportTime;
    String total;

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        WellWaterMeterageBean obj = (WellWaterMeterageBean)gson.fromJson(
                serial, this.getClass());
        this.wellNo = obj.getWellNo();
        this.reportTime = obj.getReportTime();
        this.reportType = obj.getReportType();
        this.total = obj.getTotal();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getWellNo() {
        return wellNo;
    }

    public void setWellNo(String wellNo) {
        this.wellNo = wellNo;
    }

    public byte getReportType() {
        return reportType;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public String getTotal() {
        return total;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public void setReportType(byte reportType) {
        this.reportType = reportType;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
