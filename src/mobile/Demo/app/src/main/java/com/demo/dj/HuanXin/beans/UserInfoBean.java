package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by guozhaohui on 2016/8/23.
 */
public class UserInfoBean extends BaseBean {
    String userName;
    String userCardNo;
    String areaNumber;

    public String getAreaNumber() {
        return areaNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserCardNo() {
        return userCardNo;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAreaNumber(String areaNo) {
        this.areaNumber = areaNo;
    }

    public void setUserCardNo(String userCardNo) {
        this.userCardNo = userCardNo;
    }

    @Override
    public void parse(String serial) {
        Gson gson = new Gson();
        UserInfoBean obj = (UserInfoBean)gson.fromJson(serial, this.getClass());
        this.userName = obj.getUserName();
        this.userCardNo = obj.getUserCardNo();
        this.areaNumber = obj.getAreaNumber();
    }
}
