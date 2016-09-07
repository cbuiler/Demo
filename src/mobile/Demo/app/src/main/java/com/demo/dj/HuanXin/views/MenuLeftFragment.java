package com.demo.dj.HuanXin.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.dj.HuanXin.AppManager;
import com.demo.dj.HuanXin.models.AreaInfo;
import com.demo.dj.HuanXin.models.UserInfo;
import com.demo.dj.HuanXin.models.WellInfo;
import com.demo.dj.demo.R;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/8/5.
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener , View.OnTouchListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_menu_left, container, false);
        view.setOnTouchListener(this);
        initView(view);
        return view;
    }

    private void initView(View view){
        view.findViewById(R.id.menu_water).setOnClickListener(this);
        view.findViewById(R.id.menu_electricity).setOnClickListener(this);
        view.findViewById(R.id.menu_user_count).setOnClickListener(this);
        view.findViewById(R.id.menu_upload_data).setOnClickListener(this);
        view.findViewById(R.id.menu_help).setOnClickListener(this);
    }

    /**
     * 添加onTouch是防止在实现Fragment手势的时候后面Activity中控件响应事件
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_water:
                onReadWaterMeter();
                break;
            case R.id.menu_electricity:
                onReadElecMeter();
                break;
            case R.id.menu_user_count:
                onReadUserMeter();
                break;
            case R.id.menu_upload_data:
                onSychronize();
                break;
            case R.id.menu_help:
                Toast.makeText(getContext(), "menu_help", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 跳转到需要的activity，可以以class name形式跳转
     */
    private void gotoOtherActivity(){
        Intent intent = new Intent();
        getContext().startActivity(intent);
    }

    private void onReadWaterMeter() {
        Toast.makeText(getContext(), "menu_water", Toast.LENGTH_SHORT).show();
    }

    private void onReadElecMeter() {
        Toast.makeText(getContext(), "menu_electricity", Toast.LENGTH_SHORT).show();
    }

    private void onReadUserMeter() {
        Toast.makeText(getContext(), "menu_user_count", Toast.LENGTH_SHORT).show();
    }

    private void onSychronize() {
        //基础信息同步
        AreaInfo areaInfo = new AreaInfo();
        //UserInfo userInfo = new UserInfo();
        //WellInfo wellInfo = new WellInfo();
        try {
            areaInfo.synchronize();
            //userInfo.syncUserInfo(getContext());
            //wellInfo.syncWellInfo(getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), "menu_upload_data", Toast.LENGTH_SHORT).show();
    }

}