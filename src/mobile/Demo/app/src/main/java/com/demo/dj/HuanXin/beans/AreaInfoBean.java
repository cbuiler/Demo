package com.demo.dj.HuanXin.beans;

import java.io.Serializable;
import com.google.gson.Gson;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class AreaInfoBean extends BaseBean {
    String areaName;
    String areaNumber;

    public String getAreaName(){return areaName;}
    public String getAreaNumber(){return areaNumber;}

    public void setAreaName(String name){areaName = name;}
    public void setAreaNumber(String number){areaNumber = number;}

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        AreaInfoBean obj = (AreaInfoBean)gson.fromJson(serial, this.getClass());
        this.areaName = obj.getAreaName();
        this.areaNumber = obj.getAreaNumber();
    }
}
