package com.demo.dj.HuanXin.beans;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by guozhaohui on 2016/8/24.
 */
abstract public class BaseBean implements Serializable {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public abstract void parse(String serial);
}
